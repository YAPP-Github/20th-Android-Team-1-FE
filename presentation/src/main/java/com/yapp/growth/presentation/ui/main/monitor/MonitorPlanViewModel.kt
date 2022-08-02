package com.yapp.growth.presentation.ui.main.monitor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.base.LoadState
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.Category
import com.yapp.growth.domain.entity.TimeCheckedOfDay
import com.yapp.growth.domain.entity.TimeTable
import com.yapp.growth.domain.entity.User
import com.yapp.growth.domain.onError
import com.yapp.growth.domain.onSuccess
import com.yapp.growth.domain.usecase.GetRespondUsersUseCase
import com.yapp.growth.presentation.ui.main.KEY_PLAN_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import com.yapp.growth.presentation.ui.main.monitor.MonitorPlanContract.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MonitorPlanViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getRespondUsersUseCase: GetRespondUsersUseCase,
): BaseViewModel<MonitorPlanViewState, MonitorPlanSideEffect, MonitorPlanEvent>(
    MonitorPlanViewState()
) {

    private var originalTable: TimeTable = TimeTable(emptyList(), emptyList(), 0, emptyList(), 0, "", User(0, ""), "", "", emptyList(), emptyList(), "", "", Category(0,"",""))
    private var currentIndex = 0
    private val planId: Long = savedStateHandle.get<Long>(KEY_PLAN_ID) ?: -1L

    init {
        loadRespondUsers(planId)
    }

    private fun loadRespondUsers(planId: Long) {
        viewModelScope.launch {
            updateState { copy(loadState = LoadState.LOADING) }
            getRespondUsersUseCase.invoke(planId)
                .onSuccess {
                    originalTable = it
                    makeRespondList(it)
                    val sliceTimeTable: TimeTable = if (it.availableDates.size >= 4) {
                        it.copy(availableDates = it.availableDates.subList(0, 4))
                    } else {
                        it.copy(availableDates = it.availableDates.subList(0, it.availableDates.size))
                    }
                    updateState {
                        copy(timeTable = sliceTimeTable)
                    }
                    delay(2000L)
                    updateState {
                        copy(loadState = LoadState.SUCCESS)
                    }
                }
                .onError {
                    updateState { copy(loadState = LoadState.ERROR) }
                }
        }
    }

    private fun makeRespondList(data: TimeTable) {
        val booleanArray = Array(data.totalCount*2) { false }

        val temp = mutableListOf<TimeCheckedOfDay>().also { list ->
            repeat(data.availableDates.size) {
                list.add(
                    TimeCheckedOfDay(
                    date = data.availableDates[it],
                    timeList = booleanArray.copyOf().toMutableList()
                )
                )
            }
        }.toList()
    }

    private fun filterCurrentSelectedUser(dateIndex: Int, minuteIndex: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            val day = originalTable.availableDates[currentIndex.times(4).plus(dateIndex)]
            var hour = originalTable.hourList[minuteIndex/2]

            val blockList = originalTable.timeTableDate.find { it.date == day }?.timeTableUnits
            val userList = blockList?.let { block ->
                block.find { it.index == minuteIndex }?.users
            }
            updateState {
                copy(currentClickUserData = userList ?: emptyList())
            }
        }
    }

    private fun nextDay() = viewModelScope.launch(Dispatchers.Default) {
        currentIndex += 1
        val fromIndex = currentIndex.times(4)
        if (fromIndex >= originalTable.availableDates.size) {
            currentIndex -= 1
            return@launch
        }

        val toIndex: Int = if (originalTable.availableDates.size < fromIndex.plus(4)) {
            originalTable.availableDates.size
        } else {
            fromIndex.plus(4)
        }
        val sliceCreateTimeTable: TimeTable = originalTable.copy(
            availableDates = originalTable.availableDates.subList(
                fromIndex,
                toIndex
            )
        )
        updateState {
            copy(timeTable = sliceCreateTimeTable)
        }
    }


    private fun previousDay() = viewModelScope.launch(Dispatchers.Default) {
        if (currentIndex == 0) return@launch
        currentIndex -= 1
        val fromIndex = currentIndex.times(4)
        val toIndex = fromIndex.plus(4)

        val temp: TimeTable = originalTable.copy(
            availableDates = originalTable.availableDates.subList(
                fromIndex,
                toIndex
            )
        )
        updateState {
            copy(timeTable = temp)
        }
    }

    private fun initCurrentClickTimeIndex() = updateState {
        copy(currentClickTimeIndex = -1 to -1)
    }

    override fun handleEvents(event: MonitorPlanEvent) {
        when (event) {
            MonitorPlanEvent.OnClickBackButton -> sendEffect({ MonitorPlanSideEffect.NavigateToPreviousScreen })
            MonitorPlanEvent.OnClickNextDayButton -> nextDay()
            MonitorPlanEvent.OnClickPreviousDayButton -> previousDay()
            MonitorPlanEvent.OnClickExitIcon -> sendEffect({ MonitorPlanSideEffect.HideBottomSheet })
            is MonitorPlanEvent.OnClickTimeTable -> {
                updateState { copy(currentClickTimeIndex = event.dateIndex to event.minuteIndex) }
                sendEffect({ MonitorPlanSideEffect.ShowBottomSheet })
                filterCurrentSelectedUser(event.dateIndex, event.minuteIndex)
            }
            MonitorPlanEvent.OnClickErrorRetryButton -> loadRespondUsers(planId)
        }
    }

}
