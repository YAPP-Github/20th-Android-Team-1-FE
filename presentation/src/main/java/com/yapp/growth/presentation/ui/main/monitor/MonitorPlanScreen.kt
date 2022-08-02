@file:OptIn(ExperimentalMaterialApi::class)

package com.yapp.growth.presentation.ui.main.monitor

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.growth.base.LoadState
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.component.*
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
    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )

    PlanzBottomSheetScaffoldLayout(
        scaffoldState = scaffoldState,
        sheetContent = {
            MonitorPlanBottomSheetContent(
                timeTable = uiState.timeTable,
                currentClickUserData = uiState.currentClickUserData,
                onClickExitIcon = { viewModel.setEvent(MonitorPlanEvent.OnClickExitIcon) }
            )
        }
    ) {
        Scaffold(
            topBar = {
                PlanzColorTextWithExitAppBar(
                    title = if (uiState.loadState == LoadState.SUCCESS) uiState.timeTable.promisingName else stringResource(
                        R.string.monitor_plan_title
                    ),
                    onExitClick = { viewModel.setEvent(MonitorPlanEvent.OnClickBackButton) },
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

                            LocationAndAvailableColorBox(timeTable = uiState.timeTable)

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
                viewModel.effect.collect() { effect ->
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
