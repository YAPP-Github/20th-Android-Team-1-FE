package com.yapp.growth.presentation.ui.createPlan.title

import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState
import com.yapp.growth.presentation.model.PlanThemeType

class TitleContract {
    data class TitleViewState(
        val chosenTheme: PlanThemeType? = null,
        val title: String = "",
        val place: String = "",
        val isError: Boolean = false,
    ) : ViewState

    sealed class TitleSideEffect : ViewSideEffect {
        object ExitCreateScreen : TitleSideEffect()
        object NavigateToNextScreen : TitleSideEffect()
        object NavigateToPreviousScreen : TitleSideEffect()
    }

    sealed class TitleEvent : ViewEvent {
        data class FillInTitle(val title: String) : TitleEvent()
        data class FillInPlace(val place: String) : TitleEvent()
        object ClearTitle : TitleEvent()
        object ClearPlace : TitleEvent()
        object OnClickExitButton : TitleEvent()
        object OnClickNextButton : TitleEvent()
        object OnClickBackButton : TitleEvent()
    }
}
