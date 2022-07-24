@file:OptIn(ExperimentalMaterialApi::class)

package com.yapp.growth.presentation.ui.main.manage.monitor

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.component.*
import com.yapp.growth.presentation.ui.main.manage.monitor.MonitorPlanContract.*
import kotlinx.coroutines.CoroutineScope

@Composable
fun MonitorPlanScreen(
    viewModel: MonitorPlanViewModel = hiltViewModel(),
    navigateToPreviousScreen: () -> Unit,
) {
//    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    val coroutineScope = rememberCoroutineScope()
    val uiState by viewModel.viewState.collectAsState()
    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )

    val scope = rememberCoroutineScope()
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetBackgroundColor = Color.Transparent,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            FixPlanBottomSheet(
                timeTable = uiState.timeTable,
                currentClickTimeIndex = uiState.currentClickTimeIndex,
                currentClickUserData = uiState.currentClickUserData,
                onClickSelectPlan = { }
            )
        }
    ) {
        Scaffold(
            topBar = {
                PlanzBackAndShareAppBar(
                    title = stringResource(id = R.string.monitor_plan_title_text),
                    onClickBackIcon = navigateToPreviousScreen,
                    onClickShareIcon = { /*TODO*/ }
                )
            }
        ) { padding ->

            ConstraintLayout(modifier = Modifier
                .fillMaxSize()
                .padding(padding)) {
                val (column, button) = createRefs()

                Column(modifier = Modifier.constrainAs(column) {4
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(button.top)
                    height = Dimension.fillToConstraints
                }) {

                    LocationAndAvailableColorBox(timeTable = uiState.timeTable)

                    PlanzPlanDateIndicator(
                        timeTable = uiState.timeTable,
                        onClickPreviousDayButton = { viewModel.setEvent(MonitorPlanEvent.OnClickPreviousDayButton)},
                        onClickNextDayButton = { viewModel.setEvent(MonitorPlanEvent.OnClickNextDayButton) }
                    )

                    FixPlanTimeTable(
                        timeTable = uiState.timeTable,
                        onClickTimeTable = { dateIndex, minuteIndex ->
                            viewModel.setEvent(MonitorPlanEvent.OnClickTimeTable(dateIndex, minuteIndex))
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
                MonitorPlanSideEffect.HideBottomSheet -> { }
                MonitorPlanSideEffect.NavigateToPreviousScreen -> navigateToPreviousScreen()
                MonitorPlanSideEffect.ShowBottomSheet -> sheetState.expand()
            }
        }
    }

//    PlanzBottomSheetLayout(
//        sheetState = sheetState,
//        scrimColor = Color.Transparent,
//        sheetContent = {
//            FixPlanBottomSheet(
//                timeTable = uiState.timeTable,
//                currentClickTimeIndex = uiState.currentClickTimeIndex,
//                currentClickUserData = uiState.currentClickUserData,
//                onClickSelectPlan = { date -> viewModel.setEvent(FixPlanContract.FixPlanEvent.OnClickFixButton(date)) }
//            )
//        }) {
//
//        Scaffold(
//            topBar = {
//                PlanzBackAndShareAppBar(
//                    title = stringResource(id = R.string.confirm_plan_title_text),
//                    onClickBackIcon = navigateToPreviousScreen,
//                    onClickShareIcon = { /*TODO*/ }
//                )
//            }
//        ) { padding ->
//
//            ConstraintLayout(modifier = Modifier.fillMaxSize().padding(padding)) {
//                val (column, button) = createRefs()
//
//                Column(modifier = Modifier.constrainAs(column) {
//                    top.linkTo(parent.top)
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                    bottom.linkTo(button.top)
//                    height = Dimension.fillToConstraints
//                }) {
//
//                    LocationAndAvailableColorBox(timeTable = uiState.timeTable)
//
//                    PlanzPlanDateIndicator(
//                        timeTable = uiState.timeTable,
//                        onClickPreviousDayButton = { viewModel.setEvent(FixPlanContract.FixPlanEvent.OnClickPreviousDayButton)},
//                        onClickNextDayButton = { viewModel.setEvent(FixPlanContract.FixPlanEvent.OnClickNextDayButton) }
//                    )
//
//                    FixPlanTimeTable(
//                        timeTable = uiState.timeTable,
//                        onClickTimeTable = { dateIndex, minuteIndex ->
//                            viewModel.setEvent(FixPlanContract.FixPlanEvent.OnClickTimeTable(dateIndex, minuteIndex))
//                        },
//                        currentClickTimeIndex = uiState.currentClickTimeIndex
//                    )
//                }
//
//            }
//
//        }
//
//    }

}

@Composable
fun BottomSheetDialogScreen(
    content: @Composable () -> Unit
) {

    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )

    val scope = rememberCoroutineScope()
    BottomSheetScaffold(
        sheetBackgroundColor = Color.Transparent,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            BottomSheet(content = content)
        }
    ) {
        /**
         * TODO
         * 바텀시트로 가려질 화면 Composable
         */
    }

}

@Composable
fun BottomSheetDialog(
    sheetState: BottomSheetState,
    scaffoldState: BottomSheetScaffoldState,
    scope: CoroutineScope,
    content: @Composable () -> Unit
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

    }

    BottomSheetScaffold(
        sheetBackgroundColor = Color.Transparent,
        sheetPeekHeight = 0.dp,
        scaffoldState = scaffoldState,
        sheetContent = {
            BottomSheet(content = content)
        }
    ) {
        /**
         * TODO
         * 바텀시트로 가려질 화면 Composable
         */
    }
}

@Composable
fun BottomSheet(
    content: @Composable () -> Unit
) {

    Surface(
        modifier = Modifier
            .height(200.dp)
            .clickable(false) { },
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
        ) {
            content()
        }
    }
}