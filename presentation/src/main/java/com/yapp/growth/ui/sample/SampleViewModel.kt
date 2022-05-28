package com.yapp.growth.ui.sample

import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.domain.usecase.UseCase
import com.yapp.growth.ui.sample.SampleContract.*

class SampleViewModel(
    private val useCase: UseCase
) : BaseViewModel<SampleViewState, SampleViewSideEffect, SampleViewEvent>(
    SampleViewState()
) {

    fun anyFunction() {
        // anything do . . .
        setEffect { SampleViewSideEffect.NavigateToAnyScreen }
        setState { copy(isLoading = false) }
    }

    override fun handleEvents(event: SampleViewEvent) {
        when (event) {
            is SampleViewEvent.OnAnyButtonClicked -> {
                setState { copy(isLoading = true) }
                anyFunction()
            }
        }
    }
}