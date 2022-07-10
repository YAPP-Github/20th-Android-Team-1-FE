package com.yapp.growth.presentation.ui.main.manage.confirm

import androidx.lifecycle.viewModelScope
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.RespondPlan
import com.yapp.growth.domain.entity.RespondUsers
import com.yapp.growth.domain.usecase.GetRespondUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.yapp.growth.presentation.ui.main.manage.confirm.ConfirmPlanContract.*
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

    private val _dates = MutableStateFlow<List<RespondPlan>>(emptyList())
    val dates: StateFlow<List<RespondPlan>>
        get() = _dates.asStateFlow()

    private val _respondUser = MutableStateFlow<RespondUsers>(RespondUsers(emptyList(),
        emptyList(), emptyList(),0,"","", emptyList(), emptyList()
    ))

    val respondUser: StateFlow<RespondUsers>
        get() = _respondUser

    private var cachedIndex = Pair(-1,-1)

    init {
        loadRespondUsers(0L)
    }

    private fun loadRespondUsers(promisingKey: Long) {
        viewModelScope.launch {
            val result = (getRespondUsersUseCase(promisingKey) as? NetworkResult.Success)?.data
            result?.let {
                makeRespondList(it)
                _respondUser.value = it
            }
        }
    }

    private fun makeRespondList(data: RespondUsers) {
        val booleanArray = Array(data.totalCount*2) { false }

        val temp = mutableListOf<RespondPlan>().also { list ->
            repeat(data.avaliableDate.size) {
                list.add(RespondPlan(
                    date = data.avaliableDate[it],
                    hours = data.hourList,
                    timeList = booleanArray.copyOf().toMutableList()
                ))
            }
        }.toList()

        _dates.value = temp
    }

    override fun handleEvents(event: ConfirmPlanEvent) {
        when (event) {
            is ConfirmPlanEvent.OnClickNextDayButton -> { }
            is ConfirmPlanEvent.OnClickPreviousDayButton -> { }
            is ConfirmPlanEvent.OnClickConfirmButton -> { }
            is ConfirmPlanEvent.OnClickTimeTable -> {
                _dates.value[event.dateIndex].timeList[event.minuteIndex] = _dates.value[event.dateIndex].timeList[event.minuteIndex].not()
                if (cachedIndex.first > 0 && cachedIndex.second > 0 && cachedIndex.first != event.dateIndex && cachedIndex.second != event.minuteIndex) {
                  _dates.value[cachedIndex.first].timeList[cachedIndex.second] = false
                }
                cachedIndex = event.dateIndex to event.minuteIndex
            }
        }
    }
}
