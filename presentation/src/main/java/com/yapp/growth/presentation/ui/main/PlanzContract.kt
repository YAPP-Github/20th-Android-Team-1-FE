package com.yapp.growth.presentation.ui.main

import com.prolificinteractive.materialcalendarview.CalendarDay
import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState
import com.yapp.growth.domain.entity.Plan
import com.yapp.growth.presentation.R

class PlanzContract {

    data class PlanzViewState(
        val selectDayPlans: List<Plan.FixedPlan> = emptyList(),
        val selectionDay: CalendarDay = CalendarDay.today(),
    ) : ViewState

    sealed class PlanzSideEffect : ViewSideEffect {
        object ShowBottomSheet : PlanzSideEffect()
        object HideBottomSheet : PlanzSideEffect()
        data class NavigateDetailPlanScreen(val planId: Int) : PlanzSideEffect()
    }

    sealed class PlanzEvent : ViewEvent {
        data class OnPlanItemClicked(val planId: Int) : PlanzEvent()
        object OnBottomSheetExitClicked : PlanzEvent()
        data class ShowBottomSheet(
            val selectionDay: CalendarDay,
            val selectionDayPlans: List<Plan.FixedPlan>,
        ) : PlanzEvent()
    }
}
