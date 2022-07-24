package com.yapp.growth.presentation.ui.main.manage.monitor

import androidx.lifecycle.SavedStateHandle
import com.yapp.growth.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import com.yapp.growth.presentation.ui.main.manage.monitor.MonitorPlanContract.*
import javax.inject.Inject

@HiltViewModel
class MonitorPlanViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
): BaseViewModel<MonitorPlanViewState, MonitorPlanSideEffect, MonitorPlanEvent>(
    MonitorPlanViewState()
) {

    private val planId: Long = savedStateHandle.get<Int>("planId")?.toLong() ?: 0L

    override fun handleEvents(event: MonitorPlanEvent) {
        when (event) {
            MonitorPlanEvent.OnClickBackButton -> TODO()
            MonitorPlanEvent.OnClickNextDayButton -> TODO()
            MonitorPlanEvent.OnClickPreviousDayButton -> TODO()
            is MonitorPlanEvent.OnClickTimeTable -> TODO()
        }
    }

}
