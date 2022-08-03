package com.yapp.growth.presentation.ui.main.respond

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.growth.base.LoadState
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.component.*
import com.yapp.growth.presentation.firebase.onDynamicLinkClick
import com.yapp.growth.presentation.theme.Gray300
import com.yapp.growth.presentation.theme.Gray500
import com.yapp.growth.presentation.ui.main.respond.RespondPlanContract.RespondPlanEvent
import com.yapp.growth.presentation.ui.main.respond.RespondPlanContract.RespondPlanSideEffect

@Composable
fun RespondPlanScreen(
    viewModel: RespondPlanViewModel = hiltViewModel(),
    navigateToPreviousScreen: () -> Unit,
    navigateToSendCompleteScreen: () -> Unit,
    navigateToSendRejectedScreen: () -> Unit,
) {
    val uiState by viewModel.viewState.collectAsState()
    val timeCheckedOfDays by viewModel.timeCheckedOfDays.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            PlanzBackAndClearAppBar(
                title = if (uiState.loadState == LoadState.SUCCESS) uiState.timeTable.promisingName else stringResource(R.string.respond_plan_title),
                onClickBackIcon = { viewModel.setEvent(RespondPlanEvent.OnClickBackButton) },
                onClickShareIcon = { onDynamicLinkClick(context = context, id = uiState.planId.toString()) },
                textIconTitle = stringResource(id = R.string.respond_plan_clear_select_text),
                textIconColor = Gray500,
                onClickClearText = { viewModel.setEvent(RespondPlanEvent.OnClickClearButton) },
                clickable = uiState.clickCount > 0,
            )
        }
    ) { padding ->

        when (uiState.loadState) {
            LoadState.LOADING -> PlanzLoading()
            LoadState.ERROR -> {
                PlanzError(
                    retryVisible = true,
                    onClickRetry = { viewModel.setEvent(RespondPlanEvent.OnClickErrorRetryButton) }
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

                        LocationAndAvailableColorBox(timeTable = uiState.timeTable)

                        PlanzPlanDateIndicator(
                            timeTable = uiState.timeTable,
                            onClickPreviousDayButton = { viewModel.setEvent(RespondPlanEvent.OnClickPreviousDayButton) },
                            onClickNextDayButton = { viewModel.setEvent(RespondPlanEvent.OnClickNextDayButton) },
                            enablePrev = uiState.enablePrev,
                            enableNext = uiState.enableNext,
                        )

                        PlanzPlanTimeTable(
                            timeTable = uiState.timeTable,
                            timeCheckedOfDays = timeCheckedOfDays,
                            onClickTimeTable = { dateIndex, minuteIndex ->
                                viewModel.setEvent(RespondPlanEvent.OnClickTimeTable(dateIndex, minuteIndex))
                            }
                        )
                    }

                    RespondPlanBottomButton(modifier = Modifier.constrainAs(button) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                    }, clickCount = uiState.clickCount,
                        onClickRejectPlanButton = { viewModel.setEvent(RespondPlanEvent.OnClickRejectPlanButton) },
                        onClickSendPlanButton = { viewModel.setEvent(RespondPlanEvent.OnClickSendPlanButton) }
                    )
                }
            }
        }
    }

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                RespondPlanSideEffect.NavigateToSendCompleteScreen -> navigateToSendCompleteScreen()
                RespondPlanSideEffect.NavigateToSendRejectScreen -> navigateToSendRejectedScreen()
                RespondPlanSideEffect.NavigateToPreviousScreen -> navigateToPreviousScreen()
            }
        }
    }
}

@Composable
fun RespondPlanBottomButton(
    modifier: Modifier,
    clickCount: Int,
    onClickSendPlanButton: () -> Unit,
    onClickRejectPlanButton: () -> Unit
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
        if (clickCount > 0) {
            PlanzBasicBottomButton(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(horizontal = 16.dp),
                text = stringResource(id = R.string.respond_plan_send_plan_title),
            ) {
                onClickSendPlanButton()
            }
        } else {
            PlanzBasicBottomButton(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(horizontal = 16.dp),
                buttonColor = Gray300,
                text = stringResource(id = R.string.respond_plan_send_nothing_title),
            ) {
                onClickRejectPlanButton()
            }
        }
    }
}
