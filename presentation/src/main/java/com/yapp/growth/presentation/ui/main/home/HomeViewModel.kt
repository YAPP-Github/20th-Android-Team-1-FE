package com.yapp.growth.presentation.ui.main.home

import androidx.lifecycle.viewModelScope
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.yapp.growth.LoginSdk
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.domain.onError
import com.yapp.growth.domain.onSuccess
import com.yapp.growth.domain.runCatching
import com.yapp.growth.domain.usecase.GetFixedPlansUseCase
import com.yapp.growth.domain.usecase.GetUserInfoUseCase
import com.yapp.growth.presentation.ui.main.home.HomeContract.HomeEvent
import com.yapp.growth.presentation.ui.main.home.HomeContract.HomeSideEffect
import com.yapp.growth.presentation.ui.main.home.HomeContract.HomeViewState
import com.yapp.growth.presentation.ui.main.home.HomeContract.LoginState
import com.yapp.growth.presentation.ui.main.home.HomeContract.MonthlyPlanModeState
import com.yapp.growth.presentation.util.toDate
import com.yapp.growth.presentation.util.toFormatDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getFixedPlansUseCase: GetFixedPlansUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val kakaoLoginSdk: LoginSdk
) : BaseViewModel<HomeViewState, HomeSideEffect, HomeEvent>(
    HomeViewState()
) {

    init {
        updateState { copy(loadState = HomeContract.LoadState.Loading) }
        checkValidLoginToken()
    }

    // 사용자가 여러 번 클릭했을 때 버벅거리는 현상을 없애기 위해 따로 분리
    private val _currentDate = MutableStateFlow(CalendarDay.today())
    @OptIn(FlowPreview::class)
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
            is HomeEvent.OnPlanItemClicked -> { sendEffect({ HomeSideEffect.NavigateDetailPlanScreen(event.planId) }) }
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

    private fun fetchUserInfo() {
        viewModelScope.launch {
            val cacheInfo = getUserInfoUseCase.getCachedUserInfo()

            if(cacheInfo == null) {
                getUserInfoUseCase.invoke()
                    .onSuccess {
                        updateState {
                            copy(
                                loadState = HomeContract.LoadState.Idle,
                                userName = it.userName
                            )
                        }
                    }
                    .onError {
                        updateState {
                            copy(
                                loadState = HomeContract.LoadState.Error
                            )
                        }
                    }
            } else {
                updateState {
                    copy(
                        loadState = HomeContract.LoadState.Idle,
                        userName = cacheInfo.userName
                    )
                }
            }
        }
    }

    private fun fetchPlans() {
        viewModelScope.launch {

            getFixedPlansUseCase.invoke()
                .onSuccess { plans ->
                    val todayPlans = plans.filter { plan ->
                        CalendarDay.from(plan.date.toDate()) == CalendarDay.today()
                    }
                    val monthlyPlans = plans.filter { plan ->
                        CalendarDay.from(plan.date.toDate()).month == _currentDate.value.month
                    }
                    updateState {
                        copy(
                            loadState = HomeContract.LoadState.Idle,
                            allPlans = plans,
                            monthlyPlans = monthlyPlans,
                            todayPlans = todayPlans
                        )
                    }
                }
                .onError {
                    updateState { copy(loadState = HomeContract.LoadState.Error) }
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
                if (isValidLoginToken) {
                    updateState {
                        copy(
                            loginState = LoginState.LOGIN,
                        )
                    }
                    fetchUserInfo()
                    fetchPlans()
                } else {
                    updateState {
                        copy(
                            loginState = LoginState.NONE,
                            loadState = HomeContract.LoadState.Idle
                        )
                    }
                }
            }.onError {
                updateState {
                    copy(
                        loginState = LoginState.NONE,
                        loadState = HomeContract.LoadState.Error
                    )
                }
            }
        }
    }
}
