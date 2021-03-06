package com.yapp.growth.presentation.ui.createPlan.timetable

import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState
import com.yapp.growth.domain.entity.CreateTimeTable

class CreateTimeTableContract {
    data class CreateTimeTableViewState(
        val createTimeTable: CreateTimeTable = CreateTimeTable(0,"","", emptyList(), emptyList()),
        val clickCount: Int = 0,
    ): ViewState

    sealed class CreateTimeTableSideEffect: ViewSideEffect {
        object ExitCreateScreen : CreateTimeTableSideEffect()
        object NavigateToNextScreen : CreateTimeTableSideEffect()
        object NavigateToPreviousScreen : CreateTimeTableSideEffect()
    }

    sealed class CreateTimeTableEvent: ViewEvent {
        object OnClickExitButton : CreateTimeTableEvent()
        object OnClickSendButton : CreateTimeTableEvent()
        object OnClickBackButton : CreateTimeTableEvent()
        data class OnClickTimeTable(val dateIndex: Int, val minuteIndex: Int) : CreateTimeTableEvent()
        object OnClickNextDayButton : CreateTimeTableEvent()
        object OnClickPreviousDayButton : CreateTimeTableEvent()
    }
}
