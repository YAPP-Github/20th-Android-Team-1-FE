package com.yapp.growth.presentation.ui.main.myPage.nickname

import com.yapp.growth.base.LoadState
import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState

class ModifyNickNameContract {
    data class ModifyViewState(
        val loadState: LoadState = LoadState.SUCCESS,
        val nickName: String = "",
        val nickNameHint: String = "",
        val isError: Boolean = true,
    ) : ViewState

    sealed class ModifyNickNameSideEffect : ViewSideEffect {
        object NavigateToPreviousScreen : ModifyNickNameSideEffect()
    }

    sealed class ModifyNickNameEvent : ViewEvent {
        data class FillInNickName(val nickName: String) : ModifyNickNameEvent()
        object ClearNickName : ModifyNickNameEvent()
        object OnClickModifyButton : ModifyNickNameEvent()
    }
}
