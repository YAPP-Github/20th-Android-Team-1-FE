package com.yapp.growth.presentation.ui.main

import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.presentation.ui.main.PlanzContract.PlanzEvent
import com.yapp.growth.presentation.ui.main.PlanzContract.PlanzSideEffect
import com.yapp.growth.presentation.ui.main.PlanzContract.PlanzViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlanzViewModel @Inject constructor(
) : BaseViewModel<PlanzViewState, PlanzSideEffect, PlanzEvent>(
    PlanzViewState()
) {

    override fun handleEvents(event: PlanzEvent) {
        when (event) {
            is PlanzEvent.OnPlanItemClicked -> {
                sendEffect({ PlanzSideEffect.NavigateDetailPlanScreen(event.planId) })
            }
            is PlanzEvent.OnBottomSheetExitClicked -> {
                sendEffect({ PlanzSideEffect.HideBottomSheet })
            }
            is PlanzEvent.ShowBottomSheet -> {
                updateState { copy(
                    selectDayPlans = event.selectionDayPlans,
                    selectionDay = event.selectionDay
                ) }
                sendEffect({ PlanzSideEffect.ShowBottomSheet })
            }
        }
    }

    fun clearSelectionPlans() {
        updateState { copy(selectDayPlans = emptyList()) }
    }
}
