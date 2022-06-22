package com.yapp.growth.presentation.ui.main.sample

import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.presentation.ui.main.sample.SampleContract.*

class SampleViewModel(
) : BaseViewModel<SampleViewState, SampleSideEffect, SampleEvent>(
    SampleViewState()
) {

    private fun anyFunction() {
        // anything do . . .
        sendEffect(
            { SampleSideEffect.NavigateToAnyScreen },
            { SampleSideEffect.ShowToast("This is Sample Data") }
        )
        updateState { copy(isLoading = false) }
    }

    override fun handleEvents(event: SampleEvent) {
        when (event) {
            is SampleEvent.OnAnyButtonClicked -> {
                updateState { copy(isLoading = true) }
                anyFunction()
            }
        }
    }
}