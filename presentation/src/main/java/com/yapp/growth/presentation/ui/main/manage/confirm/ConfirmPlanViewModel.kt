package com.yapp.growth.presentation.ui.main.manage.confirm

import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.domain.entity.RespondPlan
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.yapp.growth.presentation.ui.main.manage.confirm.ConfirmPlanContract.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class ConfirmPlanViewModel @Inject constructor(

): BaseViewModel<ConfirmPlanViewState, ConfirmPlanSideEffect, ConfirmPlanEvent>(
    ConfirmPlanViewState()
) {
    private val _dates = MutableStateFlow<List<RespondPlan>>(emptyList())
    val dates: StateFlow<List<RespondPlan>>
        get() = _dates.asStateFlow()

    override fun handleEvents(event: ConfirmPlanEvent) {
        when (event) {
            is ConfirmPlanEvent.OnClickNextDayButton -> TODO()
            is ConfirmPlanEvent.OnClickPreviousDayButton -> TODO()
            is ConfirmPlanEvent.OnClickConfirmButton -> TODO()
            is ConfirmPlanEvent.OnClickTimeTable -> TODO()
        }
    }
}
