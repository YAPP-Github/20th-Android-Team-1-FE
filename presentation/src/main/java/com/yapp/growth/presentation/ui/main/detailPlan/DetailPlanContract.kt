package com.yapp.growth.presentation.ui.main.detailPlan

import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState

class DetailPlanContract {
    // TODO : 약속 ID 값을 전달받고, 해당 약속의 정보를 ViewState 로 정의해야 함 (정호)
    object DetailPlanViewState : ViewState

    sealed class DetailPlanSideEffect : ViewSideEffect {
        object ExitDetailPlanScreen : DetailPlanSideEffect()
    }

    sealed class DetailPlanEvent : ViewEvent {
        object OnClickExitButton : DetailPlanEvent()
    }
}