package com.yapp.growth.presentation.ui.login

import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState

class LoginContract {
    data class LoginViewState(
        val loginState: LoginState = LoginState.NONE
    ): ViewState

    sealed class LoginSideEffect: ViewSideEffect {

    }

    sealed class LoginEvent: ViewEvent {

    }

    enum class LoginState {
        NONE, SUCCESS, REQUIRED
    }
}
