package com.yapp.growth.presentation.ui.main

import androidx.compose.ui.graphics.Color
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState
import com.yapp.growth.domain.entity.Plan
import com.yapp.growth.domain.entity.UserPlanStatus
import com.yapp.growth.presentation.theme.BackgroundColor1

class PlanzContract {

    data class PlanzViewState(
        val loginState: LoginState = LoginState.NONE,
        val selectDayPlans: List<Plan.FixedPlan> = emptyList(),
        val selectionDay: CalendarDay = CalendarDay.today(),
        val userPlanStatus: UserPlanStatus = UserPlanStatus.UNKNOWN,
        val statusBarColor: Color = BackgroundColor1,
    ) : ViewState

    sealed class PlanzSideEffect : ViewSideEffect {
        object ShowBottomSheet : PlanzSideEffect()
        object HideBottomSheet : PlanzSideEffect()
        data class NavigateDetailPlanScreen(val planId: Int) : PlanzSideEffect()
        data class MoveToConfirmPlan(val planId: Long): PlanzSideEffect()
        data class MoveToRespondPlan(val planId: Long): PlanzSideEffect()
        data class MoveToMonitorPlan(val planId: Long): PlanzSideEffect()
        object MoveToAlreadyConfirmPlan: PlanzSideEffect()
        object MoveToFulledPlan: PlanzSideEffect()
        object MoveToLogin: PlanzSideEffect()
    }

    sealed class PlanzEvent : ViewEvent {
        data class OnPlanItemClicked(val planId: Int) : PlanzEvent()
        object OnBottomNavigationClickedWhenNotLogin : PlanzEvent()
        object OnBottomSheetExitClicked : PlanzEvent()
        data class ShowBottomSheet(
            val selectionDay: CalendarDay,
            val selectionDayPlans: List<Plan.FixedPlan>,
        ) : PlanzEvent()
        data class GetUserPlanStatus(val planId: Long): PlanzEvent()
        data class ChangeStatusBarColor(val color: Color): PlanzEvent()
    }

    enum class LoginState {
        NONE, LOGIN
    }
}
