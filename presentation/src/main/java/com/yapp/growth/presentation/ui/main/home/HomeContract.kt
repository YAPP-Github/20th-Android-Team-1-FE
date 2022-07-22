package com.yapp.growth.presentation.ui.main.home

import com.prolificinteractive.materialcalendarview.CalendarDay
import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState
import com.yapp.growth.domain.entity.Plan

class HomeContract {
    data class HomeViewState(
        val loadState: LoadState = LoadState.Idle,
        val loginState: LoginState = LoginState.LOGIN,
        val userName: String = "김정호",
        val allPlans: List<Plan.FixedPlan> = emptyList(),
        val todayPlans: List<Plan.FixedPlan> = emptyList(),
        val monthlyPlans: List<Plan.FixedPlan> = emptyList(),
        val selectDayPlans: List<Plan.FixedPlan> = emptyList(),
        val selectionDay: CalendarDay = CalendarDay.today(),
        val isTodayPlanExpanded: Boolean = false,
        val isMonthlyPlanExpanded: Boolean = false,
        val monthlyPlanMode: MonthlyPlanModeState = MonthlyPlanModeState.CALENDAR
    ) : ViewState {
        enum class LoadState {
            Loading, Idle, Error
        }
    }

    // TODO : 유저 아이콘 클릭 시 내 정보 창으로 이동 (정호)
    sealed class HomeSideEffect : ViewSideEffect {
        object MoveToLogin : HomeSideEffect()
        object NavigateToMyPageScreen : HomeSideEffect()
        data class NavigateDetailPlanScreen(val planId: Long) : HomeSideEffect()
        object ShowBottomSheet : HomeSideEffect()
        object HideBottomSheet : HomeSideEffect()
    }

    sealed class HomeEvent : ViewEvent {
        object OnInduceLoginClicked : HomeEvent()
        object OnUserImageClicked : HomeEvent()
        data class OnPlanItemClicked(val planId: Long) : HomeEvent()
        data class OnCalendarDayClicked(val selectionDay: CalendarDay) : HomeEvent()
        object OnBottomSheetExitClicked : HomeEvent()
        object OnTodayPlanExpandedClicked : HomeEvent()
        object OnMonthlyPlanExpandedClicked : HomeEvent()
        object OnMonthlyPlanModeClicked : HomeEvent()
        object OnMonthlyPreviousClicked : HomeEvent()
        object OnMonthlyNextClicked : HomeEvent()
    }

    enum class LoginState {
        NONE, LOGIN
    }

    enum class MonthlyPlanModeState {
        CALENDAR, TEXT
    }
}
