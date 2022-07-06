package com.yapp.growth.presentation.ui.main.manage

import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.presentation.ui.main.manage.ManageContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManageViewModel @Inject constructor(

) : BaseViewModel<ManageViewState, ManageSideEffect, ManageEvent>(
    ManageViewState()
) {
    override fun handleEvents(event: ManageEvent) {
        when (event) {
            is ManageEvent.OnClickCreateButton -> sendEffect({ ManageSideEffect.NavigateToCreateScreen })
            is ManageEvent.OnClickWaitingPlan -> {
                val selectedPlan =
                    viewState.value.waitingPlans.firstOrNull { it.id == event.planId }
                selectedPlan?.let {
                    if (it.isLeader) sendEffect({ ManageSideEffect.NavigateToFixPlanScreen(event.planId) })
                    else sendEffect({ ManageSideEffect.NavigateToMemberResponseScreen(event.planId) })
                }
            }
            is ManageEvent.OnClickFixedPlan -> sendEffect(
                { ManageSideEffect.NavigateToInvitationScreen(event.planId) }
            )
            is ManageEvent.OnClickTab -> sendEffect({ ManageSideEffect.SwitchTab(event.tabIndex) })
        }
    }
}
