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

    init {
        loadCreateTimeTable("860fe8ec-55c4-43b1-81d6-ccb2549f9d51")
    }

    fun loadCreateTimeTable(uuid: String) = viewModelScope.launch {
        val result = (getCreateTimeTableUseCase.invoke(uuid) as? NetworkResult.Success)?.data
        result?.let {
            makeRespondList(it)
            updateState {
                copy(createTimeTable = it)
            }
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
            CreateTimeTableEvent.OnClickNextDayButton -> TODO()
            CreateTimeTableEvent.OnClickPreviousDayButton -> TODO()
            is CreateTimeTableEvent.OnClickTimeTable -> {
                _sendResponsePlan.value[event.dateIndex].timeList[event.minuteIndex] = _sendResponsePlan.value[event.dateIndex].timeList[event.minuteIndex].not()
                if (sendResponsePlan.value[event.dateIndex].timeList[event.minuteIndex]) {
                    updateState { copy(clickCount = viewState.value.clickCount.plus(1)) }
                } else {
                    updateState { copy(clickCount = viewState.value.clickCount.minus(1)) }
                }
            }
        }
    }
}
