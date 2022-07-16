package com.yapp.growth.presentation.ui.createPlan.theme

import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState
import com.yapp.growth.presentation.model.PlanThemeType

class ThemeContract {
    data class ThemeViewState(
        val chosenTheme: PlanThemeType? = null,
    ) : ViewState

    sealed class ThemeSideEffect : ViewSideEffect {
        object ExitCreateScreen : ThemeSideEffect()
        object NavigateToNextScreen : ThemeSideEffect()
    }

    sealed class ThemeEvent : ViewEvent {
        data class ChoosePlanTheme(val theme: PlanThemeType) : ThemeEvent()
        object OnClickNextButton : ThemeEvent()
        object OnClickExitButton : ThemeEvent()
    }
}
