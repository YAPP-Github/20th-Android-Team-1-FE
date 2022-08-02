package com.yapp.growth.presentation.ui.main.home

import androidx.lifecycle.viewModelScope
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.yapp.growth.LoginSdk
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.base.LoadState
import com.yapp.growth.domain.onError
import com.yapp.growth.domain.onSuccess
import com.yapp.growth.domain.runCatching
import com.yapp.growth.domain.usecase.GetDayFixedPlansUseCase
import com.yapp.growth.domain.usecase.GetMonthlyFixedPlansUseCase
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
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDayFixedPlansUseCase: GetDayFixedPlansUseCase,
    private val getMonthlyFixedPlansUseCase: GetMonthlyFixedPlansUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val kakaoLoginSdk: LoginSdk
) : BaseViewModel<HomeViewState, HomeSideEffect, HomeEvent>(
    HomeViewState()
) {
    private var isSubscribed = false

    // 사용자가 여러 번 클릭했을 때 버벅거리는 현상을 없애기 위해 따로 분리
    private val _currentDate = MutableStateFlow(CalendarDay.today())

    @OptIn(FlowPreview::class)
    val currentDate: StateFlow<CalendarDay> = _currentDate.debounce(300).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = _currentDate.value
    )

    override fun handleEvents(event: HomeEvent) {
        when (event) {
            is HomeEvent.InitHomeScreen -> {
                updateState { copy(loadState = LoadState.LOADING) }
                updateInitDateState()
                checkValidLoginToken()
            }
            is HomeEvent.OnInduceLoginClicked -> {
                sendEffect({ HomeSideEffect.MoveToLogin })
            }
            is HomeEvent.OnCalendarDayClicked -> {
                sendEffect({ HomeSideEffect.ShowBottomSheet })
                updateSelectionDaysState(event.selectionDay)
            }
            is HomeEvent.OnPlanItemClicked -> {
                sendEffect({ HomeSideEffect.NavigateDetailPlanScreen(event.planId) })
            }
            is HomeEvent.OnUserImageClicked -> {
                sendEffect({ HomeSideEffect.NavigateToMyPageScreen })
            }
            is HomeEvent.OnTodayPlanExpandedClicked -> {
                updateState { copy(isTodayPlanExpanded = !isTodayPlanExpanded) }
            }
            is HomeEvent.OnMonthlyPlanExpandedClicked -> {
                updateState { copy(isMonthlyPlanExpanded = !isMonthlyPlanExpanded) }
            }
            is HomeEvent.OnMonthlyPlanModeClicked -> {
                updateMonthlyPlanModeState(viewState.value.monthlyPlanMode)
            }
            is HomeEvent.OnMonthlyPreviousClicked -> {
                updateCurrentDateState(HomeEvent.OnMonthlyPreviousClicked)
            }
            is HomeEvent.OnMonthlyNextClicked -> {
                updateCurrentDateState(HomeEvent.OnMonthlyNextClicked)
            }
        }
    }

    private fun checkValidLoginToken() {
        viewModelScope.launch {
            runCatching { kakaoLoginSdk.isValidAccessToken() }
                .onSuccess {
                    if (it) {
                        updateState { copy(loginState = LoginState.LOGIN) }
                        fetchUserInfo()
                    } else {
                        updateState {
                            copy(
                                loginState = LoginState.NONE,
                                loadState = LoadState.SUCCESS,
                                monthlyPlanLoadState = LoadState.SUCCESS
                            )
                        }
                    }
                }
                .onError {
                    updateState {
                        copy(
                            loginState = LoginState.NONE,
                            loadState = LoadState.ERROR
                        )
                    }
                }
        }
    }

    private suspend fun fetchUserInfo() {
        val cacheInfo = getUserInfoUseCase.getCachedUserInfo()

        if (cacheInfo == null) {
            getUserInfoUseCase.invoke()
                .onSuccess {
                    updateState {
                        copy(
                            loadState = LoadState.SUCCESS,
                            userName = it.userName
                        )
                    }
                    fetchDayPlans()
                    fetchMonthlyPlans()
                }
                .onError {
                    updateState { copy(loadState = LoadState.ERROR) }
                }
        } else {
            updateState {
                copy(
                    loadState = LoadState.SUCCESS,
                    userName = cacheInfo.userName
                )
            }
            fetchDayPlans()
            fetchMonthlyPlans()
        }
    }

    private suspend fun fetchDayPlans() {
        updateState { copy(todayPlanLoadState = LoadState.LOADING) }
        getDayFixedPlansUseCase.invoke(CalendarDay.today().toFormatDate())
            .onSuccess { plans ->
                updateState {
                    copy(
                        todayPlanLoadState = LoadState.SUCCESS,
                        todayPlans = plans
                    )
                }
            }
            .onError {
                updateState {
                    copy(
                        todayPlanLoadState = LoadState.ERROR,
                    )
                }
            }
    }

    private suspend fun fetchMonthlyPlans() {
        if(!isSubscribed) {
            isSubscribed = true
            currentDate.collectLatest { currentDate ->
                updateState { copy(monthlyPlanLoadState = LoadState.LOADING) }
                getMonthlyFixedPlansUseCase.invoke(currentDate.toFormatDate())
                    .onSuccess { plans ->
                        updateState {
                            copy(
                                monthlyPlanLoadState = LoadState.SUCCESS,
                                monthlyPlans = plans,
                            )
                        }
                    }
                    .onError {
                        updateState { copy(monthlyPlanLoadState = LoadState.ERROR) }
                    }
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

    // TODO : 임시, 추후 로직 수정 필요!
    private fun updateInitDateState() {
        if(_currentDate.value.day > 10) {
            _currentDate.value = CalendarDay.from(_currentDate.value.year, _currentDate.value.month, 1)
        } else {
            _currentDate.value = CalendarDay.from(_currentDate.value.year, _currentDate.value.month, _currentDate.value.day + 1)
        }
    }
}
