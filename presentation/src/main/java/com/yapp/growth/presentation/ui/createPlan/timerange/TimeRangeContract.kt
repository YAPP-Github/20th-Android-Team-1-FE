package com.yapp.growth.presentation.ui.createPlan.timerange

import com.yapp.growth.base.LoadState
import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState

class TimeRangeContract {
    data class TimeRangeViewState(
        val startHour: Int? = null,
        val endHour: Int? = null,
        val timeSelectDialog: DialogState = DialogState.NONE,
        val isSelectedErrorRange: Boolean = true,
        val isAlertDialogVisible: Boolean = false,
    ) : ViewState {
        enum class DialogState {
            START_HOUR,
            END_HOUR,
            NONE,
        }
    }

    sealed class TimeRangeSideEffect : ViewSideEffect {
        object ExitCreateScreen : TimeRangeSideEffect()
        object NavigateToPreviousScreen : TimeRangeSideEffect()
        object ShowBottomSheet : TimeRangeSideEffect()
        object HideBottomSheet : TimeRangeSideEffect()
        object ShowSnackBar : TimeRangeSideEffect()
        object CreateTemporaryPlan : TimeRangeSideEffect()
    }

    sealed class TimeRangeEvent : ViewEvent {
        object OnClickExitButton : TimeRangeEvent()
        object OnClickNextButton : TimeRangeEvent()
        object OnClickBackButton : TimeRangeEvent()
        object OnStartHourClicked : TimeRangeEvent()
        object OnEndHourClicked : TimeRangeEvent()
        data class OnSelectedHourChanged(val hour: Int) : TimeRangeEvent()
        object OnClickAlertDialogNegativeButton : TimeRangeEvent()
        object OnClickAlertDialogPositiveButton : TimeRangeEvent()
    }

    companion object {
        const val DEFAULT_START_HOUR = 0
        const val DEFAULT_END_HOUR = 0
    }
}
