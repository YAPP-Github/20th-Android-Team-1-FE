package com.yapp.growth.presentation.ui.createPlan

import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState
import com.yapp.growth.presentation.model.PlanThemeType
import com.yapp.growth.presentation.ui.createPlan.timerange.TimeRangeContract

class CreatePlanContract {
    data class CreatePlanViewState(
        val theme: PlanThemeType? = null,
        val title: String = "",
        val place: String = "",
        val dates: List<String> = emptyList(),
        val startHour: Int = -1,
        val endHour: Int = -1,
        val tempPlanUuid: String = "",
    ) : ViewState

    sealed class CreatePlanSideEffect : ViewSideEffect {
        object NavigateToNextScreen : CreatePlanSideEffect()
    }

    sealed class CreatePlanEvent : ViewEvent {
        data class DecideTheme(val theme: PlanThemeType) : CreatePlanEvent()
        data class DecideTitle(val title: String) : CreatePlanEvent()
        data class DecidePlace(val place: String) : CreatePlanEvent()
        data class DecideDates(val dates: List<String>) : CreatePlanEvent()
        data class DecideTimeRange(val startHour: Int, val endHour: Int) : CreatePlanEvent()
    }
}
