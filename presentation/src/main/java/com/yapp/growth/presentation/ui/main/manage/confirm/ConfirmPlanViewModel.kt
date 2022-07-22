package com.yapp.growth.presentation.ui.main.manage.confirm

import androidx.lifecycle.viewModelScope
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.TimeTable
import com.yapp.growth.domain.entity.TimeCheckedOfDay
import com.yapp.growth.domain.usecase.GetRespondUsersUseCase
import com.yapp.growth.presentation.ui.main.manage.confirm.ConfirmPlanContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmPlanViewModel @Inject constructor(
    private val getRespondUsersUseCase: GetRespondUsersUseCase
) : BaseViewModel<ConfirmPlanViewState, ConfirmPlanSideEffect, ConfirmPlanEvent>(
    ConfirmPlanViewState()
) {

    private val _sendResponsePlan = MutableStateFlow<List<TimeCheckedOfDay>>(emptyList())
    val sendResponsePlan: StateFlow<List<TimeCheckedOfDay>>
        get() = _sendResponsePlan.asStateFlow()

    init {
        loadRespondUsers(14)
    }

    private fun loadRespondUsers(promisingKey: Long) {
        viewModelScope.launch {
            val result = (getRespondUsersUseCase.invoke(promisingKey) as? NetworkResult.Success)?.data
            result?.let {
                makeRespondList(it)
                updateState {
                    copy(timeTable = it)
                }
            }
        }
    }

    private fun makeRespondList(data: TimeTable) {
        val booleanArray = Array(data.totalCount*2) { false }

        val temp = mutableListOf<TimeCheckedOfDay>().also { list ->
            repeat(data.availableDates.size) {
                list.add(TimeCheckedOfDay(
                    date = data.availableDates[it],
                    timeList = booleanArray.copyOf().toMutableList()
                ))
            }
        }.toList()

        _sendResponsePlan.value = temp
    }

    private fun filterCurrentSelectedUser(dateIndex: Int, minuteIndex: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            val day = viewState.value.timeTable.availableDates[dateIndex]
            var hour = viewState.value.timeTable.hourList[minuteIndex/2]

            val blockList = viewState.value.timeTable.timeTableDate.find { it.date == day }?.timeTableUnits
            val userList = blockList?.let { block ->
                block.find { it.index == minuteIndex }?.users
            }
            updateState {
                copy(currentClickUserData = userList ?: emptyList())
            }

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
                updateState {
                    copy(currentClickTimeIndex = event.dateIndex to event.minuteIndex)
                }
                filterCurrentSelectedUser(event.dateIndex, event.minuteIndex)
            }
        }
    }
}
