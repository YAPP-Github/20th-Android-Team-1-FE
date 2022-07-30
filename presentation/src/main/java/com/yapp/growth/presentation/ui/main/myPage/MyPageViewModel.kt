package com.yapp.growth.presentation.ui.main.myPage

import androidx.lifecycle.viewModelScope
import com.yapp.growth.LoginSdk
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.domain.onError
import com.yapp.growth.domain.onSuccess
import com.yapp.growth.domain.runCatching
import com.yapp.growth.domain.usecase.DeleteUserInfoUseCase
import com.yapp.growth.domain.usecase.GetUserInfoUseCase
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.ui.main.myPage.MyPageContract.LoginState
import com.yapp.growth.presentation.ui.main.myPage.MyPageContract.MyPageEvent
import com.yapp.growth.presentation.ui.main.myPage.MyPageContract.MyPageSideEffect
import com.yapp.growth.presentation.ui.main.myPage.MyPageContract.MyPageViewState
import com.yapp.growth.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val deleteUserInfoUseCase: DeleteUserInfoUseCase,
    private val resourcesProvider: ResourceProvider,
    private val kakaoLoginSdk: LoginSdk
) : BaseViewModel<MyPageViewState, MyPageSideEffect, MyPageEvent>(MyPageViewState()) {

    init {
        updateState { copy(loadState = MyPageContract.LoadState.Loading) }
        checkValidLoginToken()
    }
    
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
                withdraw()
            }
            is MyPageEvent.OnPositiveButtonClicked -> {
                updateState { copy(isDialogVisible = false) }
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
                            loadState = MyPageContract.LoadState.Success
                        )
                    }
                }
            }.onError {
                updateState {
                    copy(
                        loginState = LoginState.NONE,
                        loadState = MyPageContract.LoadState.Error
                    )
                }
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
                    sendEffect({ MyPageSideEffect.ShowToast(resourcesProvider.getString(R.string.my_page_logout_error_text)) })
                }
        }
    }

    private fun withdraw() {
        viewModelScope.launch {
            deleteUserInfoUseCase.invoke()
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

            if(cacheInfo == null) {
                getUserInfoUseCase.invoke()
                    .onSuccess {
                        updateState {
                            copy(
                                loadState = MyPageContract.LoadState.Success,
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
            } else {
                updateState {
                    copy(
                        loadState = MyPageContract.LoadState.Success,
                        userName = cacheInfo.userName
                    )
                }
            }
        }
    }
}
