package com.yapp.growth.presentation.ui.main.manage.monitor

import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState

class MonitorPlanContract {
    data class MonitorPlanViewState(
        val temp: Boolean = true,
    ) : ViewState

    sealed class MonitorPlanSideEffect : ViewSideEffect {
        object ShowBottomSheet : MonitorPlanSideEffect()
        object HideBottomSheet : MonitorPlanSideEffect()
        object NavigateToPreviousScreen : MonitorPlanSideEffect()
    }

    sealed class MonitorPlanEvent : ViewEvent {
        object OnClickBackButton : MonitorPlanEvent()
        object OnClickNextDayButton : MonitorPlanEvent()
        object OnClickPreviousDayButton : MonitorPlanEvent()
        data class OnClickTimeTable(val dateIndex: Int, val minuteIndex: Int) : MonitorPlanEvent()
    }
}
