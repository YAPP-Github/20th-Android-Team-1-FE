package com.yapp.growth.presentation.ui.main.create.date

import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState
import com.yapp.growth.presentation.model.PlanThemeType
import com.yapp.growth.presentation.ui.main.create.title.TitleContract

class DateContract {
    data class DateViewState(
        val chosenTheme: PlanThemeType? = null,
        val title: String = "",
        val place: String = "",
        val date: String = "",
    ) : ViewState

    sealed class DateSideEffect : ViewSideEffect {
        object ExitCreateScreen : DateSideEffect()
        object NavigateToNextScreen : DateSideEffect()
        object NavigateToPreviousScreen : DateSideEffect()
    }

    sealed class DateEvent : ViewEvent {
        object OnClickExitButton : DateEvent()
        object OnClickNextButton : DateEvent()
        object OnClickBackButton : DateEvent()
    }
}
