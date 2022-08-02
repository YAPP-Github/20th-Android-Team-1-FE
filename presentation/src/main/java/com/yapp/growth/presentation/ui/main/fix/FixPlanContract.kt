package com.yapp.growth.presentation.ui.main.fix

import com.yapp.growth.base.LoadState
import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState
import com.yapp.growth.domain.entity.Category
import com.yapp.growth.domain.entity.TimeTable
import com.yapp.growth.domain.entity.User

class FixPlanContract {
    data class FixPlanViewState(
        val loadState: LoadState = LoadState.SUCCESS,
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
    ) : ViewState

    sealed class FixPlanSideEffect : ViewSideEffect {
        object ShowBottomSheet : FixPlanSideEffect()
        object HideBottomSheet : FixPlanSideEffect()
        data class NavigateToNextScreen(val planId: Long) : FixPlanSideEffect()
        object NavigateToPreviousScreen : FixPlanSideEffect()
    }

    sealed class FixPlanEvent : ViewEvent {
        object OnClickBackButton : FixPlanEvent()
        object OnClickNextDayButton : FixPlanEvent()
        object OnClickPreviousDayButton : FixPlanEvent()
        data class OnClickTimeTable(val dateIndex: Int, val minuteIndex: Int) : FixPlanEvent()
        data class OnClickFixButton(val date: String) : FixPlanEvent()
        object OnClickErrorRetryButton : FixPlanEvent()
    }
}
