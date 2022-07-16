package com.yapp.growth.presentation.ui.createPlan.theme

import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.presentation.ui.createPlan.theme.ThemeContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
) : BaseViewModel<ThemeViewState, ThemeSideEffect, ThemeEvent>(
    ThemeViewState()
) {
    override fun handleEvents(event: ThemeEvent) {
        when (event) {
            is ThemeEvent.ChoosePlanTheme -> updateState { copy(chosenTheme = event.theme) }
            is ThemeEvent.OnClickNextButton -> sendEffect({ ThemeSideEffect.NavigateToNextScreen })
            is ThemeEvent.OnClickExitButton -> sendEffect({ ThemeSideEffect.ExitCreateScreen })
        }
    }
}
