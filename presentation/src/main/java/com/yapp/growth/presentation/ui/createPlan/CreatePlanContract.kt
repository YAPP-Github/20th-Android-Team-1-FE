package com.yapp.growth.presentation.ui.createPlan

import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState
import com.yapp.growth.presentation.model.PlanThemeType

class CreatePlanContract {
    data class CreatePlanViewState(
        val theme: PlanThemeType? = null,
        val title: String = "",
        val place: String = "",
        val dates: String = "",
        val startHour: Int = -1,
        val endHour: Int = -1,
    ) : ViewState

    sealed class CreatePlanSideEffect : ViewSideEffect {

    }

    sealed class CreatePlanEvent : ViewEvent {
        data class DecideTheme(val theme: PlanThemeType) : CreatePlanEvent()
        data class DecideTitle(val title: String) : CreatePlanEvent()
        data class DecidePlace(val place: String) : CreatePlanEvent()
        data class DecideDates(val dates: String) : CreatePlanEvent() // TODO: 전달받는 타입에 맞춰 변경
        data class DecideTimeRange(val startHour: Int, val endHour: Int) : CreatePlanEvent()
    }
}
