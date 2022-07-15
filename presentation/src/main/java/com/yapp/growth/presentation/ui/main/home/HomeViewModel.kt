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
) : BaseViewModel<HomeViewState, HomeSideEffect, HomeEvent>(HomeViewState()) {

    init {
        checkValidLoginToken()
    }

    @Inject
    lateinit var kakaoLoginSdk: LoginSdk

    override fun handleEvents(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnCalendarDayClicked -> {
                sendEffect({ HomeSideEffect.OpenBottomSheet })
            }
            is HomeEvent.OnTodayPlanItemClicked -> {
                sendEffect({ HomeSideEffect.NavigateDetailPlanScreen })
            }
            is HomeEvent.OnUserImageButtonClicked -> {
                sendEffect({ HomeSideEffect.NavigateToInfoScreen })
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
                updateState { copy(loginState = LoginState.NONE) }
            }
        }
    }
}
