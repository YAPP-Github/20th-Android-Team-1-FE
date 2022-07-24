package com.yapp.growth.presentation.ui.main.manage.respond

import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState
import com.yapp.growth.domain.entity.TimeTable
import com.yapp.growth.domain.entity.User

class RespondPlanContract {
    data class RespondPlanViewState(
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
        val clickCount: Int = 0,
        val availableResponse: Boolean = true,
    ) : ViewState

    sealed class RespondPlanSideEffect : ViewSideEffect {
        object NavigateToSendCompleteScreen : RespondPlanSideEffect()
        object NavigateToSendRejectScreen: RespondPlanSideEffect()
        object NavigateToPreviousScreen : RespondPlanSideEffect()
    }

    sealed class RespondPlanEvent : ViewEvent {
        object OnClickBackButton : RespondPlanEvent()
        data class OnClickTimeTable(val dateIndex: Int, val minuteIndex: Int) : RespondPlanEvent()
        object OnClickNextDayButton : RespondPlanEvent()
        object OnClickPreviousDayButton : RespondPlanEvent()
        object OnClickSendPlanButton : RespondPlanEvent()
        object OnClickClearButton : RespondPlanEvent()
        object OnClickRejectPlanButton : RespondPlanEvent()
    }
}
