package com.yapp.growth.presentation.ui.createPlan.share

import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.presentation.ui.createPlan.share.ShareContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShareViewModel @Inject constructor(
) : BaseViewModel<ShareViewState, ShareSideEffect, ShareEvent>(ShareViewState()) {
    override fun handleEvents(event: ShareEvent) {
        when (event) {
            is ShareEvent.OnClickExit -> sendEffect({ ShareSideEffect.FinishCreatePlan })
            is ShareEvent.OnClickCopy -> {
                updateState { copy(snackBarType = ShareViewState.SnackBarType.SUCCESS) }
                sendEffect({ ShareSideEffect.CopyShareUrl })
            }
            is ShareEvent.OnClickShare -> {
                sendEffect({ ShareSideEffect.SendKakaoShareMessage })
            }
            is ShareEvent.FailToShare -> {
                updateState { copy(snackBarType = ShareViewState.SnackBarType.FAIL) }
                sendEffect({ ShareSideEffect.ShowFailToShareSnackBar })
            }
        }
    }
}
