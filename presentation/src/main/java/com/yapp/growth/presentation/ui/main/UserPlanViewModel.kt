package com.yapp.growth.presentation.ui.main

import androidx.lifecycle.viewModelScope
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.domain.entity.UserPlanStatus
import com.yapp.growth.domain.onError
import com.yapp.growth.domain.onSuccess
import com.yapp.growth.domain.repository.UserRepository
import com.yapp.growth.presentation.ui.main.UserPlanContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserPlanViewModel @Inject constructor(
    private val repository: UserRepository,
) : BaseViewModel<UserPlanViewState, UserPlanSideEffect, UserPlanEvent>(UserPlanViewState()) {

    private fun getUserPlanStatus(planId: Long) = viewModelScope.launch {
        repository.getUserPlanStatus(planId)
            .onSuccess { status ->
                when (status) {
                    UserPlanStatus.OWNER -> sendEffect({ UserPlanSideEffect.MoveToConfirmPlan(planId) })
                    UserPlanStatus.CONFIRMED -> sendEffect({ UserPlanSideEffect.MoveToAlreadyConfirmPlan })
                    UserPlanStatus.RESPONSE_ALREADY -> sendEffect({ UserPlanSideEffect.MoveToMonitorPlan(planId) })
                    UserPlanStatus.RESPONSE_MAXIMUM -> sendEffect({ UserPlanSideEffect.MoveToFulledPlan })
                    UserPlanStatus.RESPONSE_POSSIBLE -> sendEffect({ UserPlanSideEffect.MoveToRespondPlan(planId) })
                    UserPlanStatus.UNKNOWN -> { }
                }
            }
            .onError {

            }
    }

    fun getUserName(): String {
        return repository.getCachedUserInfo()?.userName ?: ""
    }


    override fun handleEvents(event: UserPlanEvent) {
        when(event) {
            is UserPlanEvent.GetUserPlanStatus -> getUserPlanStatus(event.planId)
            is UserPlanEvent.ChangeStatusBarColor -> updateState { copy(statusBarColor = event.color) }
            else -> {}
        }
    }

}
