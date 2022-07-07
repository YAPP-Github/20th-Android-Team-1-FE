package com.yapp.growth.presentation.ui.main.create.date

import androidx.lifecycle.SavedStateHandle
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.presentation.model.PlanThemeType
import com.yapp.growth.presentation.ui.main.KEY_PLAN_PLACE
import com.yapp.growth.presentation.ui.main.KEY_PLAN_THEME_TYPE
import com.yapp.growth.presentation.ui.main.KEY_PLAN_TITLE
import com.yapp.growth.presentation.ui.main.create.date.DateContract.*
import com.yapp.growth.presentation.ui.main.create.title.TitleContract
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DateViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<DateViewState, DateSideEffect, DateEvent>(DateViewState()) {

    init {
        val chosenTheme = savedStateHandle.get<String>(KEY_PLAN_THEME_TYPE)
        val title = savedStateHandle.get<String>(KEY_PLAN_TITLE)
        val place = savedStateHandle.get<String>(KEY_PLAN_PLACE)
        chosenTheme?.let {
            updateState {
                copy(
                    chosenTheme = PlanThemeType.valueOf(chosenTheme),
                    title = if (title.isNullOrBlank()) "" else title,
                    place = if (place.isNullOrBlank()) "" else place,
                )
            }
        }
    }

    override fun handleEvents(event: DateEvent) {
        when (event) {
            is DateEvent.OnClickExitButton -> sendEffect({ DateSideEffect.ExitCreateScreen })
            is DateEvent.OnClickNextButton -> sendEffect({ DateSideEffect.NavigateToNextScreen })
            is DateEvent.OnClickBackButton -> sendEffect({ DateSideEffect.NavigateToPreviousScreen })
        }
    }
}
