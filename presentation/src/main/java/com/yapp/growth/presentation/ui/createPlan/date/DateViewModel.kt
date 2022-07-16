package com.yapp.growth.presentation.ui.createPlan.date

import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.presentation.ui.createPlan.date.DateContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DateViewModel @Inject constructor(
) : BaseViewModel<DateViewState, DateSideEffect, DateEvent>(DateViewState()) {
    override fun handleEvents(event: DateEvent) {
        when (event) {
            is DateEvent.OnClickExitButton -> sendEffect({ DateSideEffect.ExitCreateScreen })
            is DateEvent.OnClickNextButton -> sendEffect({ DateSideEffect.NavigateToNextScreen })
            is DateEvent.OnClickBackButton -> sendEffect({ DateSideEffect.NavigateToPreviousScreen })
        }
    }
}
