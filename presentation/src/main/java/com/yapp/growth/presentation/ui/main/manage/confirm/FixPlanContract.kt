package com.yapp.growth.presentation.ui.main.manage.confirm

import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState
import com.yapp.growth.domain.entity.TimeTable
import com.yapp.growth.domain.entity.User

class FixPlanContract {
    data class FixPlanViewState(
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
            ""
        ),
        val currentClickTimeIndex: Pair<Int, Int> = -1 to -1,
        val currentClickUserData: List<User> = emptyList()
    ) : ViewState

    sealed class FixPlanSideEffect : ViewSideEffect {
        object ShowBottomSheet : FixPlanSideEffect()
        object HideBottomSheet : FixPlanSideEffect()
        object NavigateToNextScreen : FixPlanSideEffect()
        object NavigateToPreviousScreen : FixPlanSideEffect()
    }

    sealed class FixPlanEvent : ViewEvent {
        object OnClickBackButton : FixPlanEvent()
        object OnClickNextDayButton : FixPlanEvent()
        object OnClickPreviousDayButton : FixPlanEvent()
        data class OnClickTimeTable(val dateIndex: Int, val minuteIndex: Int) : FixPlanEvent()
        data class OnClickFixButton(val date: String) : FixPlanEvent()
    }
}
