package com.yapp.growth.presentation.ui.main.home

import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState

class HomeContract {
    object HomeViewState : ViewState

    sealed class HomeSideEffect : ViewSideEffect {
    }

    sealed class HomeEvent : ViewEvent {
    }
}