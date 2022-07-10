package com.yapp.growth.presentation.ui.main.detailPlan

import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState

class DetailPlanContract {
    data class DetailPlanViewState(
        val title: String? = null,
        val category: String? = null,
        val date: String? = null,
        val place: String? = null,
        val member: String? = null,
    ) : ViewState

    sealed class DetailPlanSideEffect : ViewSideEffect {
        object ExitDetailPlanScreen : DetailPlanSideEffect()
    }

    sealed class DetailPlanEvent : ViewEvent {
        object OnClickExitButton : DetailPlanEvent()
    }
}