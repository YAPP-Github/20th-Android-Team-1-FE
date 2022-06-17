package com.yapp.growth.ui.splash

import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.ui.splash.SplashContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
) : BaseViewModel<SplashViewState, SplashSideEffect, SplashEvent>(
    SplashViewState()
) {

    // TODO : 로그인 여부에 따라 상태 변경 필요 (정호)
    private fun setLoginState() {
        // updateState { copy(loginState = LoginState.REQUIRED) }
        // updateState { copy(loginState = LoginState.SUCCESS) }
    }

    override fun handleEvents(event: SplashEvent) {
        // NONE
    }
}