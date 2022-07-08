package com.yapp.growth.presentation.ui.main.detailPlan

import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.presentation.ui.main.detailPlan.DetailPlanContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailPlanViewModel @Inject constructor(
) : BaseViewModel<DetailPlanViewState, DetailPlanSideEffect, DetailPlanEvent>(
    DetailPlanViewState
) {
    override fun handleEvents(event: DetailPlanEvent) {
        when (event) {
            is DetailPlanEvent.OnClickExitButton -> sendEffect({ DetailPlanSideEffect.ExitDetailPlanScreen })
        }
    }
}