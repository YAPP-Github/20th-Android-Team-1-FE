package com.yapp.growth.presentation.ui.main.manage.confirm

import androidx.lifecycle.viewModelScope
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.SendingResponsePlan
import com.yapp.growth.domain.entity.ResponsePlan
import com.yapp.growth.domain.entity.User
import com.yapp.growth.domain.usecase.GetRespondUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.yapp.growth.presentation.ui.main.manage.confirm.ConfirmPlanContract.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ConfirmPlanViewModel @Inject constructor(
    private val getRespondUsersUseCase: GetRespondUsersUseCase
) : BaseViewModel<ConfirmPlanViewState, ConfirmPlanSideEffect, ConfirmPlanEvent>(
    ConfirmPlanViewState()
) {

    private val _sendResponsePlan = MutableStateFlow<List<SendingResponsePlan>>(emptyList())
    val sendResponsePlan: StateFlow<List<SendingResponsePlan>>
        get() = _sendResponsePlan.asStateFlow()

    private val _responsePlan = MutableStateFlow<ResponsePlan>(ResponsePlan(emptyList(),
        emptyList(), emptyList(),0,"","", emptyList(), emptyList()
    ))

    val responsePlan: StateFlow<ResponsePlan>
        get() = _responsePlan

    private val _currentClickTimeIndex = MutableStateFlow(-1 to -1)
    val currentClickTimeIndex: StateFlow<Pair<Int, Int>>
        get() = _currentClickTimeIndex.asStateFlow()

    private val _currentClickUserData = MutableStateFlow<List<User>>(emptyList())
    val currentClickUserData: StateFlow<List<User>>
        get() = _currentClickUserData.asStateFlow()

    init {
        loadRespondUsers(0L)
    }

    private fun loadRespondUsers(promisingKey: Long) {
        viewModelScope.launch {
            val result = (getRespondUsersUseCase(promisingKey) as? NetworkResult.Success)?.data
            result?.let {
                makeRespondList(it)
                _responsePlan.value = it
            }
        }
    }

    private fun makeRespondList(data: ResponsePlan) {
        val booleanArray = Array(data.totalCount*2) { false }

        val temp = mutableListOf<SendingResponsePlan>().also { list ->
            repeat(data.availableDate.size) {
                list.add(SendingResponsePlan(
                    date = data.availableDate[it],
                    hours = data.hourList,
                    timeList = booleanArray.copyOf().toMutableList()
                ))
            }
        }.toList()

        _sendResponsePlan.value = temp
    }

    private fun filterCurrentSelectedUser(dateIndex: Int, minuteIndex: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            val day = responsePlan.value.availableDate[dateIndex]
            var hour = responsePlan.value.hourList[minuteIndex/2]

            val blockList = responsePlan.value.timeTable.find { it.date == day }?.blocks
            val userList = blockList?.let { block ->
                block.find { it.index == minuteIndex }?.users
            }
            _currentClickUserData.value = userList ?: emptyList()

            sendEffect({
                ConfirmPlanSideEffect.ShowBottomSheet
            })
        }
    }

    override fun handleEvents(event: ConfirmPlanEvent) {
        when (event) {
            is ConfirmPlanEvent.OnClickNextDayButton -> { }
            is ConfirmPlanEvent.OnClickPreviousDayButton -> { }
            is ConfirmPlanEvent.OnClickConfirmButton -> { }
            is ConfirmPlanEvent.OnClickTimeTable -> {
                _sendResponsePlan.value[event.dateIndex].timeList[event.minuteIndex] = _sendResponsePlan.value[event.dateIndex].timeList[event.minuteIndex].not()
                _currentClickTimeIndex.value  = event.dateIndex to event.minuteIndex
                filterCurrentSelectedUser(event.dateIndex, event.minuteIndex)
            }
        }
    }
}
