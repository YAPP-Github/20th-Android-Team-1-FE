package com.yapp.growth.presentation.ui.main.myPage

import androidx.lifecycle.viewModelScope
import com.yapp.growth.LoginSdk
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.base.LoadState
import com.yapp.growth.domain.onError
import com.yapp.growth.domain.onSuccess
import com.yapp.growth.domain.runCatching
import com.yapp.growth.domain.usecase.GetUserInfoUseCase
import com.yapp.growth.domain.usecase.RemoveCachedUserInfoUseCase
import com.yapp.growth.domain.usecase.RemoveUserInfoUseCase
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
    private val removeCachedUserInfoUseCase: RemoveCachedUserInfoUseCase,
    private val removeUserInfoUseCase: RemoveUserInfoUseCase,
    private val resourcesProvider: ResourceProvider,
    private val kakaoLoginSdk: LoginSdk
) : BaseViewModel<MyPageViewState, MyPageSideEffect, MyPageEvent>(MyPageViewState()) {

    override fun handleEvents(event: MyPageEvent) {
        when (event) {
            is MyPageEvent.InitMyPageScreen -> {
                updateState { copy(loadState = LoadState.LOADING) }
                checkValidLoginToken()
            }
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
                withdraw()
            }
            is MyPageEvent.OnPositiveButtonClicked -> {
                updateState { copy(isDialogVisible = false) }
            }
            MyPageEvent.OnClickModifyNickname -> {
                sendEffect({ MyPageSideEffect.ModifyNickName })
            }
        }
    }

    private fun checkValidLoginToken() {
        viewModelScope.launch {
            runCatching {
                val isValidLoginToken = kakaoLoginSdk.isValidAccessToken()
                if (isValidLoginToken) {
                    updateState {
                        copy(
                            loginState = LoginState.LOGIN,
                        )
                    }
                    fetchUserInfo()
                } else {
                    updateState {
                        copy(
                            loginState = LoginState.NONE,
                            loadState = LoadState.SUCCESS
                        )
                    }
                }
            }.onError {
                updateState {
                    copy(
                        loginState = LoginState.NONE,
                        loadState = LoadState.ERROR
                    )
                }
            }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            runCatching { kakaoLoginSdk.logout() }
                .onSuccess {
                    removeCachedUserInfoUseCase.invoke()
                    sendEffect({ MyPageSideEffect.MoveToLogin })
                }
                .onError {
                    sendEffect({ MyPageSideEffect.ShowToast(resourcesProvider.getString(R.string.my_page_logout_error_text)) })
                }
        }
    }

    private fun withdraw() {
        viewModelScope.launch {
            removeUserInfoUseCase.invoke()
                .onSuccess {
                    runCatching { kakaoLoginSdk.withdraw() }
                        .onSuccess {
                            sendEffect({ MyPageSideEffect.MoveToLogin })
                        }
                        .onError {
                            sendEffect({ MyPageSideEffect.ShowToast(resourcesProvider.getString(R.string.my_page_withdraw_error_text)) })
                        }
                }
                .onError {
                    sendEffect({ MyPageSideEffect.ShowToast(resourcesProvider.getString(R.string.my_page_withdraw_error_text)) })
                }
        }
    }

    private fun fetchUserInfo() {
        viewModelScope.launch {
            val cacheInfo = getUserInfoUseCase.getCachedUserInfo()

            if (cacheInfo == null) {
                getUserInfoUseCase.invoke()
                    .onSuccess {
                        updateState {
                            copy(
                                loadState = LoadState.SUCCESS,
                                userName = it.userName
                            )
                        }
                    }
                    .onError {
                        updateState {
                            copy(
                                loadState = LoadState.ERROR,
                            )
                        }
                    }
            } else {
                updateState {
                    copy(
                        loadState = LoadState.SUCCESS,
                        userName = cacheInfo.userName
                    )
                }
            }
        }
    }
}
