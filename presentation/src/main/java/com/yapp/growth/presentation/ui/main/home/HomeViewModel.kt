package com.yapp.growth.presentation.ui.main.home

import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.presentation.ui.main.home.HomeContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
) : BaseViewModel<HomeViewState, HomeSideEffect, HomeEvent>(HomeViewState()) {

    // TODO : 로그인 여부 체크(정호)

    override fun handleEvents(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnCalendarDayClicked -> {
                sendEffect({ HomeSideEffect.OpenBottomSheet })
            }
            is HomeEvent.OnTodayPlanItemClicked -> {
                sendEffect({ HomeSideEffect.NavigateDetailPlanScreen })
            }
            is HomeEvent.OnUserImageButtonClicked -> {
                sendEffect({ HomeSideEffect.NavigateToInfoScreen })
            }
        }
    }
}
