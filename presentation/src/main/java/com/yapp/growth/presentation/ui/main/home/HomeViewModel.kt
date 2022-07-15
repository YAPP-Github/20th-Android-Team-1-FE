package com.yapp.growth.presentation.ui.main.home

import androidx.lifecycle.viewModelScope
import com.yapp.growth.LoginSdk
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.domain.onError
import com.yapp.growth.domain.runCatching
import com.yapp.growth.presentation.ui.main.home.HomeContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
) : BaseViewModel<HomeViewState, HomeSideEffect, HomeEvent>(
    HomeViewState()
) {

    init {
        checkValidLoginToken()
    }

    @Inject
    lateinit var kakaoLoginSdk: LoginSdk

    override fun handleEvents(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnCalendarDayClicked -> { sendEffect({ HomeSideEffect.ShowBottomSheet }) }
            is HomeEvent.OnBottomSheetExitClicked -> { sendEffect({ HomeSideEffect.HideBottomSheet }) }
            is HomeEvent.OnTodayPlanItemClicked -> { sendEffect({ HomeSideEffect.NavigateDetailPlanScreen }) }
            is HomeEvent.OnUserImageButtonClicked -> { sendEffect({ HomeSideEffect.NavigateToInfoScreen }) }
            is HomeEvent.OnTodayPlanExpandedClicked -> { updateState { copy(isTodayPlanExpanded = !isTodayPlanExpanded) } }
            is HomeEvent.OnMonthlyPlanExpandedClicked -> { updateState { copy(isMonthlyPlanExpanded = !isMonthlyPlanExpanded) } }
            is HomeEvent.OnMonthlyPlanModeClicked -> { updateMonthlyPlanModeState( viewState.value.monthlyPlanMode ) }
        }
    }

    private fun updateMonthlyPlanModeState(monthlyPlanModeState: MonthlyPlanModeState) {
        when (monthlyPlanModeState) {
            MonthlyPlanModeState.CALENDAR -> {
                updateState { copy( monthlyPlanMode = MonthlyPlanModeState.TEXT) }
            }
            MonthlyPlanModeState.TEXT -> {
                updateState { copy( monthlyPlanMode = MonthlyPlanModeState.CALENDAR) }
            }
        }
    }

    private fun checkValidLoginToken() {
        viewModelScope.launch {
            runCatching {
                val isValidLoginToken = kakaoLoginSdk.isValidAccessToken()
                if (isValidLoginToken) updateState { copy(loginState = LoginState.LOGIN) }
                else updateState { copy(loginState = LoginState.NONE) }
            }.onError {
                updateState { copy(loginState = LoginState.LOGIN) }
            }
        }
    }
}
