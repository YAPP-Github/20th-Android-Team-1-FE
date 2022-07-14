package com.yapp.growth.presentation.ui.main.home

import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState

class HomeContract {
    data class HomeViewState(
        val loginState: LoginState = LoginState.LOGIN
    ) : ViewState

    sealed class HomeSideEffect : ViewSideEffect {
        object NavigateToMyPageScreen : HomeSideEffect()
        object NavigateDetailPlanScreen : HomeSideEffect()
        object OpenBottomSheet : HomeSideEffect()
    }

    sealed class HomeEvent : ViewEvent {
        object OnUserImageButtonClicked : HomeEvent()
        object OnTodayPlanItemClicked : HomeEvent()
        object OnCalendarDayClicked : HomeEvent()
    }

    enum class LoginState {
        NONE, LOGIN
    }
}
