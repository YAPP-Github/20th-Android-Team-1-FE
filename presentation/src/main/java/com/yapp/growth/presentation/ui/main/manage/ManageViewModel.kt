package com.yapp.growth.presentation.ui.main.manage

import androidx.lifecycle.viewModelScope
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.base.LoadState
import com.yapp.growth.domain.onError
import com.yapp.growth.domain.onSuccess
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
    override fun handleEvents(event: ManageEvent) {
        when (event) {
            is ManageEvent.InitManageScreen -> {
                getWaitingPlans()
                getFixedPlans()
            }
            is ManageEvent.OnClickCreateButton -> sendEffect({ ManageSideEffect.NavigateToCreateScreen })
            is ManageEvent.OnClickWaitingPlan -> {
                val selectedPlan =
                    viewState.value.waitingPlans.firstOrNull { it.id == event.planId }
                selectedPlan?.let { waitingPlan ->
                    if (waitingPlan.isLeader) sendEffect({
                        ManageSideEffect.NavigateToFixPlanScreen(event.planId)
                    })
                    else if (waitingPlan.isAlreadyReplied) sendEffect({
                        ManageSideEffect.NavigateToMonitorPlanScreen(event.planId)
                    })
                    else sendEffect({ ManageSideEffect.NavigateToMemberResponseScreen(event.planId) })
                }
            }
            is ManageEvent.OnClickFixedPlan -> sendEffect(
                { ManageSideEffect.NavigateToDetailPlanScreen(event.planId) }
            )
            is ManageEvent.OnClickTab -> sendEffect({ ManageSideEffect.SwitchTab(event.tabIndex) })
        }
    }

    private fun getWaitingPlans() = viewModelScope.launch {
        updateState { copy(waitingPlansLoadState = LoadState.LOADING) }
        getWaitingPlansUseCase()
            .onSuccess { waitingPlans ->
                updateState { copy(waitingPlansLoadState = LoadState.SUCCESS, waitingPlans = waitingPlans) }
            }.onError {
                updateState { copy(waitingPlansLoadState = LoadState.ERROR) }
            }
    }

    private fun getFixedPlans() = viewModelScope.launch {
        updateState { copy(fixedPlansLoadState = LoadState.LOADING) }
        getFixedPlansUseCase()
            .onSuccess { fixedPlans ->
                updateState { copy(fixedPlansLoadState = LoadState.SUCCESS, fixedPlans = fixedPlans) }
            }.onError {
                updateState { copy(fixedPlansLoadState = LoadState.ERROR) }
            }
    }
}
