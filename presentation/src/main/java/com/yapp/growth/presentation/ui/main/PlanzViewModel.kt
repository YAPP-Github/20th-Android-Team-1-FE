package com.yapp.growth.presentation.ui.main

import androidx.lifecycle.viewModelScope
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.domain.entity.UserPlanStatus
import com.yapp.growth.domain.onError
import com.yapp.growth.domain.onSuccess
import com.yapp.growth.domain.repository.UserRepository
import com.yapp.growth.presentation.ui.main.PlanzContract.PlanzEvent
import com.yapp.growth.presentation.ui.main.PlanzContract.PlanzSideEffect
import com.yapp.growth.presentation.ui.main.PlanzContract.PlanzViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanzViewModel @Inject constructor(
    private val repository: UserRepository,
) : BaseViewModel<PlanzViewState, PlanzSideEffect, PlanzEvent>(
    PlanzViewState()
) {

    private fun getUserPlanStatus(planId: Long) = viewModelScope.launch {
        repository.getUserPlanStatus(planId)
            .onSuccess { status ->
                when (status) {
                    UserPlanStatus.OWNER -> sendEffect({ PlanzSideEffect.MoveToConfirmPlan(planId) })
                    UserPlanStatus.CONFIRMED -> sendEffect({ PlanzSideEffect.MoveToAlreadyConfirmPlan })
                    UserPlanStatus.RESPONSE_ALREADY -> sendEffect({ PlanzSideEffect.MoveToMonitorPlan(planId) })
                    UserPlanStatus.RESPONSE_MAXIMUM -> sendEffect({ PlanzSideEffect.MoveToFulledPlan })
                    UserPlanStatus.RESPONSE_POSSIBLE -> sendEffect({ PlanzSideEffect.MoveToRespondPlan(planId) })
                    UserPlanStatus.UNKNOWN -> { }
                }
            }
            .onError {

            }
    }

    fun getUserName(): String {
        return repository.getCachedUserInfo()?.userName ?: ""
    }

    override fun handleEvents(event: PlanzEvent) {
        when (event) {
            is PlanzEvent.OnPlanItemClicked -> {
                sendEffect({ PlanzSideEffect.NavigateDetailPlanScreen(event.planId) })
            }
            is PlanzEvent.OnBottomSheetExitClicked -> {
                sendEffect({ PlanzSideEffect.HideBottomSheet })
            }
            is PlanzEvent.ShowBottomSheet -> {
                updateState { copy(
                    selectDayPlans = event.selectionDayPlans,
                    selectionDay = event.selectionDay
                ) }
                sendEffect({ PlanzSideEffect.ShowBottomSheet })
            }
            is PlanzEvent.ChangeStatusBarColor -> updateState { copy(statusBarColor = event.color) }
            is PlanzEvent.GetUserPlanStatus -> getUserPlanStatus(event.planId)
        }
    }

    fun clearSelectionPlans() {
        updateState { copy(selectDayPlans = emptyList()) }
    }
}
