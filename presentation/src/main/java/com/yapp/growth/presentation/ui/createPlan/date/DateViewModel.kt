package com.yapp.growth.presentation.ui.createPlan.date

import androidx.lifecycle.viewModelScope
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.presentation.ui.createPlan.date.DateContract.DateEvent
import com.yapp.growth.presentation.ui.createPlan.date.DateContract.DateSideEffect
import com.yapp.growth.presentation.ui.createPlan.date.DateContract.DateViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DateViewModel @Inject constructor(
) : BaseViewModel<DateViewState, DateSideEffect, DateEvent>(
    DateViewState()
) {

    // 사용자가 여러 번 클릭했을 때 버벅거리는 현상을 없애기 위해 따로 분리
    private val _currentDate = MutableStateFlow(CalendarDay.today())

    @OptIn(FlowPreview::class)
    val currentDate = _currentDate.debounce(300).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = _currentDate.value
    )

    override fun handleEvents(event: DateEvent) {
        when (event) {
            is DateEvent.OnClickExitButton -> sendEffect({ DateSideEffect.ExitCreateScreen })
            is DateEvent.OnClickNextButton -> sendEffect({ DateSideEffect.NavigateToNextScreen })
            is DateEvent.OnClickBackButton -> sendEffect({ DateSideEffect.NavigateToPreviousScreen })
            is DateEvent.OnDateSelected -> {
                updateState { this.copy(dates = event.dates) }
                // TODO : 리스트의 상태 변화를 감지하지 못하는 이유 찾기, 일단 임시로 Boolean 데이터 선언
                if (event.dates.isNotEmpty()) {
                    updateState { this.copy(isDatesEmpty = false) }
                } else {
                    updateState { this.copy(isDatesEmpty = true) }
                }
            }
            is DateEvent.OnPreviousDateClicked -> sendEffect({ DateSideEffect.ShowSnackBar("이전 날짜는 선택이 불가합니다.") })
            is DateEvent.OnDateOverSelected -> sendEffect({ DateSideEffect.ShowSnackBar("최대 12일까지 선택이 가능합니다.") })
            is DateEvent.OnMonthlyPreviousClicked -> { updateDateState(DateEvent.OnMonthlyPreviousClicked) }
            is DateEvent.OnMonthlyNextClicked -> { updateDateState(DateEvent.OnMonthlyNextClicked) }
        }
    }

    private fun updateDateState(event: DateEvent) {
        var month = _currentDate.value.month + 1
        var year = _currentDate.value.year

        when (event) {
            DateEvent.OnMonthlyPreviousClicked -> {
                month--
                if (month == 0) {
                    year--
                    month = 12
                }
            }
            DateEvent.OnMonthlyNextClicked -> {
                month++
                if (month == 13) {
                    year++
                    month = 1
                }
            }
        }

        _currentDate.value = CalendarDay.from(year, month - 1, 1)
    }
}
