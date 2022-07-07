package com.yapp.growth.presentation.ui.main.create.timerange

import androidx.lifecycle.SavedStateHandle
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.presentation.model.PlanThemeType
import com.yapp.growth.presentation.ui.main.KEY_PLAN_DATES
import com.yapp.growth.presentation.ui.main.KEY_PLAN_PLACE
import com.yapp.growth.presentation.ui.main.KEY_PLAN_THEME_TYPE
import com.yapp.growth.presentation.ui.main.KEY_PLAN_TITLE
import com.yapp.growth.presentation.ui.main.create.timerange.TimeRangeContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TimeRangeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<TimeRangeViewState, TimeRangeSideEffect, TimeRangeEvent>(TimeRangeViewState()) {
    init {
        val chosenTheme = savedStateHandle.get<String>(KEY_PLAN_THEME_TYPE)
        val title = savedStateHandle.get<String>(KEY_PLAN_TITLE)
        val place = savedStateHandle.get<String>(KEY_PLAN_PLACE)
        val dates = savedStateHandle.get<String>(KEY_PLAN_DATES)
        chosenTheme?.let {
            updateState {
                copy(
                    chosenTheme = PlanThemeType.valueOf(chosenTheme),
                    title = if (title.isNullOrBlank()) "" else title,
                    place = if (place.isNullOrBlank()) "" else place,
                    dates = if (dates.isNullOrBlank()) "" else dates,
                )
            }
        }
    }

    override fun handleEvents(event: TimeRangeEvent) {
        when (event) {
            is TimeRangeEvent.OnClickExitButton -> sendEffect({ TimeRangeSideEffect.ExitCreateScreen })
            is TimeRangeEvent.OnClickNextButton -> sendEffect({ TimeRangeSideEffect.NavigateToNextScreen })
            is TimeRangeEvent.OnClickBackButton -> sendEffect({ TimeRangeSideEffect.NavigateToPreviousScreen })
            is TimeRangeEvent.OnSelectedHourChanged -> {
                updateHourState(viewState.value.dialogState, event.hour)
                sendEffect({ TimeRangeSideEffect.HideBottomSheet })
                checkRangeError(viewState.value.startHour, viewState.value.endHour)
            }
            is TimeRangeEvent.OnStartHourClicked -> {
                updateState { copy(dialogState = TimeRangeViewState.DialogState.START_HOUR) }
                sendEffect({ TimeRangeSideEffect.ShowBottomSheet })
            }
            is TimeRangeEvent.OnEndHourClicked -> {
                updateState { copy(dialogState = TimeRangeViewState.DialogState.END_HOUR) }
                sendEffect({ TimeRangeSideEffect.ShowBottomSheet })
            }
        }
    }

    private fun updateHourState(dialogState: TimeRangeViewState.DialogState, hour: Int) {
        when (dialogState) {
            TimeRangeViewState.DialogState.START_HOUR -> {
                updateState {
                    copy(
                        startHour = hour,
                        dialogState = TimeRangeViewState.DialogState.NONE
                    )
                }
            }
            TimeRangeViewState.DialogState.END_HOUR -> updateState {
                copy(
                    endHour = hour,
                    dialogState = TimeRangeViewState.DialogState.NONE
                )
            }
            TimeRangeViewState.DialogState.NONE -> updateState {
                copy(dialogState = TimeRangeViewState.DialogState.NONE)
            }
        }
    }

    private fun checkRangeError(startHour: Int?, endHour: Int?) {
        if (startHour == null) {
            showErrorState(isError = true)
            return
        }
        val start: Int = startHour

        if (endHour == null) {
            showErrorState(isError = true)
            return
        }
        val end: Int = endHour

        if (start < end) {
            showErrorState(isError = false)
            return
        }

        showErrorState(isError = true, showSnackBar = true)
    }

    private fun showErrorState(isError: Boolean, showSnackBar: Boolean = false) {
        updateState { copy(isError = isError) }
        if (isError && showSnackBar) sendEffect({ TimeRangeSideEffect.ShowSnackBar })
    }
}
