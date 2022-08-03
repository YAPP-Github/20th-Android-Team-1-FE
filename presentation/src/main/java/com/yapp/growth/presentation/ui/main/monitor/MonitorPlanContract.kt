package com.yapp.growth.presentation.ui.main.monitor

import com.yapp.growth.base.LoadState
import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState
import com.yapp.growth.domain.entity.Category
import com.yapp.growth.domain.entity.TimeTable
import com.yapp.growth.domain.entity.User
import com.yapp.growth.presentation.ui.main.fix.FixPlanContract

class MonitorPlanContract {
    data class MonitorPlanViewState(
        val bottomSheet: BottomSheet = BottomSheet.HIDE,
        val loadState: LoadState = LoadState.SUCCESS,
        val respondents: List<User> = emptyList(),
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
            "",
            "",
            Category(0,"",""),
        ),
        val currentClickTimeIndex: Pair<Int, Int> = -1 to -1,
        val currentClickUserData: List<User> = emptyList(),
        val planId: Long = -1,
    ) : ViewState {
        enum class BottomSheet {
            HIDE,
            RESPONDENT,
            PARTICIPANT
        }
    }

    sealed class MonitorPlanSideEffect : ViewSideEffect {
        object ShowBottomSheet : MonitorPlanSideEffect()
        object HideBottomSheet : MonitorPlanSideEffect()
        object NavigateToPreviousScreen : MonitorPlanSideEffect()
    }

    sealed class MonitorPlanEvent : ViewEvent {
        object OnClickBackButton : MonitorPlanEvent()
        object OnClickNextDayButton : MonitorPlanEvent()
        object OnClickPreviousDayButton : MonitorPlanEvent()
        object OnClickExitIcon : MonitorPlanEvent()
        data class OnClickTimeTable(val dateIndex: Int, val minuteIndex: Int) : MonitorPlanEvent()
        object OnClickErrorRetryButton : MonitorPlanEvent()
        object OnClickAvailableColorBox : MonitorPlanEvent()
    }
}
