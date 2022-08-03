package com.yapp.growth.presentation.ui.createPlan.timerange

import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.base.LoadState
import com.yapp.growth.presentation.ui.createPlan.timerange.TimeRangeContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TimeRangeViewModel @Inject constructor(
) : BaseViewModel<TimeRangeViewState, TimeRangeSideEffect, TimeRangeEvent>(TimeRangeViewState()) {
    override fun handleEvents(event: TimeRangeEvent) {
        when (event) {
            is TimeRangeEvent.OnClickExitButton -> sendEffect({ TimeRangeSideEffect.ExitCreateScreen })
            is TimeRangeEvent.OnClickNextButton -> updateState {
                copy(isAlertDialogVisible = true)
            }
            is TimeRangeEvent.OnClickBackButton -> sendEffect({ TimeRangeSideEffect.NavigateToPreviousScreen })
            is TimeRangeEvent.OnSelectedHourChanged -> {
                updateHourState(viewState.value.timeSelectDialog, event.hour)
                sendEffect({ TimeRangeSideEffect.HideBottomSheet })
                checkRangeError(viewState.value.startHour, viewState.value.endHour)
            }
            is TimeRangeEvent.OnStartHourClicked -> {
                updateState { copy(timeSelectDialog = TimeRangeViewState.DialogState.START_HOUR) }
                sendEffect({ TimeRangeSideEffect.ShowBottomSheet })
            }
            is TimeRangeEvent.OnEndHourClicked -> {
                updateState { copy(timeSelectDialog = TimeRangeViewState.DialogState.END_HOUR) }
                sendEffect({ TimeRangeSideEffect.ShowBottomSheet })
            }
            is TimeRangeEvent.OnClickAlertDialogNegativeButton -> updateState {
                copy(isAlertDialogVisible = false)
            }
            is TimeRangeEvent.OnClickAlertDialogPositiveButton -> {
                updateState { copy(isAlertDialogVisible = false) }
                sendEffect({ TimeRangeSideEffect.CreateTemporaryPlan })
            }
        }
    }

    private fun updateHourState(dialogState: TimeRangeViewState.DialogState, hour: Int) {
        when (dialogState) {
            TimeRangeViewState.DialogState.START_HOUR -> {
                updateState {
                    copy(
                        startHour = hour,
                        timeSelectDialog = TimeRangeViewState.DialogState.NONE
                    )
                }
            }
            TimeRangeViewState.DialogState.END_HOUR -> updateState {
                copy(
                    endHour = hour,
                    timeSelectDialog = TimeRangeViewState.DialogState.NONE
                )
            }
            TimeRangeViewState.DialogState.NONE -> updateState {
                copy(timeSelectDialog = TimeRangeViewState.DialogState.NONE)
            }
        }
    }

    private fun checkRangeError(startHour: Int?, endHour: Int?) {
        if (startHour == null || endHour == null) {
            showErrorState(isError = true)
            return
        }

        if (startHour < endHour) {
            showErrorState(isError = false)
            return
        }

        showErrorState(isError = true, showSnackBar = true)
    }

    private fun showErrorState(isError: Boolean, showSnackBar: Boolean = false) {
        updateState { copy(isSelectedErrorRange = isError) }
        if (isError && showSnackBar) sendEffect({ TimeRangeSideEffect.ShowSnackBar })
    }
}
