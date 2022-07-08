package com.yapp.growth.presentation.ui.main.plandetail

import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.presentation.ui.main.plandetail.DetailPlanContract.*
import javax.inject.Inject

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