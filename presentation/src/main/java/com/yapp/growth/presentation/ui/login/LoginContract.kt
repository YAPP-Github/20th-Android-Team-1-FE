package com.yapp.growth.presentation.ui.login

import android.content.Context
import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState

class LoginContract {
    data class LoginViewState(
        val loginState: LoginState = LoginState.NONE
    ): ViewState

    sealed class LoginSideEffect: ViewSideEffect {
        object MoveToMain: LoginSideEffect()
        object LoginFailed: LoginSideEffect()
    }

    sealed class LoginEvent: ViewEvent {
        data class OnClickKakaoLoginButton(val context: Context): LoginEvent()
    }

    enum class LoginState {
        NONE, SUCCESS, REQUIRED
    }
}
