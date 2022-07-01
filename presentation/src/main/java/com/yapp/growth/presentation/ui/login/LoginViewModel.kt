package com.yapp.growth.presentation.ui.login

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.yapp.growth.LoginSdk
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.domain.onError
import com.yapp.growth.domain.onSuccess
import com.yapp.growth.domain.runCatching
import com.yapp.growth.presentation.ui.login.LoginContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
) : BaseViewModel<LoginViewState, LoginSideEffect, LoginEvent>(
    LoginViewState()
) {

    @Inject
    lateinit var kakaoLoginSdk: LoginSdk

    override fun handleEvents(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnClickKakaoLoginButton -> requestLogin(event.context)
        }
    }

    private fun requestLogin(context: Context) {
        viewModelScope.launch {

            runCatching { kakaoLoginSdk.login(context) }
                .onSuccess { sendEffect({ LoginSideEffect.MoveToMain }) }
                .onError { sendEffect({ LoginSideEffect.LoginFailed }) }
        }
    }

}
