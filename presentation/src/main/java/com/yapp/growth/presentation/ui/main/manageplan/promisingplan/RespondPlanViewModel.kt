package com.yapp.growth.presentation.ui.main.manageplan.promisingplan

import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.domain.entity.RespondPlan
import com.yapp.growth.presentation.ui.main.manageplan.promisingplan.RespondPlanContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RespondPlanViewModel @Inject constructor(

): BaseViewModel<PromisingViewState, PromisingSideEffect, PromisingEvent>(
    PromisingViewState()
) {
    private val _timeList = mutableListOf<RespondPlan>()
    val timeList: List<RespondPlan>
        get() = _timeList.toList()

    private val year_df = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).parse(LocalDateTime.now().toString())

    private val cal = Calendar.getInstance().apply {
        time = Date()
    }

    private val df: DateFormat = SimpleDateFormat("HH:mm", Locale.KOREA)

    init {
        getTime()
    }

    private fun getTime() {
        val booleanItems = listOf(true, false, true, false, true, false, true, false,
            true, false, true, false, true, false, true, false,true, false, true,
            false, true, false, true, false,true, false, true, false, true, false, true, false,).toMutableList()

        val booleanItems2 = listOf(false, true, false, true, false, true, false, true,
            false, true, false, true, false, true, false, true,false, true, false,
            true, false, true, false, true,true, false, true, false, true, false, true, false,).toMutableList()

        val hours = mutableListOf<String>().also { timesList ->
            repeat(booleanItems.size/2) {
                timesList.add(df.format(cal.time))
                cal.add(Calendar.HOUR, 1)
            }
        }.toList()


        val list = listOf(
            RespondPlan(year_df, hours, booleanItems),
            RespondPlan(year_df, hours, booleanItems2),
            RespondPlan(year_df, hours, booleanItems),
            RespondPlan(year_df, hours, booleanItems2)
        )
        _timeList.addAll(list)
    }

    override fun handleEvents(event: PromisingEvent) {
        when (event) {
            is PromisingEvent.OnClickTimeTable -> { updateState { copy(selectTimes = !event.temp) }}
            is PromisingEvent.OnClickNextButton -> {}
            is PromisingEvent.OnClickExitButton -> {}
        }
    }
}
