package com.yapp.growth.presentation.ui.login

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.yapp.growth.LoginSdk
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.domain.onError
import com.yapp.growth.domain.onSuccess
import com.yapp.growth.domain.runCatching
import com.yapp.growth.domain.usecase.GetUserInfoUseCase
import com.yapp.growth.domain.usecase.SignUpUseCase
import com.yapp.growth.presentation.ui.login.LoginContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
) : BaseViewModel<LoginViewState, LoginSideEffect, LoginEvent>(
    LoginViewState()
) {

    @Inject
    lateinit var kakaoLoginSdk: LoginSdk

    private fun requestKakaoLogin(context: Context) = viewModelScope.launch {
        runCatching { kakaoLoginSdk.login(context) }
            .onSuccess { requestGetUserInfo() }
            .onError { sendEffect({ LoginSideEffect.LoginFailed }) }
    }


    private fun requestGetUserInfo() = viewModelScope.launch {
        getUserInfoUseCase()
            .onSuccess { sendEffect({ LoginSideEffect.MoveToMain }) }
            .onError { requestSignUp() }
    }

    private fun requestSignUp() = viewModelScope.launch {
        signUpUseCase()
            .onSuccess {
                sendEffect({ LoginSideEffect.MoveToMain })
            }
            .onError {
                sendEffect({ LoginSideEffect.LoginFailed })
            }
    }

    override fun handleEvents(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnClickKakaoLoginButton -> requestKakaoLogin(event.context)
            is LoginEvent.OnClickNonLoginButton -> sendEffect({ LoginSideEffect.MoveToMain })
        }
    }

}
