package com.yapp.growth.presentation.ui.main.detail

import com.yapp.growth.base.LoadState
import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState

class DetailPlanContract {
    data class DetailPlanViewState(
        val title: String = "",
        val category: String = "",
        val date: String = "",
        val place: String = "",
        val member: String = "",
    ) : ViewState

    sealed class DetailPlanSideEffect : ViewSideEffect {
        object ExitDetailPlanScreen : DetailPlanSideEffect()
    }

    sealed class DetailPlanEvent : ViewEvent {
        object OnClickExitButton : DetailPlanEvent()
    }
}