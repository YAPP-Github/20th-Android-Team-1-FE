package com.yapp.growth.presentation.ui.main.manage.respond

import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.domain.entity.RespondPlan
import com.yapp.growth.presentation.ui.main.manage.respond.RespondPlanContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RespondPlanViewModel @Inject constructor(

): BaseViewModel<RespondPlanViewState, RespondPlanSideEffect, RespondPlanEvent>(
    RespondPlanViewState()
) {
    private val _dates = MutableStateFlow<List<RespondPlan>>(emptyList())
    val dates: StateFlow<List<RespondPlan>>
        get() = _dates.asStateFlow()

    private val _clickCount = MutableStateFlow(0)
    val clickCount: StateFlow<Int>
        get() = _clickCount.asStateFlow()

    private var list1 = listOf<RespondPlan>()
    private var list2 = listOf<RespondPlan>()

    private val calHour = Calendar.getInstance().apply {
        time = Date()
    }

    private val calDay = Calendar.getInstance().apply {
        time = Date()
    }

    private val dfHour: DateFormat = SimpleDateFormat("HH:mm", Locale.KOREA)
    private val dfDay: DateFormat = SimpleDateFormat("M/d", Locale.KOREA)

    init {
        getTime()
    }

    private fun getTime() {
        val booleanArray = Array(20){ false }

        val dayList = mutableListOf<String>().also { dayList ->
            repeat(8) {
                dayList.add(dfDay.format(calDay.time))
                calDay.add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        val hourList = mutableListOf<String>().also { hourList ->
            repeat(booleanArray.size/2) {
                hourList.add(dfHour.format(calHour.time))
                calHour.add(Calendar.HOUR, 1)
            }
        }.toList()

        list1 = listOf(
            RespondPlan(dayList[0], hourList, booleanArray.copyOf().toMutableList()),
            RespondPlan(dayList[1], hourList, booleanArray.copyOf().toMutableList()),
            RespondPlan(dayList[2], hourList, booleanArray.copyOf().toMutableList()),
            RespondPlan(dayList[3], hourList, booleanArray.copyOf().toMutableList())
        )

        list2 = listOf(
            RespondPlan(dayList[4], hourList, booleanArray.copyOf().toMutableList()),
            RespondPlan(dayList[5], hourList, booleanArray.copyOf().toMutableList()),
            RespondPlan(dayList[6], hourList, booleanArray.copyOf().toMutableList()),
            RespondPlan(dayList[7], hourList, booleanArray.copyOf().toMutableList())
        )

        _dates.value = list1
    }

    override fun handleEvents(event: RespondPlanEvent) {
        when (event) {
            is RespondPlanEvent.OnClickTimeTable -> {
                _dates.value[event.dateIndex].timeList[event.minuteIndex] = _dates.value[event.dateIndex].timeList[event.minuteIndex].not()
                if (dates.value[event.dateIndex].timeList[event.minuteIndex]) _clickCount.value += 1 else _clickCount.value -= 1
            }
            is RespondPlanEvent.OnClickNextDayButton -> { _dates.value = list2 }
            is RespondPlanEvent.OnClickPreviousDayButton -> { _dates.value = list1 }
            is RespondPlanEvent.OnClickRespondButton -> {  }
        }
    }
}
