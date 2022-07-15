package com.yapp.growth.presentation.ui.createPlan.share

import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState

class ShareContract {
    data class ShareViewState(
        val shareUrl: String = "planz/link/sample/125", // TODO: 공유 URL
    ) : ViewState

    sealed class ShareSideEffect : ViewSideEffect {
        object FinishCreatePlan : ShareSideEffect()
        object ShowSnackBar : ShareSideEffect()
    }

    sealed class ShareEvent : ViewEvent {
        object OnClickExit : ShareEvent()
        object OnClickCopy : ShareEvent()
    }
}
