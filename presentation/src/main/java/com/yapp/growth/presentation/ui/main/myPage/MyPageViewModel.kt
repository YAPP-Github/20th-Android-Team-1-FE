package com.yapp.growth.presentation.ui.main.myPage

import androidx.lifecycle.viewModelScope
import com.yapp.growth.LoginSdk
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.domain.onError
import com.yapp.growth.domain.onSuccess
import com.yapp.growth.domain.runCatching
import com.yapp.growth.domain.usecase.GetUserInfoUseCase
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.ui.main.myPage.MyPageContract.LoginState
import com.yapp.growth.presentation.ui.main.myPage.MyPageContract.MyPageEvent
import com.yapp.growth.presentation.ui.main.myPage.MyPageContract.MyPageSideEffect
import com.yapp.growth.presentation.ui.main.myPage.MyPageContract.MyPageViewState
import com.yapp.growth.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val resourcesProvider: ResourceProvider,
) : BaseViewModel<MyPageViewState, MyPageSideEffect, MyPageEvent>(MyPageViewState()) {

    init {
        updateState { copy(loadState = MyPageContract.LoadState.Loading) }
        fetchUserInfo()
        checkValidLoginToken()
    }

    @Inject
    lateinit var kakaoLoginSdk: LoginSdk

    override fun handleEvents(event: MyPageEvent) {
        when (event) {
            is MyPageEvent.OnTermsClicked -> {
                sendEffect({ MyPageSideEffect.NavigateToTerms })
            }
            is MyPageEvent.OnPolicyClicked -> {
                sendEffect({ MyPageSideEffect.NavigateToPolicy })
            }
            is MyPageEvent.OnLogoutClicked -> {
                logout()
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

    private fun logout() {
        viewModelScope.launch {
            runCatching { kakaoLoginSdk.logout() }
                .onSuccess {
                    sendEffect({ MyPageSideEffect.MoveToLogin })
                }
                .onError {
                    sendEffect({ MyPageSideEffect.ShowToast(resourcesProvider.getString(R.string.my_page_logout_error_text))})
                }
        }
    }

    private fun fetchUserInfo() {
        viewModelScope.launch {
            getUserInfoUseCase.invoke()
                .onSuccess {
                    updateState {
                        copy(
                            loadState = MyPageContract.LoadState.Idle,
                            userName = it.userName
                        )
                    }
                }
                .onError {
                    updateState {
                        copy(
                            loadState = MyPageContract.LoadState.Error
                        )
                    }
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
                updateState { copy(loginState = LoginState.LOGIN) }
            }
        }
    }
}
