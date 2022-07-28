package com.yapp.growth.presentation.ui.createPlan.theme

import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.domain.usecase.GetPlanCategoriesUseCase
import com.yapp.growth.presentation.ui.createPlan.theme.ThemeContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val getPlanCategoriesUseCase: GetPlanCategoriesUseCase,
) : BaseViewModel<ThemeViewState, ThemeSideEffect, ThemeEvent>(
    ThemeViewState()
) {
    override fun handleEvents(event: ThemeEvent) {
        when (event) {
            is ThemeEvent.ChoosePlanCategory -> updateState { copy(chosenCategory = event.category) }
            is ThemeEvent.OnClickNextButton -> sendEffect({ ThemeSideEffect.NavigateToNextScreen })
            is ThemeEvent.OnClickExitButton -> sendEffect({ ThemeSideEffect.ExitCreateScreen })
        }
    }
}
