package com.yapp.growth.presentation.ui.splash

import androidx.lifecycle.viewModelScope
import com.yapp.growth.LoginSdk
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.domain.onError
import com.yapp.growth.domain.runCatching
import com.yapp.growth.presentation.ui.splash.SplashContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
) : BaseViewModel<SplashViewState, SplashSideEffect, SplashEvent>(
    SplashViewState()
) {
    @Inject
    lateinit var kakaoLoginSdk: LoginSdk

    internal fun checkValidLoginToken() {
        viewModelScope.launch {
            runCatching {
                val isValidLoginToken = kakaoLoginSdk.isValidAccessToken()
                if (isValidLoginToken) sendEffect({ SplashSideEffect.MoveToMain })
                else sendEffect({ SplashSideEffect.LoginFailed })
            }.onError {
                sendEffect({ SplashSideEffect.LoginFailed })
            }

        }
    }

    override fun handleEvents(event: SplashEvent) {
        // NONE
    }
}
