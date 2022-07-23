package com.yapp.growth.presentation.ui.createPlan.timerange

import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.presentation.ui.createPlan.timerange.TimeRangeContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TimeRangeViewModel @Inject constructor(
) : BaseViewModel<TimeRangeViewState, TimeRangeSideEffect, TimeRangeEvent>(TimeRangeViewState()) {
    override fun handleEvents(event: TimeRangeEvent) {
        when (event) {
            is TimeRangeEvent.OnClickExitButton -> sendEffect({ TimeRangeSideEffect.ExitCreateScreen })
            is TimeRangeEvent.OnClickNextButton -> sendEffect({ TimeRangeSideEffect.CreateTemporaryPlan })
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
        updateState { copy(isError = isError) }
        if (isError && showSnackBar) sendEffect({ TimeRangeSideEffect.ShowSnackBar })
    }
}
