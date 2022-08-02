package com.yapp.growth.presentation.ui.main

import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.presentation.ui.main.MainContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
) : BaseViewModel<MainViewState, MainSideEffect, MainEvent>(MainViewState) {
    override fun handleEvents(event: MainEvent) {
        when (event) {
            MainEvent.FinishedCreateActivity -> sendEffect({ MainSideEffect.RefreshScreen })
        }
    }
}
