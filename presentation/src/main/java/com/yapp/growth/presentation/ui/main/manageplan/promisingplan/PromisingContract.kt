package com.yapp.growth.presentation.ui.main.manageplan.promisingplan

import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState

class PromisingContract {
    data class PromisingViewState(
        val empty: String? = null
    ): ViewState

    sealed class PromisingSideEffect: ViewSideEffect {

    }

    sealed class PromisingEvent: ViewEvent {
        object OnClickNextButton: PromisingEvent()
        object OnClickExitButton: PromisingEvent()
    }
}
