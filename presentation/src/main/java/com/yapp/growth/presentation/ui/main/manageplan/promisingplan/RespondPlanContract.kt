package com.yapp.growth.presentation.ui.main.manageplan.promisingplan

import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState

class RespondPlanContract {
    data class PromisingViewState(
        val selectTimes: Boolean = false
    ): ViewState

    sealed class PromisingSideEffect: ViewSideEffect {

    }

    sealed class PromisingEvent: ViewEvent {
        data class OnClickTimeTable(val temp: Boolean): PromisingEvent()
        object OnClickNextButton: PromisingEvent()
        object OnClickExitButton: PromisingEvent()
    }
}
