package com.yapp.growth.presentation.ui.splash

import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.presentation.ui.splash.SplashContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
) : BaseViewModel<SplashViewState, SplashSideEffect, SplashEvent>(
    SplashViewState()
) {
    // TODO : 임시로 초기값은 무조건 로그인 화면으로 가게 지정
    init {
        updateState { copy(loginState = LoginState.REQUIRED) }
    }

    // TODO : 로그인 여부에 따라 상태 변경 필요
    private fun setLoginState() {
        // updateState { copy(loginState = LoginState.REQUIRED) }
        // updateState { copy(loginState = LoginState.SUCCESS) }
    }

    override fun handleEvents(event: SplashEvent) {
        // NONE
    }
}