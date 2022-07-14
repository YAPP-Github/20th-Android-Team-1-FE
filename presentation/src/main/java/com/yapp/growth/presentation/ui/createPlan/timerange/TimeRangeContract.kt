package com.yapp.growth.presentation.ui.createPlan.timerange

import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState
import com.yapp.growth.presentation.model.PlanThemeType

class TimeRangeContract {
    data class TimeRangeViewState(
        val chosenTheme: PlanThemeType? = null,
        val title: String = "",
        val place: String = "",
        val dates: String = "",
        val startHour: Int? = null,
        val endHour: Int? = null,
        val dialogState: DialogState = DialogState.NONE,
        val isError: Boolean = true,
    ) : ViewState {
        enum class DialogState {
            START_HOUR,
            END_HOUR,
            NONE,
        }
    }

    sealed class TimeRangeSideEffect : ViewSideEffect {
        object ExitCreateScreen : TimeRangeSideEffect()
        object NavigateToNextScreen : TimeRangeSideEffect()
        object NavigateToPreviousScreen : TimeRangeSideEffect()
        object ShowBottomSheet : TimeRangeSideEffect()
        object HideBottomSheet : TimeRangeSideEffect()
        object ShowSnackBar : TimeRangeSideEffect()
    }

    sealed class TimeRangeEvent : ViewEvent {
        object OnClickExitButton : TimeRangeEvent()
        object OnClickNextButton : TimeRangeEvent()
        object OnClickBackButton : TimeRangeEvent()
        object OnStartHourClicked : TimeRangeEvent()
        object OnEndHourClicked : TimeRangeEvent()
        data class OnSelectedHourChanged(val hour: Int) : TimeRangeEvent()
    }
}
