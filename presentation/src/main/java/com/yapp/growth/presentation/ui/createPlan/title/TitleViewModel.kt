package com.yapp.growth.presentation.ui.createPlan.title

import androidx.lifecycle.viewModelScope
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.base.LoadState
import com.yapp.growth.domain.onError
import com.yapp.growth.domain.onSuccess
import com.yapp.growth.domain.usecase.GetSampleTitleUseCase
import com.yapp.growth.presentation.ui.createPlan.title.TitleContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TitleViewModel @Inject constructor(
    private val getSampleTitleUseCase: GetSampleTitleUseCase,
) : BaseViewModel<TitleViewState, TitleSideEffect, TitleEvent>(
    TitleViewState()
) {
    override fun handleEvents(event: TitleEvent) {
        when (event) {
            is TitleEvent.FillInTitle -> reflectUpdatedState(title = event.title)
            is TitleEvent.FillInPlace -> reflectUpdatedState(place = event.place)
            is TitleEvent.InitHintText -> getSampleTitle(event.categoryId)
            is TitleEvent.ClearTitle -> reflectUpdatedState(title = "")
            is TitleEvent.ClearPlace -> reflectUpdatedState(place = "")
            is TitleEvent.OnClickExitButton -> sendEffect({ TitleSideEffect.ExitCreateScreen })
            is TitleEvent.OnClickNextButton -> sendEffect({ TitleSideEffect.NavigateToNextScreen })
            is TitleEvent.OnClickBackButton -> sendEffect({ TitleSideEffect.NavigateToPreviousScreen })
        }
    }

    private fun getSampleTitle(categoryId: Int) = viewModelScope.launch {
        updateState { copy(loadState = LoadState.LOADING) }
        getSampleTitleUseCase.invoke(categoryId)
            .onSuccess { sampleTitle ->
                updateState { copy(loadState = LoadState.LOADING, sampleTitle = sampleTitle) }
            }
            .onError { updateState { copy(loadState = LoadState.ERROR) } }
    }

    private fun reflectUpdatedState(
        title: String = viewState.value.title,
        place: String = viewState.value.place,
    ) {
        updateState {
            copy(
                title = title,
                place = place,
                isError = isOverflowed(title, place)
            )
        }
    }

    private fun isOverflowed(title: String, place: String): Boolean {
        return (title.length > MAX_LENGTH_TITLE) || (place.length > MAX_LENGTH_PLACE)
    }
}
