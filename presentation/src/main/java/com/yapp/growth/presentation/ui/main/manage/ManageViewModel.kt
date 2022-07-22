package com.yapp.growth.presentation.ui.main.manage

import androidx.lifecycle.viewModelScope
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.usecase.GetWaitingPlansUseCase
import com.yapp.growth.presentation.ui.main.manage.ManageContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ManageViewModel @Inject constructor(
    private val getWaitingPlansUseCase: GetWaitingPlansUseCase,
) : BaseViewModel<ManageViewState, ManageSideEffect, ManageEvent>(
    ManageViewState()
) {
    init {
        viewModelScope.launch {
            getWaitingPlans()
        }
    }

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

    private suspend fun getWaitingPlans() {
        when(val result = getWaitingPlansUseCase()) {
            is NetworkResult.Success -> {
                updateState {
                    copy(waitingPlans = result.data)
                }
            }
            is NetworkResult.Error -> {

            }
            is NetworkResult.Loading -> {

            }
        }
    }
}
