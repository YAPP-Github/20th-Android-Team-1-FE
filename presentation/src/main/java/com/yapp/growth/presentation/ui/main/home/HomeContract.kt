package com.yapp.growth.presentation.ui.main.home

import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState

class HomeContract {
    data class HomeViewState(
        val loginState: LoginState = LoginState.LOGIN
    ) : ViewState

    // TODO : 유저 아이콘 클릭 시 내 정보 창으로 이동 (정호)
    sealed class HomeSideEffect : ViewSideEffect {
        object NavigateToInfoScreen : HomeSideEffect()
        object NavigateDetailPlanScreen : HomeSideEffect()
        object ShowBottomSheet : HomeSideEffect()
        object HideBottomSheet : HomeSideEffect()
    }

    sealed class HomeEvent : ViewEvent {
        object OnUserImageButtonClicked : HomeEvent()
        object OnTodayPlanItemClicked : HomeEvent()
        object OnCalendarDayClicked : HomeEvent()
        object OnBottomSheetExitClicked : HomeEvent()
    }

    enum class LoginState {
        NONE, LOGIN
    }
}
