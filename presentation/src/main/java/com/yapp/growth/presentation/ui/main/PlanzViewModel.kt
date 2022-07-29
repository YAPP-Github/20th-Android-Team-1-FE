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
    PlanzViewState
) {

    override fun handleEvents(event: PlanzEvent) {
        when(event) {
            is PlanzEvent.OnBottomSheetExitClicked -> { sendEffect({ PlanzSideEffect.HideBottomSheet }) }
        }
    }
}
