package com.yapp.growth.presentation.ui.createPlan.theme

import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState
import com.yapp.growth.domain.entity.Category

class ThemeContract {
    data class ThemeViewState(
        val planCategories: List<Category> = emptyList(),
        val chosenCategory: Category? = null,
    ) : ViewState

    sealed class ThemeSideEffect : ViewSideEffect {
        object ExitCreateScreen : ThemeSideEffect()
        object NavigateToNextScreen : ThemeSideEffect()
    }

    sealed class ThemeEvent : ViewEvent {
        data class ChoosePlanCategory(val category: Category) : ThemeEvent()
        object OnClickNextButton : ThemeEvent()
        object OnClickExitButton : ThemeEvent()
    }
}
