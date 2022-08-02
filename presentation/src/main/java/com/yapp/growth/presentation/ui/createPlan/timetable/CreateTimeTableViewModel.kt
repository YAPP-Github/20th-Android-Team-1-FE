package com.yapp.growth.presentation.ui.createPlan.timetable

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.base.LoadState
import com.yapp.growth.domain.entity.CreateTimeTable
import com.yapp.growth.domain.entity.TimeCheckedOfDay
import com.yapp.growth.domain.onError
import com.yapp.growth.domain.onSuccess
import com.yapp.growth.domain.usecase.GetCreateTimeTableUseCase
import com.yapp.growth.domain.usecase.MakePlanUseCase
import com.yapp.growth.presentation.ui.createPlan.timetable.CreateTimeTableContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateTimeTableViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCreateTimeTableUseCase: GetCreateTimeTableUseCase,
    private val makePlanUseCase: MakePlanUseCase,
): BaseViewModel<CreateTimeTableViewState, CreateTimeTableSideEffect, CreateTimeTableEvent>(CreateTimeTableViewState()) {

    private val _timeCheckedOfDays = MutableStateFlow<List<TimeCheckedOfDay>>(emptyList())
    val timeCheckedOfDays: StateFlow<List<TimeCheckedOfDay>>
        get() = _timeCheckedOfDays.asStateFlow()

    private var originalTable: CreateTimeTable = CreateTimeTable(0,"","", emptyList(), emptyList())
    private var currentIndex = 0

    private var uuid: String = savedStateHandle.get<String>("uuid") ?: ""

    init {
        loadCreateTimeTable(uuid)
    }

    private fun loadCreateTimeTable(uuid: String) = viewModelScope.launch {
        updateState { copy(loadState = LoadState.LOADING) }
        getCreateTimeTableUseCase.invoke(uuid)
            .onSuccess {
                originalTable = it
                makeRespondList(it)
                val sliceCreateTimeTable = if (it.availableDates.size >= 4) {
                    it.copy(availableDates = it.availableDates.subList(0, 4))
                } else {
                    it.copy(availableDates = it.availableDates.subList(0, it.availableDates.size))
                }

                updateState {
                    copy(loadState = LoadState.SUCCESS, createTimeTable = sliceCreateTimeTable)
                }
            }
            .onError {
                updateState { copy(loadState = LoadState.ERROR) }
            }
    }

    private fun nextDay() {
        currentIndex += 1
        val fromIndex = currentIndex.times(4)
        if (fromIndex >= originalTable.availableDates.size) {
            currentIndex -= 1
            return
        }

        val toIndex: Int = if (originalTable.availableDates.size < fromIndex.plus(4)) {
            originalTable.availableDates.size
        } else {
            fromIndex.plus(4)
        }
        val sliceCreateTimeTable: CreateTimeTable = originalTable.copy(availableDates = originalTable.availableDates.subList(fromIndex, toIndex))
        updateState {
            copy(createTimeTable = sliceCreateTimeTable)
        }
    }

    private fun previousDay() {
        if (currentIndex == 0) return
        currentIndex -= 1
        val fromIndex = currentIndex.times(4)
        val toIndex = fromIndex.plus(4)

        val temp: CreateTimeTable = originalTable.copy(availableDates = originalTable.availableDates.subList(fromIndex, toIndex))
        updateState {
            copy(createTimeTable = temp)
        }
    }

    private fun makeRespondList(data: CreateTimeTable) {
        val booleanArray = Array(data.totalCount.times(2)) { false }

        val temp = mutableListOf<TimeCheckedOfDay>().also { list ->
            repeat(data.availableDates.size) {
                list.add(TimeCheckedOfDay(
                    date = data.availableDates[it],
                    timeList = booleanArray.copyOf().toMutableList()
                ))
            }
        }.toList()

        _timeCheckedOfDays.value = temp
    }

    private fun sendMakePlan(uuid: String, timeCheckedOfDays: List<TimeCheckedOfDay>) =
        viewModelScope.launch {
            makePlanUseCase.invoke(uuid, timeCheckedOfDays)
                .onSuccess { planId ->
                    sendEffect({ CreateTimeTableSideEffect.NavigateToNextScreen(planId) })
                }
                .onError {
                    TODO()
                }
        }

    override fun handleEvents(event: CreateTimeTableEvent) {
        when (event) {
            CreateTimeTableEvent.OnClickBackButton -> {
                if(viewState.value.loadState == LoadState.SUCCESS) {
                    updateState { copy(isDialogVisible = true) }
                } else {
                    sendEffect({ CreateTimeTableSideEffect.ExitCreateScreen })
                }
            }
            CreateTimeTableEvent.OnClickExitButton -> {
                if(viewState.value.loadState == LoadState.SUCCESS) {
                    updateState { copy(isDialogVisible = true) }
                } else {
                    sendEffect({ CreateTimeTableSideEffect.ExitCreateScreen })
                }
            }
            CreateTimeTableEvent.OnClickSendButton -> sendMakePlan(uuid, timeCheckedOfDays.value)
            CreateTimeTableEvent.OnClickNextDayButton -> { nextDay() }
            CreateTimeTableEvent.OnClickPreviousDayButton -> { previousDay() }
            is CreateTimeTableEvent.OnClickTimeTable -> {
                _timeCheckedOfDays.value[currentIndex.times(4).plus(event.dateIndex)].timeList[event.minuteIndex] = _timeCheckedOfDays.value[currentIndex.times(4).plus(event.dateIndex)].timeList[event.minuteIndex].not()
                if (timeCheckedOfDays.value[currentIndex.times(4).plus(event.dateIndex)].timeList[event.minuteIndex]) {
                    updateState { copy(clickCount = viewState.value.clickCount.plus(1)) }
                } else {
                    updateState { copy(clickCount = viewState.value.clickCount.minus(1)) }
                }
            }
            CreateTimeTableEvent.OnClickDialogNegativeButton -> updateState { copy(isDialogVisible = false) }
            CreateTimeTableEvent.OnClickDialogPositiveButton -> sendEffect({ CreateTimeTableSideEffect.ExitCreateScreen })
            CreateTimeTableEvent.OnClickErrorRetryButton -> loadCreateTimeTable(uuid)
        }
    }
}
