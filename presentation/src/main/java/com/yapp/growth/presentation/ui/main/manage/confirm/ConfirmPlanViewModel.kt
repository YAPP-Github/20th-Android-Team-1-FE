package com.yapp.growth.presentation.ui.main.manage.confirm

import androidx.lifecycle.viewModelScope
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.TimeCheckedOfDay
import com.yapp.growth.domain.entity.TimeTable
import com.yapp.growth.domain.entity.User
import com.yapp.growth.domain.onError
import com.yapp.growth.domain.onSuccess
import com.yapp.growth.domain.usecase.GetRespondUsersUseCase
import com.yapp.growth.domain.usecase.SendConfirmPlanUseCase
import com.yapp.growth.presentation.ui.main.manage.confirm.ConfirmPlanContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmPlanViewModel @Inject constructor(
    private val getRespondUsersUseCase: GetRespondUsersUseCase,
    private val sendConfirmPlanUseCase: SendConfirmPlanUseCase
) : BaseViewModel<ConfirmPlanViewState, ConfirmPlanSideEffect, ConfirmPlanEvent>(
    ConfirmPlanViewState()
) {

    private var originalTable: TimeTable = TimeTable(emptyList(), emptyList(), 0, emptyList(), 0, "", User(0, ""), "", "", emptyList(), emptyList(), "")
    private var currentIndex = 0
    private val promisingId: Long = 25

    init {
        loadRespondUsers(promisingId)
    }

    private fun loadRespondUsers(promisingId: Long) {
        viewModelScope.launch {
            val result = (getRespondUsersUseCase.invoke(promisingId) as? NetworkResult.Success)?.data
            result?.let {
                originalTable = it
                makeRespondList(it)
                val sliceTimeTable: TimeTable = it.copy(availableDates = it.availableDates.subList(0,4))
                updateState {
                    copy(timeTable = sliceTimeTable)
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

            sendEffect({
                ConfirmPlanSideEffect.ShowBottomSheet
            })
        }
    }

    private fun sendConfirmPlan(date: String) = viewModelScope.launch {
        sendConfirmPlanUseCase.invoke(promisingId, date)
            .onSuccess {
                println(it)
            }
            .onError {
                print(it)
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

    override fun handleEvents(event: ConfirmPlanEvent) {
        when (event) {
            ConfirmPlanEvent.OnClickNextDayButton -> {
                initCurrentClickTimeIndex()
                nextDay()
            }
            ConfirmPlanEvent.OnClickPreviousDayButton -> {
                initCurrentClickTimeIndex()
                previousDay()
            }
            ConfirmPlanEvent.OnClickBackButton -> { sendEffect({ ConfirmPlanSideEffect.NavigateToPreviousScreen }) }
            is ConfirmPlanEvent.OnClickConfirmButton -> { sendConfirmPlan(event.date) }
            is ConfirmPlanEvent.OnClickTimeTable -> {
                updateState {
                    copy(currentClickTimeIndex = event.dateIndex to event.minuteIndex)
                }
                filterCurrentSelectedUser(event.dateIndex, event.minuteIndex)
            }
        }
    }
}
