package com.yapp.growth.presentation.ui.main

import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState

class PlanzContract {
    object PlanzViewState : ViewState

    sealed class PlanzSideEffect : ViewSideEffect {
        object ShowBottomSheet : PlanzSideEffect()
        object HideBottomSheet : PlanzSideEffect()
    }

    sealed class PlanzEvent : ViewEvent {
        object OnBottomSheetExitClicked : PlanzEvent()
    }

}
