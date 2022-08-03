package com.yapp.growth.presentation.ui.main.myPage.nickname

import androidx.lifecycle.viewModelScope
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.base.LoadState
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.onError
import com.yapp.growth.domain.onSuccess
import com.yapp.growth.domain.usecase.GetUserInfoUseCase
import com.yapp.growth.domain.usecase.ModifyNickNameUseCase
import com.yapp.growth.presentation.ui.createPlan.title.MAX_LENGTH_TITLE
import dagger.hilt.android.lifecycle.HiltViewModel
import com.yapp.growth.presentation.ui.main.myPage.nickname.ModifyNickNameContract.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModifyNickNameViewModel @Inject constructor(
    private val userInfoUseCase: GetUserInfoUseCase,
    private val modifyNickNameUseCase: ModifyNickNameUseCase,
) : BaseViewModel<ModifyViewState, ModifyNickNameSideEffect, ModifyNickNameEvent>(
    ModifyViewState()
) {

    init {
        viewModelScope.launch {
            val userName = userInfoUseCase.getCachedUserInfo() ?: (userInfoUseCase.invoke() as? NetworkResult.Success)?.data
            userName?.let { updateState { copy(nickNameHint = it.userName) } }
        }
    }

    private fun reflectUpdatedState(nickName: String = viewState.value.nickName) {
        updateState {
            copy(
                nickName = nickName,
                isError = isOverflowed(nickName)
            )
        }
    }

    private fun isOverflowed(nickName: String): Boolean {
        return nickName.isBlank() || (nickName.length > MAX_LENGTH_TITLE)
    }

    private fun modifyNickName(nickName: String) = viewModelScope.launch {
        updateState { copy(loadState = LoadState.LOADING) }
        modifyNickNameUseCase.invoke(nickName)
            .onSuccess {
                updateState { copy(loadState = LoadState.SUCCESS) }
                sendEffect({ ModifyNickNameSideEffect.NavigateToPreviousScreen })
            }
            .onError {
                updateState { copy(loadState = LoadState.ERROR) }
            }
    }

    override fun handleEvents(event: ModifyNickNameEvent) {
        when (event) {
            ModifyNickNameEvent.ClearNickName -> reflectUpdatedState("")
            is ModifyNickNameEvent.FillInNickName -> reflectUpdatedState(event.nickName)
            ModifyNickNameEvent.OnClickModifyButton -> modifyNickName(viewState.value.nickName)
        }
    }

}
