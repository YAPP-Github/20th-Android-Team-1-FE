package com.yapp.growth.presentation.ui.main.sample

import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState

class SampleContract {

    data class SampleViewState(
        val isLoading: Boolean = false
    ) : ViewState

    sealed class SampleSideEffect : ViewSideEffect {
        object NavigateToAnyScreen : SampleSideEffect()
        data class ShowToast(val msg: String) : SampleSideEffect()
    }

    sealed class SampleEvent : ViewEvent {
        object OnAnyButtonClicked : SampleEvent()
    }

}