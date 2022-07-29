package com.yapp.growth.presentation.ui.createPlan

import androidx.lifecycle.viewModelScope
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.domain.entity.TemporaryPlan
import com.yapp.growth.domain.onError
import com.yapp.growth.domain.onSuccess
import com.yapp.growth.domain.usecase.CreateTemporaryPlanUseCase
import com.yapp.growth.presentation.ui.createPlan.CreatePlanContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePlanViewModel @Inject constructor(
    private val createTemporaryPlanUseCase: CreateTemporaryPlanUseCase,
) : BaseViewModel<CreatePlanViewState, CreatePlanSideEffect, CreatePlanEvent>(CreatePlanViewState()) {
    override fun handleEvents(event: CreatePlanEvent) {
        when (event) {
            is CreatePlanEvent.DecideCategory -> updateState { copy(category = event.category) }
            is CreatePlanEvent.DecideTitle -> updateState { copy(title = event.title) }
            is CreatePlanEvent.DecidePlace -> updateState { copy(place = event.place) }
            is CreatePlanEvent.DecideDates -> updateState { copy(dates = event.dates) }
            is CreatePlanEvent.DecideTimeRange -> {
                updateState {
                    copy(
                        startHour = event.startHour,
                        endHour = event.endHour
                    )
                }
                createTemporaryPlan()
            }
        }
    }

    private fun createTemporaryPlan() = viewModelScope.launch {
        createTemporaryPlanUseCase(
            TemporaryPlan(
                title = viewState.value.title,
                startHour = viewState.value.startHour,
                endHour = viewState.value.endHour,
                categoryId = viewState.value.category?.id ?: 0,
                availableDates = viewState.value.dates,
                place = viewState.value.place
            )
        ).onSuccess {
            updateState { copy(tempPlanUuid = it.uuid) }
            sendEffect({ CreatePlanSideEffect.NavigateToNextScreen })
        }.onError {

        }
    }
}
