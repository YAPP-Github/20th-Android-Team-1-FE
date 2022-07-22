package com.yapp.growth.presentation.ui.main.home

import androidx.lifecycle.viewModelScope
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.yapp.growth.LoginSdk
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.domain.onError
import com.yapp.growth.domain.onSuccess
import com.yapp.growth.domain.runCatching
import com.yapp.growth.domain.usecase.GetAllFixedPlanListUseCase
import com.yapp.growth.presentation.ui.main.home.HomeContract.HomeEvent
import com.yapp.growth.presentation.ui.main.home.HomeContract.HomeSideEffect
import com.yapp.growth.presentation.ui.main.home.HomeContract.HomeViewState
import com.yapp.growth.presentation.ui.main.home.HomeContract.LoginState
import com.yapp.growth.presentation.ui.main.home.HomeContract.MonthlyPlanModeState
import com.yapp.growth.presentation.util.toDate
import com.yapp.growth.presentation.util.toFormatDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllFixedPlanListUseCase: GetAllFixedPlanListUseCase,
    private val kakaoLoginSdk: LoginSdk
) : BaseViewModel<HomeViewState, HomeSideEffect, HomeEvent>(
    HomeViewState()
) {

    init {
        checkValidLoginToken()
        fetchPlans()
    }

    // 사용자가 여러 번 클릭했을 때 버벅거리는 현상을 없애기 위해 따로 분리
    private val _currentDate = MutableStateFlow(CalendarDay.today())
    val currentDate: StateFlow<CalendarDay> = _currentDate.debounce(300).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = _currentDate.value
    )

    override fun handleEvents(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnInduceLoginClicked -> { sendEffect({ HomeSideEffect.MoveToLogin }) }
            is HomeEvent.OnCalendarDayClicked -> {
                sendEffect({ HomeSideEffect.ShowBottomSheet })
                updateSelectionDaysState(event.selectionDay)
            }
            is HomeEvent.OnBottomSheetExitClicked -> { sendEffect({ HomeSideEffect.HideBottomSheet }) }
            is HomeEvent.OnPlanItemClicked -> { sendEffect({ HomeSideEffect.NavigateDetailPlanScreen }) }
            is HomeEvent.OnUserImageClicked -> { sendEffect({ HomeSideEffect.NavigateToMyPageScreen }) }
            is HomeEvent.OnTodayPlanExpandedClicked -> { updateState { copy(isTodayPlanExpanded = !isTodayPlanExpanded) } }
            is HomeEvent.OnMonthlyPlanExpandedClicked -> { updateState { copy(isMonthlyPlanExpanded = !isMonthlyPlanExpanded) } }
            is HomeEvent.OnMonthlyPlanModeClicked -> { updateMonthlyPlanModeState(viewState.value.monthlyPlanMode) }
            is HomeEvent.OnMonthlyPreviousClicked -> {
                updateCurrentDateState(HomeEvent.OnMonthlyPreviousClicked)
                updateMonthlyPlansState()
            }
            is HomeEvent.OnMonthlyNextClicked -> {
                updateCurrentDateState(HomeEvent.OnMonthlyNextClicked)
                updateMonthlyPlansState()
            }
        }
    }

    private fun fetchPlans() {
        viewModelScope.launch {
            val result = (getAllFixedPlanListUseCase.invoke())

            result.onSuccess { plans ->
                val todayPlans = plans.filter { plan ->
                    CalendarDay.from(plan.date.toDate()) == CalendarDay.today()
                }
                val monthlyPlans = plans.filter { plan ->
                    CalendarDay.from(plan.date.toDate()).month == _currentDate.value.month
                }
                updateState {
                    copy(allPlans = plans, monthlyPlans = monthlyPlans, todayPlans = todayPlans)
                }
            }

            result.onError {
                Timber.e(it)
            }
        }
    }

    private fun updateSelectionDaysState(selectionDay: CalendarDay) {
        updateState { copy(selectionDay = selectionDay) }

        val selectionDays = viewState.value.monthlyPlans.filter {
            selectionDay.date.toFormatDate() == it.date.toDate().toFormatDate()
        }

        updateState { copy(selectDayPlans = selectionDays) }
    }

    private fun updateMonthlyPlanModeState(monthlyPlanModeState: MonthlyPlanModeState) {
        when (monthlyPlanModeState) {
            MonthlyPlanModeState.CALENDAR -> {
                updateState { copy(monthlyPlanMode = MonthlyPlanModeState.TEXT) }
            }
            MonthlyPlanModeState.TEXT -> {
                updateState { copy(monthlyPlanMode = MonthlyPlanModeState.CALENDAR) }
            }
        }
    }

    private fun updateCurrentDateState(event: HomeEvent) {
        var month = _currentDate.value.month + 1
        var year = _currentDate.value.year

        when (event) {
            HomeEvent.OnMonthlyPreviousClicked -> {
                month--
                if (month == 0) {
                    year--
                    month = 12
                }
            }
            HomeEvent.OnMonthlyNextClicked -> {
                month++
                if (month == 13) {
                    year++
                    month = 1
                }
            }
        }

        _currentDate.value = CalendarDay.from(year, month - 1, 1)
    }

    private fun updateMonthlyPlansState() {
        viewModelScope.launch {
            currentDate.collectLatest { currentDate ->
                val monthlyPlans = viewState.value.allPlans.filter {
                    CalendarDay.from(it.date.toDate()).month == currentDate.month
                }
                updateState {
                    copy(monthlyPlans = monthlyPlans)
                }
            }
        }
    }

    private fun checkValidLoginToken() {
        viewModelScope.launch {
            runCatching {
                val isValidLoginToken = kakaoLoginSdk.isValidAccessToken()
                if (isValidLoginToken) updateState { copy(loginState = LoginState.LOGIN) }
                else updateState { copy(loginState = LoginState.NONE) }
            }.onError {
                updateState { copy(loginState = LoginState.LOGIN) }
            }
        }
    }
}
