package com.yapp.growth.presentation.ui.createPlan.share

import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState

class ShareContract {
    data class ShareViewState(
        val shareUrl: String = "",
        val snackBarType: SnackBarType = SnackBarType.SUCCESS,
        val planId: Long? = null,
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
