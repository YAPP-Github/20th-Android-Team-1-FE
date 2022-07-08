package com.yapp.growth.presentation.ui.main.manageplan.respondplan

import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState

class RespondPlanContract {
    data class RespondPlanViewState(
        val selectTimes: Boolean = false
    ): ViewState

    sealed class RespondPlanSideEffect: ViewSideEffect {

    }

    sealed class RespondPlanEvent: ViewEvent {
        data class OnClickTimeTable(val dateIndex: Int, val minuteIndex: Int): RespondPlanEvent()
        object OnClickNextDayButton: RespondPlanEvent()
        object OnClickPreviousDayButton: RespondPlanEvent()
        object OnClickRespondButton: RespondPlanEvent()
    }
}
