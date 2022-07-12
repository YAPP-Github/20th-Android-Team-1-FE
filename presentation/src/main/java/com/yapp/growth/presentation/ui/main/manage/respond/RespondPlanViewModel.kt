package com.yapp.growth.presentation.ui.main.manage.respond

import androidx.lifecycle.viewModelScope
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.SendingResponsePlan
import com.yapp.growth.domain.entity.ResponsePlan
import com.yapp.growth.domain.usecase.GetRespondUsersUseCase
import com.yapp.growth.presentation.ui.main.manage.respond.RespondPlanContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RespondPlanViewModel @Inject constructor(
    private val getRespondUsersUseCase: GetRespondUsersUseCase
): BaseViewModel<RespondPlanViewState, RespondPlanSideEffect, RespondPlanEvent>(
    RespondPlanViewState()
) {
    private val _sendResponsePlan = MutableStateFlow<List<SendingResponsePlan>>(emptyList())
    val sendResponsePlan: StateFlow<List<SendingResponsePlan>>
        get() = _sendResponsePlan.asStateFlow()

    private val _responsePlan = MutableStateFlow<ResponsePlan>(
        ResponsePlan(
            emptyList(), emptyList(), emptyList(), 0, "", "", emptyList(), emptyList()
        )
    )

    val responsePlan: StateFlow<ResponsePlan>
        get() = _responsePlan

    private val _clickCount = MutableStateFlow(0)
    val clickCount: StateFlow<Int>
        get() = _clickCount.asStateFlow()

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

    override fun handleEvents(event: RespondPlanEvent) {
        when (event) {
            is RespondPlanEvent.OnClickTimeTable -> {
                _sendResponsePlan.value[event.dateIndex].timeList[event.minuteIndex] = _sendResponsePlan.value[event.dateIndex].timeList[event.minuteIndex].not()
                if (sendResponsePlan.value[event.dateIndex].timeList[event.minuteIndex]) _clickCount.value += 1 else _clickCount.value -= 1
            }
            is RespondPlanEvent.OnClickNextDayButton -> {  }
            is RespondPlanEvent.OnClickPreviousDayButton -> {  }
            is RespondPlanEvent.OnClickRespondButton -> {  }
        }
    }
}
