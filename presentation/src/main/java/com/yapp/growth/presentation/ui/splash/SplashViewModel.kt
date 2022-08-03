package com.yapp.growth.presentation.ui.splash

import androidx.lifecycle.viewModelScope
import com.yapp.growth.LoginSdk
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.domain.onError
import com.yapp.growth.domain.onSuccess
import com.yapp.growth.domain.runCatching
import com.yapp.growth.domain.usecase.GetUserInfoUseCase
import com.yapp.growth.presentation.ui.splash.SplashContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
) : BaseViewModel<SplashViewState, SplashSideEffect, SplashEvent>(
    SplashViewState()
) {
    @Inject
    lateinit var kakaoLoginSdk: LoginSdk

    internal fun checkValidLoginToken() = viewModelScope.launch {
        getUserInfoUseCase()
            .onSuccess {
                val token = kakaoLoginSdk.getAccessToken()
                token?.let { Timber.tag("카카오 토큰").d(it.accessToken) }
                sendEffect({ SplashSideEffect.MoveToMain })
            }
            .onError { sendEffect({ SplashSideEffect.LoginFailed }) }
    }


    override fun handleEvents(event: SplashEvent) {
        // NONE
    }
}
