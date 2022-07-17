package com.yapp.growth.presentation.ui.createPlan.title

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.component.PlanzButtonWithBack
import com.yapp.growth.presentation.component.PlanzCreateStepTitle
import com.yapp.growth.presentation.component.PlanzTextField
import com.yapp.growth.presentation.ui.createPlan.CreatePlanContract.CreatePlanEvent.DecideTitle
import com.yapp.growth.presentation.ui.createPlan.CreatePlanViewModel
import com.yapp.growth.presentation.ui.createPlan.title.TitleContract.TitleEvent
import com.yapp.growth.presentation.ui.createPlan.title.TitleContract.TitleSideEffect
import com.yapp.growth.presentation.util.composableActivityViewModel

@Composable
fun TitleScreen(
    sharedViewModel: CreatePlanViewModel = composableActivityViewModel(),
    viewModel: TitleViewModel = hiltViewModel(),
    exitCreateScreen: () -> Unit,
    navigateToNextScreen: () -> Unit,
    navigateToPreviousScreen: () -> Unit,
) {
    val viewState by viewModel.viewState.collectAsState()

    Scaffold(
        topBar = {
            PlanzCreateStepTitle(
                currentStep = 2,
                title = stringResource(id = R.string.create_plan_theme_title_text),
                onExitClick = { viewModel.setEvent(TitleEvent.OnClickExitButton) }
            )
        }
    ) { padding ->
        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {
            Column(
                modifier = Modifier.padding(top = 44.dp),
                verticalArrangement = Arrangement.spacedBy(26.dp)
            ) {
                PlanzTextField(
                    label = stringResource(id = R.string.create_plan_title_title_label),
                    hint = stringResource(id = R.string.create_plan_title_title_hint),
                    maxLength = MAX_LENGTH_TITLE,
                    text = viewState.title,
                    onInputChanged = { viewModel.setEvent(TitleEvent.FillInTitle(it)) },
                    onDeleteClicked = { viewModel.setEvent(TitleEvent.FillInTitle("")) }
                )

                PlanzTextField(
                    label = stringResource(id = R.string.create_plan_title_place_label),
                    hint = stringResource(id = R.string.create_plan_title_place_hint),
                    maxLength = MAX_LENGTH_PLACE,
                    text = viewState.place,
                    onInputChanged = { viewModel.setEvent(TitleEvent.FillInPlace(it)) },
                    onDeleteClicked = { viewModel.setEvent(TitleEvent.FillInPlace("")) }
                )
            }

            PlanzButtonWithBack(
                text = stringResource(id = R.string.create_plan_next_button_text),
                enabled = !viewState.isError,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 32.dp),
                onClick = { viewModel.setEvent(TitleEvent.OnClickNextButton) },
                onBackClick = { viewModel.setEvent(TitleEvent.OnClickBackButton) }
            )
        }
    }

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is TitleSideEffect.ExitCreateScreen -> {
                    exitCreateScreen()
                }
                is TitleSideEffect.NavigateToNextScreen -> {
                    sharedViewModel.setEvent(DecideTitle(viewState.title))
                    navigateToNextScreen()
                }
                is TitleSideEffect.NavigateToPreviousScreen -> {
                    navigateToPreviousScreen()
                }
            }
        }
    }
}

@Preview
@Composable
fun TitleScreenPreview() {
    TitleScreen(
        exitCreateScreen = {},
        navigateToNextScreen = { },
        navigateToPreviousScreen = {}
    )
}

const val MAX_LENGTH_TITLE = 20
const val MAX_LENGTH_PLACE = 20
