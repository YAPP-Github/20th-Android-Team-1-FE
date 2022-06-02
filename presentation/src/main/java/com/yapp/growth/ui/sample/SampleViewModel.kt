package com.yapp.growth.ui.sample

import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.domain.usecase.UseCase
import com.yapp.growth.ui.sample.SampleContract.*

class SampleViewModel(
    private val useCase: UseCase
) : BaseViewModel<SampleViewState, SampleSideEffect, SampleEvent>(
    SampleViewState()
) {

    fun anyFunction() {
        // anything do . . .
        setEffect { SampleSideEffect.NavigateToAnyScreen }
        setState { copy(isLoading = false) }
    }

    override fun handleEvents(event: SampleEvent) {
        when (event) {
            is SampleEvent.OnAnyButtonClicked -> {
                setState { copy(isLoading = true) }
                anyFunction()
            }
        }
    }
}