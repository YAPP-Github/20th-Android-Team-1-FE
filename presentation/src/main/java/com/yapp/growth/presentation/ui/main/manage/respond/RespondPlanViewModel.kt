package com.yapp.growth.presentation.ui.main.manage.respond

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.TimeCheckedOfDay
import com.yapp.growth.domain.entity.TimeTable
import com.yapp.growth.domain.entity.User
import com.yapp.growth.domain.onError
import com.yapp.growth.domain.onSuccess
import com.yapp.growth.domain.usecase.GetRespondUsersUseCase
import com.yapp.growth.domain.usecase.SendRejectPlanUseCase
import com.yapp.growth.domain.usecase.SendRespondPlanUseCase
import com.yapp.growth.presentation.ui.main.manage.respond.RespondPlanContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RespondPlanViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getRespondUsersUseCase: GetRespondUsersUseCase,
    private val sendRespondPlanUseCase: SendRespondPlanUseCase,
    private val sendRejectPlanUseCase: SendRejectPlanUseCase,
): BaseViewModel<RespondPlanViewState, RespondPlanSideEffect, RespondPlanEvent>(
    RespondPlanViewState()
) {

    private val _timeCheckedOfDays = MutableStateFlow<List<TimeCheckedOfDay>>(emptyList())
    val timeCheckedOfDays: StateFlow<List<TimeCheckedOfDay>>
        get() = _timeCheckedOfDays.asStateFlow()

    private var originalTable: TimeTable = TimeTable(emptyList(), emptyList(), 0, emptyList(), 0, "", User(0, ""), "", "", emptyList(), emptyList(), "")
    private var currentIndex = 0
    private var planId: Long = savedStateHandle.get<Int>("planId")?.toLong() ?: 0L

    init {
        loadRespondUsers(planId)
    }

    private fun loadRespondUsers(promisingKey: Long) {
        viewModelScope.launch {
            val result = (getRespondUsersUseCase.invoke(promisingKey) as? NetworkResult.Success)?.data
            result?.let {
                originalTable = it
                checkAvailableResponse(it)
                makeRespondList(it)

                val sliceTimeTable = if (it.availableDates.size >= 4) {
                    it.copy(availableDates = it.availableDates.subList(0, 4))
                } else {
                    it.copy(availableDates = it.availableDates.subList(0, it.availableDates.size))
                }

                updateState {
                    copy(timeTable = sliceTimeTable)
                }
            }
        }
    }

    private fun checkAvailableResponse(timeTable: TimeTable) {
        if (timeTable.users.size >= 10) updateState { copy(availableResponse = false) }
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
        val sliceCreateTimeTable: TimeTable = originalTable.copy(availableDates = originalTable.availableDates.subList(fromIndex, toIndex))
        updateState {
            copy(timeTable = sliceCreateTimeTable)
        }
    }

    private fun previousDay() = viewModelScope.launch(Dispatchers.Default) {
        if (currentIndex == 0) return@launch
        currentIndex -= 1
        val fromIndex = currentIndex.times(4)
        val toIndex = fromIndex.plus(4)

        val temp: TimeTable = originalTable.copy(availableDates = originalTable.availableDates.subList(fromIndex, toIndex))
        updateState {
            copy(timeTable = temp)
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

        _timeCheckedOfDays.value = temp
    }

    private fun clearTimeCheckedOfDays() {
        _timeCheckedOfDays.value.forEach {
            it.timeList.map { false }
        }
    }

    private fun sendRespondPlan(planId: Long, timeCheckedOfDays: List<TimeCheckedOfDay>) =
        viewModelScope.launch {
            sendRespondPlanUseCase.invoke(planId, timeCheckedOfDays)
                .onSuccess {
                    sendEffect({ RespondPlanSideEffect.NavigateToSendCompleteScreen })
                }
                .onError {
                    Timber.tag("API ERROR").e(it)
                }
        }

    private fun sendRejectPlan(planId: Long) =
        viewModelScope.launch {
            sendRejectPlanUseCase.invoke(planId)
                .onSuccess {
                    sendEffect({ RespondPlanSideEffect.NavigateToSendRejectScreen })
                }
                .onError {
                    Timber.tag("API ERROR").e(it)
                }
        }

    override fun handleEvents(event: RespondPlanEvent) {
        when (event) {
            is RespondPlanEvent.OnClickBackButton -> { sendEffect({ RespondPlanSideEffect.NavigateToPreviousScreen }) }
            is RespondPlanEvent.OnClickTimeTable -> {
                _timeCheckedOfDays.value[currentIndex.times(4).plus(event.dateIndex)].timeList[event.minuteIndex] = _timeCheckedOfDays.value[currentIndex.times(4).plus(event.dateIndex)].timeList[event.minuteIndex].not()
                if (timeCheckedOfDays.value[currentIndex.times(4).plus(event.dateIndex)].timeList[event.minuteIndex]) {
                    updateState { copy(clickCount = viewState.value.clickCount.plus(1)) }
                } else {
                    updateState { copy(clickCount = viewState.value.clickCount.minus(1)) }
                }
            }
            is RespondPlanEvent.OnClickNextDayButton -> nextDay()
            is RespondPlanEvent.OnClickPreviousDayButton -> previousDay()
            is RespondPlanEvent.OnClickSendPlanButton -> sendRespondPlan(planId, timeCheckedOfDays.value)
            is RespondPlanEvent.OnClickClearButton -> clearTimeCheckedOfDays()
            is RespondPlanEvent.OnClickRejectPlanButton -> sendRejectPlan(planId)
        }
    }
}
