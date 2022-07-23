package com.yapp.growth.presentation.ui.createPlan

import androidx.lifecycle.viewModelScope
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.TemporaryPlan
import com.yapp.growth.domain.usecase.CreateTemporaryPlanUseCase
import com.yapp.growth.presentation.ui.createPlan.CreatePlanContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CreatePlanViewModel @Inject constructor(
    private val createTemporaryPlanUseCase: CreateTemporaryPlanUseCase,
) : BaseViewModel<CreatePlanViewState, CreatePlanSideEffect, CreatePlanEvent>(CreatePlanViewState()) {
    override fun handleEvents(event: CreatePlanEvent) {
        when (event) {
            is CreatePlanEvent.DecideTheme -> updateState { copy(theme = event.theme) }
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

    private fun createTemporaryPlan() {
        viewModelScope.launch {
            val result = createTemporaryPlanUseCase(
                TemporaryPlan(
                    title = viewState.value.title,
                    startHour = viewState.value.startHour,
                    endHour = viewState.value.endHour,
                    categoryId = viewState.value.theme?.ordinal ?: 0,
                    availableDates = viewState.value.dates,
                    place = viewState.value.place
                )
            )

            Timber.w("titles = ${viewState.value.title}")
            Timber.w("startHour = ${viewState.value.startHour}")
            Timber.w("endHour = ${viewState.value.endHour}")
            Timber.w("categoryId = ${viewState.value.theme?.ordinal ?: 0}")
            Timber.w("availableDates = ${viewState.value.dates}")
            Timber.w("place = ${viewState.value.place}")

            when (result) {
                is NetworkResult.Success -> {
                    updateState {
                        copy(tempPlanUuid = result.data.uuid)
                    }
                    Timber.w("uuid: ${result.data}")
                    sendEffect({ CreatePlanSideEffect.NavigateToNextScreen })
                }
                is NetworkResult.Error -> {

                    Timber.w("exception: ${result.exception}")
                }
                is NetworkResult.Loading -> {

                }
            }
        }
    }
}
