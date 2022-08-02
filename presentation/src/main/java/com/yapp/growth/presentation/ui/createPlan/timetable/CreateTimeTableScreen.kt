package com.yapp.growth.presentation.ui.createPlan.timetable

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.growth.base.LoadState
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.component.*
import com.yapp.growth.presentation.ui.createPlan.timetable.CreateTimeTableContract.*

@Composable
fun CreateTimeTableScreen(
    viewModel: CreateTimeTableViewModel = hiltViewModel(),
    exitCreateScreen: () -> Unit,
    navigateToNextScreen: (Long) -> Unit,
) {
    val uiState by viewModel.viewState.collectAsState()
    val timeCheckedOfDays by viewModel.timeCheckedOfDays.collectAsState()

    Scaffold(
        topBar = {
            PlanzCreateStepTitle(
                currentStep = 5,
                title = stringResource(id = R.string.create_plan_time_table_title_text),
                onExitClick = { viewModel.setEvent(CreateTimeTableEvent.OnClickExitButton) }
            )
        }
    ) { padding ->

        when (uiState.loadState) {
            LoadState.LOADING -> PlanzLoading()
            LoadState.ERROR -> {
                PlanzError(
                    retryVisible = true,
                    onClickRetry = { viewModel.setEvent(CreateTimeTableEvent.OnClickErrorRetryButton) }
                )
            }
            LoadState.SUCCESS -> {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    val (column, button) = createRefs()

                    Column(modifier = Modifier.constrainAs(column) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(button.top)
                        height = Dimension.fillToConstraints
                    }) {


                        CreateTimeTableDateIndicator(
                            createTimeTable = uiState.createTimeTable,
                            onClickPreviousDayButton = { viewModel.setEvent(CreateTimeTableEvent.OnClickPreviousDayButton) },
                            onClickNextDayButton = { viewModel.setEvent(CreateTimeTableEvent.OnClickNextDayButton) }
                        )

                        CreateTimeTable(
                            createTimeTable = uiState.createTimeTable,
                            timeCheckedOfDays = timeCheckedOfDays,
                            onClickTimeTable = { dateIndex, minuteIndex ->
                                viewModel.setEvent(CreateTimeTableEvent.OnClickTimeTable(dateIndex, minuteIndex))
                            }
                        )
                    }

                    CreateTimeTableBottomButton(modifier = Modifier.constrainAs(button) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                    }, clickCount = uiState.clickCount,
                        onClickSendPlanButton = { viewModel.setEvent(CreateTimeTableEvent.OnClickSendButton) }
                    )
                }
            }
        }

        PlanzAlertDialog(
            visible = uiState.isDialogVisible,
            title = stringResource(R.string.planz_alert_dialog_title),
            content = stringResource(R.string.planz_alert_dialog_content),
            positiveButtonText = stringResource(R.string.planz_alert_dialog_positive_button_text),
            negativeButtonText = stringResource(R.string.planz_alert_dialog_negative_button_text),
            onClickNegativeButton = {
                viewModel.setEvent(CreateTimeTableEvent.OnClickDialogNegativeButton)
            },
            onClickPositiveButton = {
                viewModel.setEvent(CreateTimeTableEvent.OnClickDialogPositiveButton)
            },
        )

    }

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                CreateTimeTableSideEffect.ExitCreateScreen -> exitCreateScreen()
                is CreateTimeTableSideEffect.NavigateToNextScreen -> navigateToNextScreen(effect.planId)
            }
        }
    }

    BackHandler {
        viewModel.setEvent(CreateTimeTableEvent.OnClickBackButton)
    }
}

@Composable
fun CreateTimeTableBottomButton(
    modifier: Modifier,
    clickCount: Int,
    onClickSendPlanButton: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable(false) { },
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        elevation = 6.dp,
        color = Color.White,
    ) {

        PlanzBasicBottomButton(
            modifier = Modifier
                .wrapContentHeight()
                .padding(horizontal = 16.dp),
            text = stringResource(id = R.string.create_plan_time_table_button_text),
            enabled = clickCount > 0
        ) {
            onClickSendPlanButton()
        }

    }
}
