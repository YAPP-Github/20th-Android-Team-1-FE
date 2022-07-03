package com.yapp.growth.presentation.ui.main.manageplan.promisingplan

import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.domain.entity.Promising
import com.yapp.growth.presentation.ui.main.manageplan.promisingplan.PromisingContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PromisingPlanViewModel @Inject constructor(

): BaseViewModel<PromisingViewState, PromisingSideEffect, PromisingEvent>(
    PromisingViewState()
) {
    private val _timeList = mutableListOf<Promising>()
    val timeList: List<Promising>
        get() = _timeList.toList()

    private val year_df = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).parse(LocalDateTime.now().toString())

    val cal = Calendar.getInstance().apply {
        time = Date()
    }

    val df: DateFormat = SimpleDateFormat("HH:mm", Locale.KOREA)

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

        val list = listOf(
            Promising(year_df, booleanItems),
            Promising(year_df, booleanItems2),
            Promising(year_df, booleanItems),
            Promising(year_df, booleanItems2)
        )
        _timeList.addAll(list)
    }

    override fun handleEvents(event: PromisingEvent) {

    }
}
