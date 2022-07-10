package com.yapp.growth.presentation.ui.main.manage.confirm

import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState

class ConfirmPlanContract {
    data class ConfirmPlanViewState(
        val state: Boolean = false
    ) : ViewState

    sealed class ConfirmPlanSideEffect : ViewSideEffect {
        object ChangedList : ConfirmPlanSideEffect()
        object ShowBottomSheet : ConfirmPlanSideEffect()
        object HideBottomSheet : ConfirmPlanSideEffect()
    }

    sealed class ConfirmPlanEvent : ViewEvent {
        object OnClickNextDayButton : ConfirmPlanEvent()
        object OnClickPreviousDayButton : ConfirmPlanEvent()
        data class OnClickTimeTable(val dateIndex: Int, val minuteIndex: Int) : ConfirmPlanEvent()
        data class OnClickConfirmButton(val dateIndex: Int, val minuteIndex: Int) :
            ConfirmPlanEvent()
    }
}
