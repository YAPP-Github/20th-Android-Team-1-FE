package com.yapp.growth.ui.splash

import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.ui.splash.SplashContract.*

class SplashViewModel(
) : BaseViewModel<SplashViewState, SplashSideEffect, SplashEvent>(
    SplashViewState()
) {

    override fun handleEvents(event: SplashEvent) {
        TODO("Not yet implemented")
    }
}