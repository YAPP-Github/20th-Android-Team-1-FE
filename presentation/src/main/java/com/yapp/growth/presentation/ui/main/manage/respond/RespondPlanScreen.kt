package com.yapp.growth.presentation.ui.main.manage.respond

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.component.*
import com.yapp.growth.presentation.theme.Gray100
import com.yapp.growth.presentation.theme.Gray300
import com.yapp.growth.presentation.theme.Gray800
import com.yapp.growth.presentation.theme.PlanzTypography
import com.yapp.growth.presentation.ui.main.manage.respond.RespondPlanContract.RespondPlanEvent
import com.yapp.growth.presentation.ui.main.manage.respond.RespondPlanContract.RespondPlanSideEffect

@Composable
fun RespondPlanScreen(
    viewModel: RespondPlanViewModel = hiltViewModel(),
    navigateToPreviousScreen: () -> Unit,
    navigateToSendCompleteScreen: () -> Unit,
    navigateToSendRejectedScreen: () -> Unit,
) {
    val uiState by viewModel.viewState.collectAsState()
    val timeCheckedOfDays by viewModel.timeCheckedOfDays.collectAsState()

    Scaffold(
        topBar = {
            PlanzBackAndClearAppBar(
                title = stringResource(id = R.string.respond_plan_title_text),
                onClickBackIcon = { viewModel.setEvent(RespondPlanEvent.OnClickBackButton) },
                textIconTitle = stringResource(id = R.string.respond_plan_clear_select_text),
                onClickClearIcon = { viewModel.setEvent(RespondPlanEvent.OnClickClearButton) }
            )
        }
    ) { padding ->

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

                if (uiState.availableResponse) {
                    PlanzPlanDateIndicator(
                        timeTable = uiState.timeTable,
                        onClickPreviousDayButton = { viewModel.setEvent(RespondPlanEvent.OnClickPreviousDayButton) },
                        onClickNextDayButton = { viewModel.setEvent(RespondPlanEvent.OnClickNextDayButton) }
                    )

                    PlanzPlanTimeTable(
                        timeTable = uiState.timeTable,
                        timeCheckedOfDays = timeCheckedOfDays,
                        onClickTimeTable = { dateIndex, minuteIndex ->
                            viewModel.setEvent(RespondPlanEvent.OnClickTimeTable(dateIndex, minuteIndex))
                        }
                    )
                } else {
                    PlanFulled()
                }
            }

            if (uiState.availableResponse) {
                RespondPlanBottomButton(modifier = Modifier.constrainAs(button) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                }, clickCount = uiState.clickCount,
                    onClickRejectPlanButton = { viewModel.setEvent(RespondPlanEvent.OnClickRejectPlanButton) },
                    onClickSendPlanButton = { viewModel.setEvent(RespondPlanEvent.OnClickSendPlanButton) }
                )
            } else {
                Box(
                    modifier = Modifier
                        .constrainAs(button) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                            width = Dimension.fillToConstraints
                        }
                        .background(Gray100)
                        .padding(start = 16.dp, end = 16.dp, bottom = 32.dp),
                ) {
                    PlanzBasicButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.respond_plan_fulled_button_text),
                        onClick = { navigateToPreviousScreen() }
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

@Composable
fun PlanFulled() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray100),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .wrapContentWidth(),
            painter = painterResource(id = R.drawable.ic_failed_character_64),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = stringResource(id = R.string.respond_plan_fulled_title_text),
            style = PlanzTypography.body2,
            color = Gray800,
            textAlign = TextAlign.Center
        )
    }
}
