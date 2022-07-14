package com.yapp.growth.presentation.ui.createPlan

import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.presentation.ui.createPlan.CreatePlanContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreatePlanViewModel @Inject constructor(
) : BaseViewModel<CreatePlanViewState, CreatePlanSideEffect, CreatePlanEvent>(CreatePlanViewState()) {
    override fun handleEvents(event: CreatePlanEvent) {
        when(event) {
            is CreatePlanEvent.DecideTheme -> updateState { copy(theme = event.theme) }
            is CreatePlanEvent.DecideTitle -> updateState { copy(title = event.title) }
            is CreatePlanEvent.DecidePlace -> updateState { copy(place = event.place) }
            is CreatePlanEvent.DecideDates -> updateState { copy(dates = event.dates) }
            is CreatePlanEvent.DecideTimeRange -> updateState { copy(
                startHour = event.startHour,
                endHour = event.endHour
            ) }
        }
    }
}
