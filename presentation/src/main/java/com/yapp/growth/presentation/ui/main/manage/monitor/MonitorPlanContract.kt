package com.yapp.growth.presentation.ui.main.manage.monitor

import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState
import com.yapp.growth.domain.entity.TimeTable
import com.yapp.growth.domain.entity.User

class MonitorPlanContract {
    data class MonitorPlanViewState(
        val timeTable: TimeTable = TimeTable(
            emptyList(),
            emptyList(),
            0,
            emptyList(),
            0,
            "",
            User(0, ""),
            "",
            "",
            emptyList(),
            emptyList(),
            ""
        ),
        val currentClickTimeIndex: Pair<Int, Int> = -1 to -1,
        val currentClickUserData: List<User> = emptyList(),
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
