package com.yapp.growth.presentation.ui.createPlan.share

import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState

class ShareContract {
    data class ShareViewState(
        val shareUrl: String = "planz/link/sample/125", // TODO: 공유 URL
        val snackBarType: SnackBarType = SnackBarType.SUCCESS,
    ) : ViewState {
        enum class SnackBarType {
            SUCCESS,
            FAIL
        }
    }

    sealed class ShareSideEffect : ViewSideEffect {
        object FinishCreatePlan : ShareSideEffect()
        object CopyShareUrl : ShareSideEffect()
        object ShowSuccessSnackBar : ShareSideEffect()
        object ShowFailToShareSnackBar : ShareSideEffect()
        object SendKakaoShareMessage : ShareSideEffect()
    }

    sealed class ShareEvent : ViewEvent {
        object OnClickExit : ShareEvent()
        object OnClickCopy : ShareEvent()
        object OnClickShare : ShareEvent()
        object FailToShare : ShareEvent()
    }
}
