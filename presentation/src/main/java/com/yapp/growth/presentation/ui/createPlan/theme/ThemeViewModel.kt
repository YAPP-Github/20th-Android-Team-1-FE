package com.yapp.growth.presentation.ui.createPlan.theme

import androidx.lifecycle.viewModelScope
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.base.LoadState
import com.yapp.growth.domain.onError
import com.yapp.growth.domain.onSuccess
import com.yapp.growth.domain.usecase.GetPlanCategoriesUseCase
import com.yapp.growth.presentation.ui.createPlan.theme.ThemeContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val getPlanCategoriesUseCase: GetPlanCategoriesUseCase,
) : BaseViewModel<ThemeViewState, ThemeSideEffect, ThemeEvent>(
    ThemeViewState()
) {
    init {
        getPlanCategories()
    }

    override fun handleEvents(event: ThemeEvent) {
        when (event) {
            is ThemeEvent.ChoosePlanCategory -> updateState { copy(chosenCategory = event.category) }
            is ThemeEvent.OnClickNextButton -> sendEffect({ ThemeSideEffect.NavigateToNextScreen })
            is ThemeEvent.OnClickExitButton -> sendEffect({ ThemeSideEffect.ExitCreateScreen })
            is ThemeEvent.OnClickErrorRetryButton -> getPlanCategories()
        }
    }

    private fun getPlanCategories() = viewModelScope.launch {
        updateState { copy(loadState = LoadState.LOADING) }
        getPlanCategoriesUseCase.invoke()
            .onSuccess { categories ->
                updateState { copy(loadState = LoadState.SUCCESS, planCategories = categories) }
            }.onError {
                updateState { copy(loadState = LoadState.ERROR) }
            }
    }
}
