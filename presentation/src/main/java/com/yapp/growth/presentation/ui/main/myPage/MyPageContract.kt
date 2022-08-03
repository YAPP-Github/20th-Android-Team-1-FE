package com.yapp.growth.presentation.ui.main.myPage

import com.yapp.growth.base.LoadState
import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState

class MyPageContract {
    data class MyPageViewState(
        val loadState: LoadState = LoadState.SUCCESS,
        val loginState: LoginState = LoginState.NONE,
        val userName: String = "",
        val isDialogVisible: Boolean = false,
    ) : ViewState

    sealed class MyPageSideEffect : ViewSideEffect {
        object MoveToLogin : MyPageSideEffect()
        object NavigateToTerms : MyPageSideEffect()
        object NavigateToPolicy : MyPageSideEffect()
        object ExitMyPageScreen : MyPageSideEffect()
        object ModifyNickName : MyPageSideEffect()
        data class ShowToast(val msg: String) : MyPageSideEffect()
    }

    sealed class MyPageEvent : ViewEvent {
        object InitMyPageScreen : MyPageEvent()
        object OnTermsClicked : MyPageEvent()
        object OnPolicyClicked : MyPageEvent()
        object OnLogoutClicked : MyPageEvent()
        object OnSignUpClicked : MyPageEvent()
        object OnWithDrawClicked : MyPageEvent()
        object OnBackButtonClicked : MyPageEvent()
        object OnNegativeButtonClicked : MyPageEvent()
        object OnPositiveButtonClicked : MyPageEvent()
        object OnClickModifyNickname : MyPageEvent()
    }

    enum class LoginState {
        NONE, LOGIN
    }
}
