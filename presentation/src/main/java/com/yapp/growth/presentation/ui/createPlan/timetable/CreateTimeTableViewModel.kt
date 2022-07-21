package com.yapp.growth.presentation.ui.createPlan.timetable

import androidx.lifecycle.viewModelScope
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.CreateTimeTable
import com.yapp.growth.domain.entity.SendingResponsePlan
import com.yapp.growth.domain.usecase.GetCreateTimeTableUseCase
import com.yapp.growth.presentation.ui.createPlan.timetable.CreateTimeTableContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateTimeTableViewModel @Inject constructor(
    private val getCreateTimeTableUseCase: GetCreateTimeTableUseCase
): BaseViewModel<CreateTimeTableViewState, CreateTimeTableSideEffect, CreateTimeTableEvent>(CreateTimeTableViewState()) {

    private val _sendResponsePlan = MutableStateFlow<List<SendingResponsePlan>>(emptyList())
    val sendResponsePlan: StateFlow<List<SendingResponsePlan>>
        get() = _sendResponsePlan.asStateFlow()

    private var originalTable: CreateTimeTable = CreateTimeTable(0,"","", emptyList(), emptyList())

    private var currentIndex = 0

    init {
        loadCreateTimeTable("6d60dac6-2b1d-4b8f-8a06-5766a90c559c")
    }

    fun loadCreateTimeTable(uuid: String) = viewModelScope.launch {
        val result = (getCreateTimeTableUseCase.invoke(uuid) as? NetworkResult.Success)?.data
        result?.let {
            originalTable = it
            makeRespondList(it)
            val temp: CreateTimeTable = it.copy(availableDates = it.availableDates.subList(0,4))
            updateState {
                copy(createTimeTable = temp)
            }
        }
    }

    private fun nextDay() {
        currentIndex += 1
        val fromIndex = currentIndex.times(4)
        if (fromIndex >= originalTable.availableDates.size) {
            currentIndex -= 1
            return
        }

        var toIndex = -1
        toIndex = if (originalTable.availableDates.size < fromIndex.plus(4)) {
            originalTable.availableDates.size
        } else {
            fromIndex.plus(4)
        }
        val temp: CreateTimeTable = originalTable.copy(availableDates = originalTable.availableDates.subList(fromIndex, toIndex))
        updateState {
            copy(createTimeTable = temp)
        }
    }

    private fun previousDay() {
        currentIndex -= 1
        val fromIndex = currentIndex.times(4)
        if (fromIndex <= 0) {
            currentIndex += 1
            return
        }

    }

    private fun makeRespondList(data: CreateTimeTable) {
        val booleanArray = Array(data.totalCount*2) { false }

        val temp = mutableListOf<SendingResponsePlan>().also { list ->
            repeat(data.availableDates.size) {
                list.add(SendingResponsePlan(
                    date = data.availableDates[it],
                    timeList = booleanArray.copyOf().toMutableList()
                ))
            }
        }.toList()

        _sendResponsePlan.value = temp
    }

    override fun handleEvents(event: CreateTimeTableEvent) {
        when (event) {
            CreateTimeTableEvent.OnClickBackButton -> sendEffect({ CreateTimeTableSideEffect.NavigateToPreviousScreen })
            CreateTimeTableEvent.OnClickExitButton -> sendEffect({ CreateTimeTableSideEffect.ExitCreateScreen })
            CreateTimeTableEvent.OnClickNextButton -> sendEffect({ CreateTimeTableSideEffect.NavigateToNextScreen })
            CreateTimeTableEvent.OnClickNextDayButton -> { nextDay() }
            CreateTimeTableEvent.OnClickPreviousDayButton -> { previousDay() }
            is CreateTimeTableEvent.OnClickTimeTable -> {
                _sendResponsePlan.value[currentIndex.times(4).plus(event.dateIndex)].timeList[event.minuteIndex] = _sendResponsePlan.value[currentIndex.times(4).plus(event.dateIndex)].timeList[event.minuteIndex].not()
                if (sendResponsePlan.value[currentIndex.times(4).plus(event.dateIndex)].timeList[event.minuteIndex]) {
                    updateState { copy(clickCount = viewState.value.clickCount.plus(1)) }
                } else {
                    updateState { copy(clickCount = viewState.value.clickCount.minus(1)) }
                }
            }
        }
    }
}
