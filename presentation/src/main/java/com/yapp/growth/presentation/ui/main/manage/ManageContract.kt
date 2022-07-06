package com.yapp.growth.presentation.ui.main.manage

import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState
import com.yapp.growth.domain.entity.Plan

class ManageContract {
    data class ManageViewState(
        val waitingPlans: List<Plan.WaitingPlan> = emptyList(),
        val fixedPlans: List<Plan.FixedPlan> = emptyList(),
    ) : ViewState

    sealed class ManageSideEffect : ViewSideEffect {
        object NavigateToCreateScreen : ManageSideEffect()
        data class NavigateToFixPlanScreen(val planId: Int) : ManageSideEffect()
        data class NavigateToMemberResponseScreen(val planId: Int) : ManageSideEffect()
        data class NavigateToInvitationScreen(val planId: Int) : ManageSideEffect()
        data class SwitchTab(val tabIndex: Int) : ManageSideEffect()
    }

    sealed class ManageEvent : ViewEvent {
        object OnClickCreateButton : ManageEvent()
        data class OnClickWaitingPlan(val planId: Int) : ManageEvent()
        data class OnClickFixedPlan(val planId: Int) : ManageEvent()
        data class OnClickTab(val tabIndex: Int) : ManageEvent()
    }
}
