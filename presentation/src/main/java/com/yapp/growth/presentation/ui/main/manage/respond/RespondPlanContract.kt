package com.yapp.growth.presentation.ui.main.manage.respond

import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState
import com.yapp.growth.domain.entity.ResponsePlan
import com.yapp.growth.domain.entity.SendingResponsePlan
import com.yapp.growth.domain.entity.User

class RespondPlanContract {
    data class RespondPlanViewState(
        val responsePlan: ResponsePlan = ResponsePlan(
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
        val clickCount: Int = 0
    ) : ViewState

    sealed class RespondPlanSideEffect : ViewSideEffect {

    }

    sealed class RespondPlanEvent : ViewEvent {
        data class OnClickTimeTable(val dateIndex: Int, val minuteIndex: Int) : RespondPlanEvent()
        object OnClickNextDayButton : RespondPlanEvent()
        object OnClickPreviousDayButton : RespondPlanEvent()
        object OnClickRespondButton : RespondPlanEvent()
    }
}
