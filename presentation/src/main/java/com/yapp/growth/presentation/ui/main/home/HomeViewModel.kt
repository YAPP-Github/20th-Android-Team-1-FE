package com.yapp.growth.presentation.ui.main.home

import androidx.lifecycle.viewModelScope
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.yapp.growth.LoginSdk
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.domain.entity.Plan
import com.yapp.growth.domain.onError
import com.yapp.growth.domain.runCatching
import com.yapp.growth.presentation.ui.main.home.HomeContract.HomeEvent
import com.yapp.growth.presentation.ui.main.home.HomeContract.HomeSideEffect
import com.yapp.growth.presentation.ui.main.home.HomeContract.HomeViewState
import com.yapp.growth.presentation.ui.main.home.HomeContract.LoginState
import com.yapp.growth.presentation.ui.main.home.HomeContract.MonthlyPlanModeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
) : BaseViewModel<HomeViewState, HomeSideEffect, HomeEvent>(
    HomeViewState()
) {

    init {
        checkValidLoginToken()
        fetchDayPlanList()
        fetchMonthPlanList()
    }

    @Inject
    lateinit var kakaoLoginSdk: LoginSdk

    override fun handleEvents(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnCalendarDayClicked -> {
                sendEffect({ HomeSideEffect.ShowBottomSheet })
                fetchSelectDayPlanList(event.selectionDay)
            }
            is HomeEvent.OnBottomSheetExitClicked -> { sendEffect({ HomeSideEffect.HideBottomSheet }) }
            is HomeEvent.OnTodayPlanItemClicked -> { sendEffect({ HomeSideEffect.NavigateDetailPlanScreen }) }
            is HomeEvent.OnUserImageButtonClicked -> { sendEffect({ HomeSideEffect.NavigateToInfoScreen }) }
            is HomeEvent.OnTodayPlanExpandedClicked -> { updateState { copy(isTodayPlanExpanded = !isTodayPlanExpanded) } }
            is HomeEvent.OnMonthlyPlanExpandedClicked -> { updateState { copy(isMonthlyPlanExpanded = !isMonthlyPlanExpanded) } }
            is HomeEvent.OnMonthlyPlanModeClicked -> { updateMonthlyPlanModeState(viewState.value.monthlyPlanMode) }
            is HomeEvent.OnMonthlyPreviousClicked -> { updateDateState(HomeEvent.OnMonthlyPreviousClicked) }
            is HomeEvent.OnMonthlyNextClicked -> { updateDateState(HomeEvent.OnMonthlyNextClicked) }
        }
    }

    // TODO : /api/promises/date/<date>
    private fun fetchDayPlanList() {
        updateState {
            copy(
                todayPlans = listOf(
                    Plan.FixedPlan(
                        id = 0,
                        title = "돼지파티돼지파티돼지",
                        isLeader = false,
                        category = "식사",
                        members = listOf("안녕, 안녕, 안녕"),
                        place = "강남",
                        date = "2022-07-10T11:30:00"
                    ),
                    Plan.FixedPlan(
                        id = 1,
                        title = "돼지파티 123",
                        isLeader = false,
                        category = "식사",
                        members = listOf("안녕, 안녕, 안녕"),
                        place = "강남",
                        date = "2022-07-10T11:00:00"
                    ),
                    Plan.FixedPlan(
                        id = 2,
                        title = "돼지파티 1234",
                        isLeader = false,
                        category = "식사",
                        members = listOf("안녕, 안녕, 안녕"),
                        place = "강남",
                        date = "2022-07-10T11:30:00"
                    ),
                )
            )
        }
    }

    // TODO : GET /api/promises/month/<month>
    private fun fetchMonthPlanList() {
        val monthlyPlans = listOf(
            Plan.FixedPlan(
                id = 0,
                title = "돼지파티12312",
                isLeader = false,
                category = "식사",
                members = listOf("안녕, 안녕, 안녕"),
                place = "강남",
                date = "2022-06-30T11:00:00"
            ),
            Plan.FixedPlan(
                id = 0,
                title = "돼지파티12312",
                isLeader = false,
                category = "식사",
                members = listOf("안녕, 안녕, 안녕"),
                place = "강남",
                date = "2022-07-10T11:30:00"
            ),
            Plan.FixedPlan(
                0,
                "돼지파티",
                false,
                "식사",
                members = listOf("안녕, 안녕, 안녕"),
                place = "강남",
                date = "2022-07-10T11:30:00"
            ),
            Plan.FixedPlan(
                0,
                "돼지파티",
                false,
                "식사",
                members = listOf("안녕, 안녕, 안녕"),
                place = "강남",
                date = "2022-07-08T11:30:00"
            ),
            Plan.FixedPlan(
                id = 0,
                title = "돼지파티",
                isLeader = false,
                category = "식사",
                members = listOf("안녕, 안녕, 안녕"),
                place = "강남",
                date = "2022-07-10T11:35:00"
            ),
            Plan.FixedPlan(
                id = 0,
                title = "돼지파티321312",
                isLeader = false,
                category = "식사",
                members = listOf("안녕, 안녕, 안녕"),
                place = "강남",
                date = "2022-07-15T11:30:00"
            ),
            Plan.FixedPlan(
                id = 0,
                title = "돼지파티312312",
                isLeader = false,
                category = "식사",
                members = listOf("안녕, 안녕, 안녕"),
                place = "강남",
                date = "2022-07-15T11:30:00"
            ),
        )

        updateState {
            copy( monthlyPlans = monthlyPlans )
        }
    }

    private fun fetchSelectDayPlanList(
        selectionDay: CalendarDay,
    ) {
        updateState { copy(selectionDay = selectionDay) }
        val selectionDays = viewState.value.monthlyPlans.filter {
            val tmp = SimpleDateFormat("yyyy-MM-dd").parse(it.date) as Date
            selectionDay.date == tmp
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

    private fun updateDateState(event: HomeEvent) {
        var month = viewState.value.currentDate.month + 1
        var year = viewState.value.currentDate.year

        when(event) {
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
        updateState { copy(currentDate = CalendarDay.from(year, month - 1, 1)) }
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
