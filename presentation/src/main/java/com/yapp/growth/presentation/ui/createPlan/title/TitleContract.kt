package com.yapp.growth.presentation.ui.createPlan.title

import com.yapp.growth.base.LoadState
import com.yapp.growth.base.ViewEvent
import com.yapp.growth.base.ViewSideEffect
import com.yapp.growth.base.ViewState

class TitleContract {
    data class TitleViewState(
        val loadState: LoadState = LoadState.SUCCESS,
        val sampleTitle: String = "",
        val title: String = "",
        val place: String = "",
        val isError: Boolean = false,
    ) : ViewState

    sealed class TitleSideEffect : ViewSideEffect {
        object ExitCreateScreen : TitleSideEffect()
        object NavigateToNextScreen : TitleSideEffect()
        object NavigateToPreviousScreen : TitleSideEffect()
    }

    sealed class TitleEvent : ViewEvent {
        data class FillInTitle(val title: String) : TitleEvent()
        data class FillInPlace(val place: String) : TitleEvent()
        data class InitHintText(val categoryId: Int): TitleEvent()
        object ClearTitle : TitleEvent()
        object ClearPlace : TitleEvent()
        object OnClickExitButton : TitleEvent()
        object OnClickNextButton : TitleEvent()
        object OnClickBackButton : TitleEvent()
        data class OnClickErrorRetryButton(val categoryId: Int) : TitleEvent()
    }
}
