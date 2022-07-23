package com.yapp.growth.presentation.ui.main.manage.confirm

import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState
import com.yapp.growth.domain.entity.TimeTable
import com.yapp.growth.domain.entity.User
import com.yapp.growth.presentation.ui.createPlan.timetable.CreateTimeTableContract

class ConfirmPlanContract {
    data class ConfirmPlanViewState(
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
        val currentClickUserData: List<User> = emptyList()
    ) : ViewState

    sealed class ConfirmPlanSideEffect : ViewSideEffect {
        object ShowBottomSheet : ConfirmPlanSideEffect()
        object HideBottomSheet : ConfirmPlanSideEffect()
        object NavigateToNextScreen : ConfirmPlanSideEffect()
        object NavigateToPreviousScreen : ConfirmPlanSideEffect()
    }

    sealed class ConfirmPlanEvent : ViewEvent {
        object OnClickBackButton : ConfirmPlanEvent()
        object OnClickNextDayButton : ConfirmPlanEvent()
        object OnClickPreviousDayButton : ConfirmPlanEvent()
        data class OnClickTimeTable(val dateIndex: Int, val minuteIndex: Int) : ConfirmPlanEvent()
        data class OnClickConfirmButton(val date: String) : ConfirmPlanEvent()
    }
}
