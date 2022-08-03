package com.yapp.growth.presentation.ui.createPlan.date

import com.prolificinteractive.materialcalendarview.CalendarDay
import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState

class DateContract {
    data class DateViewState(
        val dates: List<CalendarDay> = emptyList(),
        val isDatesEmpty: Boolean = false,
    ) : ViewState

    sealed class DateSideEffect : ViewSideEffect {
        object ExitCreateScreen : DateSideEffect()
        object NavigateToNextScreen : DateSideEffect()
        object NavigateToPreviousScreen : DateSideEffect()
        data class ShowSnackBar(val msg: String) : DateSideEffect()
    }

    sealed class DateEvent : ViewEvent {
        object OnClickExitButton : DateEvent()
        object OnClickNextButton : DateEvent()
        object OnClickBackButton : DateEvent()
        data class OnDateSelected(val dates: List<CalendarDay>) : DateEvent()
        object OnPreviousDateClicked : DateEvent()
        object OnDateOverSelected : DateEvent()
        object OnMonthlyPreviousClicked : DateEvent()
        object OnMonthlyNextClicked : DateEvent()
    }
}
