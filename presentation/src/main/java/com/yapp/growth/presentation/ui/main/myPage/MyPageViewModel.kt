package com.yapp.growth.presentation.ui.main.myPage

import androidx.lifecycle.viewModelScope
import com.yapp.growth.LoginSdk
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.domain.onError
import com.yapp.growth.domain.runCatching
import com.yapp.growth.presentation.ui.main.myPage.MyPageContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
) : BaseViewModel<MyPageViewState, MyPageSideEffect, MyPageEvent>(MyPageViewState()) {

    init {
        checkValidLoginToken()
    }

    @Inject
    lateinit var kakaoLoginSdk: LoginSdk

    override fun handleEvents(event: MyPageEvent) {
        when (event) {
            // TODO : 이용약관, 개인정보 처리 방침, 탈퇴하기 (다이얼로그)
            is MyPageEvent.OnLogoutClicked -> {
                sendEffect({ MyPageSideEffect.MoveToLogin })
                // logout()
            }
            is MyPageEvent.OnSignUpClicked -> {
                sendEffect({ MyPageSideEffect.MoveToLogin })
            }
            is MyPageEvent.OnWithDrawClicked -> {
                updateState { copy(isDialogVisible = true) }
            }
            is MyPageEvent.OnBackButtonClicked -> {
                sendEffect({ MyPageSideEffect.ExitMyPageScreen })
            }
            is MyPageEvent.OnNegativeButtonClicked -> {
                updateState { copy(isDialogVisible = false) }
            }
            is MyPageEvent.OnPositiveButtonClicked -> {
                sendEffect({ MyPageSideEffect.MoveToLogin })
                // withdraw()
            }
        }
    }

    private fun checkValidLoginToken() {
        viewModelScope.launch {
            runCatching {
                val isValidLoginToken = kakaoLoginSdk.isValidAccessToken()
                if (isValidLoginToken) updateState { copy(loginState = LoginState.LOGIN) }
                else updateState { copy(loginState = LoginState.NONE) }
            }.onError {
                updateState { copy(loginState = LoginState.NONE) }
            }
        }
    }
}
