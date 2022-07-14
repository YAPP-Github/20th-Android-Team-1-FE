package com.yapp.growth.presentation.ui.createPlan.date

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.component.PlanzButtonWithBack
import com.yapp.growth.presentation.component.PlanzCreateStepTitleWithDescription
import com.yapp.growth.presentation.ui.createPlan.CreatePlanContract
import com.yapp.growth.presentation.ui.createPlan.CreatePlanContract.CreatePlanEvent.DecideDates
import com.yapp.growth.presentation.ui.createPlan.CreatePlanViewModel
import com.yapp.growth.presentation.ui.createPlan.date.DateContract.DateEvent
import com.yapp.growth.presentation.ui.createPlan.date.DateContract.DateSideEffect
import com.yapp.growth.presentation.util.composableActivityViewModel
import kotlinx.coroutines.flow.collect

@Composable
fun DateScreen(
    sharedViewModel: CreatePlanViewModel = composableActivityViewModel(),
    viewModel: DateViewModel = hiltViewModel(),
    exitCreateScreen: () -> Unit,
    navigateToNextScreen: () -> Unit,
    navigateToPreviousScreen: () -> Unit,
) {
    val viewState by viewModel.viewState.collectAsState()

    Scaffold(
        topBar = {
            PlanzCreateStepTitleWithDescription(
                currentStep = 3,
                title = stringResource(id = R.string.create_plan_date_title_text),
                description = stringResource(id = R.string.create_plan_date_max_day_text),
                onExitClick = { viewModel.setEvent(DateEvent.OnClickExitButton) }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column {
                Spacer(modifier = Modifier.height(36.dp))
                DateCalendar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp)
                )
            }

            PlanzButtonWithBack(
                text = stringResource(id = R.string.create_plan_next_button_text),
                enabled = true,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 32.dp),
                onClick = { viewModel.setEvent(DateEvent.OnClickNextButton) },
                onBackClick = { viewModel.setEvent(DateEvent.OnClickBackButton) }
            )
        }
    }

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is DateSideEffect.ExitCreateScreen -> {
                    exitCreateScreen()
                }
                is DateSideEffect.NavigateToNextScreen -> {
                    sharedViewModel.setEvent(DecideDates(viewState.dates))
                    navigateToNextScreen()
                }
                is DateSideEffect.NavigateToPreviousScreen -> {
                    navigateToPreviousScreen()
                }
            }
        }
    }
}

@Composable
fun DateCalendar(
    modifier: Modifier = Modifier,
) {
    // TODO: 캘린더 추가

    Box(
        modifier = modifier
            .background(Color.Black)
            .height(280.dp)
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun DateScreenPreview() {
    DateScreen(
        exitCreateScreen = {},
        navigateToNextScreen = { },
        navigateToPreviousScreen = {}
    )
}
