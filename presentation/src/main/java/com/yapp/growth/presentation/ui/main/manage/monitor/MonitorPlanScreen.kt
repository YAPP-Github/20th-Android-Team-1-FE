@file:OptIn(ExperimentalMaterialApi::class)

package com.yapp.growth.presentation.ui.main.manage.monitor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.component.*
import com.yapp.growth.presentation.ui.main.manage.confirm.FixPlanContract

@Composable
fun MonitorPlanScreen(
    viewModel: MonitorPlanViewModel = hiltViewModel(),
    navigateToPreviousScreen: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val uiState by viewModel.viewState.collectAsState()

    PlanzBottomSheetLayout(
        sheetState = sheetState,
        scrimColor = Color.Transparent,
        sheetContent = {
            FixPlanBottomSheet(
                timeTable = uiState.timeTable,
                currentClickTimeIndex = uiState.currentClickTimeIndex,
                currentClickUserData = uiState.currentClickUserData,
                onClickSelectPlan = { date -> viewModel.setEvent(FixPlanContract.FixPlanEvent.OnClickFixButton(date)) }
            )
        }) {

        Scaffold(
            topBar = {
                PlanzBackAndShareAppBar(
                    title = stringResource(id = R.string.confirm_plan_title_text),
                    onClickBackIcon = navigateToPreviousScreen,
                    onClickShareIcon = { /*TODO*/ }
                )
            }
        ) { padding ->

            ConstraintLayout(modifier = Modifier.fillMaxSize().padding(padding)) {
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
                        onClickPreviousDayButton = { viewModel.setEvent(FixPlanContract.FixPlanEvent.OnClickPreviousDayButton)},
                        onClickNextDayButton = { viewModel.setEvent(FixPlanContract.FixPlanEvent.OnClickNextDayButton) }
                    )

                    FixPlanTimeTable(
                        timeTable = uiState.timeTable,
                        onClickTimeTable = { dateIndex, minuteIndex ->
                            viewModel.setEvent(FixPlanContract.FixPlanEvent.OnClickTimeTable(dateIndex, minuteIndex))
                        },
                        currentClickTimeIndex = uiState.currentClickTimeIndex
                    )
                }

            }

        }

    }

}
