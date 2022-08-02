package com.yapp.growth.presentation.ui.createPlan.timetable

import com.yapp.growth.base.LoadState
import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState
import com.yapp.growth.domain.entity.CreateTimeTable

class CreateTimeTableContract {
    data class CreateTimeTableViewState(
        val loadState: LoadState = LoadState.SUCCESS,
        val createTimeTable: CreateTimeTable = CreateTimeTable(0,"","", emptyList(), emptyList()),
        val clickCount: Int = 0,
        val isDialogVisible: Boolean = false,
    ): ViewState

    sealed class CreateTimeTableSideEffect: ViewSideEffect {
        object ExitCreateScreen : CreateTimeTableSideEffect()
        data class NavigateToNextScreen(val planId: Long) : CreateTimeTableSideEffect()
    }

    sealed class CreateTimeTableEvent: ViewEvent {
        object OnClickExitButton : CreateTimeTableEvent()
        object OnClickSendButton : CreateTimeTableEvent()
        object OnClickBackButton : CreateTimeTableEvent()
        data class OnClickTimeTable(val dateIndex: Int, val minuteIndex: Int) : CreateTimeTableEvent()
        object OnClickNextDayButton : CreateTimeTableEvent()
        object OnClickPreviousDayButton : CreateTimeTableEvent()
        object OnClickDialogPositiveButton : CreateTimeTableEvent()
        object OnClickDialogNegativeButton : CreateTimeTableEvent()
        object OnClickErrorRetryButton : CreateTimeTableEvent()
    }
}
