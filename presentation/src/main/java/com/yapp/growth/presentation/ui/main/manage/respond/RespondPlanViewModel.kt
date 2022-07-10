package com.yapp.growth.presentation.ui.main.manage.respond

import androidx.lifecycle.viewModelScope
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.RespondPlan
import com.yapp.growth.domain.entity.RespondUsers
import com.yapp.growth.domain.usecase.GetRespondUsersUseCase
import com.yapp.growth.presentation.ui.main.manage.respond.RespondPlanContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RespondPlanViewModel @Inject constructor(
    private val getRespondUsersUseCase: GetRespondUsersUseCase
): BaseViewModel<RespondPlanViewState, RespondPlanSideEffect, RespondPlanEvent>(
    RespondPlanViewState()
) {
    private val _dates = MutableStateFlow<List<RespondPlan>>(emptyList())
    val dates: StateFlow<List<RespondPlan>>
        get() = _dates.asStateFlow()

    private val _respondUser = MutableStateFlow<RespondUsers>(
        RespondUsers(
            emptyList(), emptyList(), emptyList(), 0, "", "", emptyList(), emptyList()
        )
    )

    val respondUser: StateFlow<RespondUsers>
        get() = _respondUser

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

    override fun handleEvents(event: RespondPlanEvent) {
        when (event) {
            is RespondPlanEvent.OnClickTimeTable -> {
                _dates.value[event.dateIndex].timeList[event.minuteIndex] = _dates.value[event.dateIndex].timeList[event.minuteIndex].not()
                if (dates.value[event.dateIndex].timeList[event.minuteIndex]) _clickCount.value += 1 else _clickCount.value -= 1
            }
            is RespondPlanEvent.OnClickNextDayButton -> {  }
            is RespondPlanEvent.OnClickPreviousDayButton -> {  }
            is RespondPlanEvent.OnClickRespondButton -> {  }
        }
    }
}
