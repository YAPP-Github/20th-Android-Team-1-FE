@file:OptIn(ExperimentalMaterialApi::class)

package com.yapp.growth.presentation.ui.main.monitor

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.growth.base.LoadState
import com.yapp.growth.domain.entity.TimeTable
import com.yapp.growth.domain.entity.User
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.component.*
import com.yapp.growth.presentation.firebase.onDynamicLinkClick
import com.yapp.growth.presentation.theme.Gray800
import com.yapp.growth.presentation.theme.Gray900
import com.yapp.growth.presentation.theme.MainPurple900
import com.yapp.growth.presentation.theme.PlanzTypography
import com.yapp.growth.presentation.ui.main.monitor.MonitorPlanContract.MonitorPlanEvent
import com.yapp.growth.presentation.ui.main.monitor.MonitorPlanContract.MonitorPlanSideEffect
import kotlinx.coroutines.launch

@Composable
fun MonitorPlanScreen(
    viewModel: MonitorPlanViewModel = hiltViewModel(),
    navigateToPreviousScreen: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val uiState by viewModel.viewState.collectAsState()
    val context = LocalContext.current
    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )

    PlanzBottomSheetScaffoldLayout(
        scaffoldState = scaffoldState,
        sheetContent = {
            if (uiState.bottomSheet == MonitorPlanContract.MonitorPlanViewState.BottomSheet.RESPONDENT) {
                PlanzRespondentBottomSheetContent(respondents = uiState.respondents)
            } else {
                MonitorPlanBottomSheetContent(
                    timeTable = uiState.timeTable,
                    currentClickUserData = uiState.currentClickUserData,
                    onClickExitIcon = { viewModel.setEvent(MonitorPlanEvent.OnClickExitIcon) }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                PlanzColorTextWithExitAppBar(
                    title = if (uiState.loadState == LoadState.SUCCESS) uiState.timeTable.promisingName else stringResource(
                        R.string.monitor_plan_title
                    ),
                    onClickShareIcon = { onDynamicLinkClick(context = context, id = uiState.planId.toString()) },
                    onClickExitIcon = { viewModel.setEvent(MonitorPlanEvent.OnClickBackButton) },
                    isLoading = uiState.loadState == LoadState.LOADING
                )
            }
        ) { padding ->
            when (uiState.loadState) {
                LoadState.ERROR -> {
                    PlanzError(
                        retryVisible = true,
                        onClickRetry = {
                            viewModel.setEvent(MonitorPlanEvent.OnClickErrorRetryButton)
                        }
                    )
                }
                LoadState.LOADING -> {
                    ShimmerLocationAndAvailableColorBox()
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

                            LocationAndAvailableColorBox(
                                timeTable = uiState.timeTable,
                                onClickAvailableColorBox = { viewModel.setEvent(MonitorPlanEvent.OnClickAvailableColorBox) }
                            )

                            PlanzPlanDateIndicator(
                                timeTable = uiState.timeTable,
                                onClickPreviousDayButton = { viewModel.setEvent(MonitorPlanEvent.OnClickPreviousDayButton) },
                                onClickNextDayButton = { viewModel.setEvent(MonitorPlanEvent.OnClickNextDayButton) }
                            )

                            FixPlanTimeTable(
                                timeTable = uiState.timeTable,
                                onClickTimeTable = { dateIndex, minuteIndex ->
                                    viewModel.setEvent(
                                        MonitorPlanEvent.OnClickTimeTable(
                                            dateIndex,
                                            minuteIndex
                                        )
                                    )
                                },
                                currentClickTimeIndex = uiState.currentClickTimeIndex
                            )
                        }
                    }
                }
            }

            LaunchedEffect(key1 = viewModel.effect) {
                viewModel.effect.collect { effect ->
                    when (effect) {
                        MonitorPlanSideEffect.NavigateToPreviousScreen -> navigateToPreviousScreen()
                        MonitorPlanSideEffect.HideBottomSheet -> {
                            coroutineScope.launch { sheetState.collapse() }
                        }
                        MonitorPlanSideEffect.ShowBottomSheet -> {
                            coroutineScope.launch { sheetState.expand() }
                        }
                    }
                }
            }

            BackHandler(enabled = sheetState.isExpanded) {
                coroutineScope.launch { sheetState.collapse() }
            }

        }
    }
}

@Composable
fun MonitorPlanBottomSheetContent(
    timeTable: TimeTable,
    currentClickUserData: List<User>,
    onClickExitIcon: () -> Unit,
) {
    val respondUserText = StringBuilder()
    currentClickUserData.forEachIndexed { index, user ->
        when (index) {
            0, 3, 7 -> respondUserText.append(user.userName)
            2, 6 -> respondUserText.append(", ${user.userName.plus("\n")}")
            else -> respondUserText.append(", ${user.userName}")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(start = 20.dp, end = 20.dp)) {

        Icon(
            modifier = Modifier
                .align(Alignment.End)
                .clickable {
                    onClickExitIcon()
                },
            painter = painterResource(R.drawable.ic_exit),
            tint = Color.Unspecified,
            contentDescription = stringResource(R.string.icon_exit_content_description),
        )

        Row {
            Text(
                text = timeTable.promisingName,
                style = PlanzTypography.subtitle1,
                color = MainPurple900,
            )

            Text(
                text = stringResource(id = R.string.monitor_plan_join_title_text),
                style = PlanzTypography.subtitle1,
                color = Gray900,
            )
        }

        if (respondUserText.isNotEmpty()) {
            Text(
                modifier = Modifier.padding(top = 12.dp),
                text = respondUserText.toString(),
                style = PlanzTypography.caption,
                color = Gray800,
            )
        }
    }
}
