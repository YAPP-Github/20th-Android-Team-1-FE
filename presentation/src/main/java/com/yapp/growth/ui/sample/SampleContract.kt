package com.yapp.growth.ui.sample

import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState

class SampleContract {

    data class SampleViewState(
        val isLoading: Boolean = false
    ) : ViewState

    sealed class SampleViewSideEffect : ViewSideEffect {
        object NavigateToAnyScreen : SampleViewSideEffect()
        data class ShowToast(val msg: String) : SampleViewSideEffect()
    }

    sealed class SampleViewEvent : ViewEvent {
        object OnAnyButtonClicked : SampleViewEvent()
    }

}