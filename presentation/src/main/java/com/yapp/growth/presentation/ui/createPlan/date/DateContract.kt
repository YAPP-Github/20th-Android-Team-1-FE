package com.yapp.growth.presentation.ui.createPlan.date

import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState

class DateContract {
    data class DateViewState(
        val dates: String = "",
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
