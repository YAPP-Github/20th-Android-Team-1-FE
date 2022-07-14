package com.yapp.growth.presentation.ui.main.myPage

import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState

class MyPageContract {
    data class MyPageViewState(
        val loginState: LoginState = LoginState.NONE,
        val userName: String = "김정호",
    ) : ViewState

    sealed class MyPageSideEffect : ViewSideEffect {
        object MoveToLogin : MyPageSideEffect()
        object ExitMyPageScreen : MyPageSideEffect()
    }

    sealed class MyPageEvent : ViewEvent {
        object OnLogoutClicked : MyPageEvent()
        object OnSignUpClicked : MyPageEvent()
        object OnBackButtonClicked : MyPageEvent()
    }

    enum class LoginState {
        NONE, LOGIN
    }
}
