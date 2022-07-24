package com.yapp.growth.presentation.ui.main.manage

import androidx.lifecycle.viewModelScope
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.usecase.GetFixedPlansUseCase
import com.yapp.growth.domain.usecase.GetWaitingPlansUseCase
import com.yapp.growth.presentation.ui.main.manage.ManageContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ManageViewModel @Inject constructor(
    private val getWaitingPlansUseCase: GetWaitingPlansUseCase,
    private val getFixedPlansUseCase: GetFixedPlansUseCase,
) : BaseViewModel<ManageViewState, ManageSideEffect, ManageEvent>(
    ManageViewState()
) {
    init {
        getWaitingPlans()
        getFixedPlans()
    }

    override fun handleEvents(event: ManageEvent) {
        when (event) {
            is ManageEvent.OnClickCreateButton -> sendEffect({ ManageSideEffect.NavigateToCreateScreen })
            is ManageEvent.OnClickWaitingPlan -> {
                val selectedPlan =
                    viewState.value.waitingPlans.firstOrNull { it.id == event.planId }
                selectedPlan?.let { waitingPlan ->
                    if (waitingPlan.isLeader) sendEffect({ ManageSideEffect.NavigateToFixPlanScreen(event.planId) })
                    else if (waitingPlan.isAlreadyReplied) sendEffect({ ManageSideEffect.NavigateToMonitorPlanScreen(event.planId) })
                    else sendEffect({ ManageSideEffect.NavigateToMemberResponseScreen(event.planId) })
                }
            }
            is ManageEvent.OnClickFixedPlan -> sendEffect(
                { ManageSideEffect.NavigateToInvitationScreen(event.planId) }
            )
            is ManageEvent.OnClickTab -> sendEffect({ ManageSideEffect.SwitchTab(event.tabIndex) })
        }
    }

    private fun getWaitingPlans() {
        viewModelScope.launch {
            when (val result = getWaitingPlansUseCase()) {
                is NetworkResult.Success -> {
                    updateState {
                        copy(waitingPlans = result.data)
                    }
                    Timber.w("waitingPlans = ${result.data}")
                }
                is NetworkResult.Error -> {
                    Timber.w("waitingPlans error = ${result.exception}")
                }
                is NetworkResult.Loading -> {

                }
            }
        }
    }

    private fun getFixedPlans() {
        viewModelScope.launch {
            when (val result = getFixedPlansUseCase()) {
                is NetworkResult.Success -> {
                    updateState {
                        copy(fixedPlans = result.data)
                    }
                    Timber.w("fixedPlans = ${result.data}")
                }
                is NetworkResult.Error -> {
                    Timber.w("fixedPlans error = ${result.exception}")
                }
                is NetworkResult.Loading -> {

                }
            }
        }
    }
}
