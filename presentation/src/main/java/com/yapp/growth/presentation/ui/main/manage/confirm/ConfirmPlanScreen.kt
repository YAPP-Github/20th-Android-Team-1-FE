@file:OptIn(ExperimentalMaterialApi::class)

package com.yapp.growth.presentation.ui.main.manage.confirm

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.component.*
import com.yapp.growth.presentation.ui.main.manage.confirm.ConfirmPlanContract.ConfirmPlanEvent
import com.yapp.growth.presentation.ui.main.manage.confirm.ConfirmPlanContract.ConfirmPlanSideEffect
import kotlinx.coroutines.launch


@Composable
fun ConfirmPlanScreen(
    viewModel: ConfirmPlanViewModel = hiltViewModel(),
    navigateToPreviousScreen: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val uiState by viewModel.viewState.collectAsState()

    PlanzBottomSheetLayout(
        sheetState = sheetState,
        scrimColor = Color.Transparent,
        sheetContent = {
            ConfirmPlanBottomSheet(
                timeTable = uiState.timeTable,
                currentClickTimeIndex = uiState.currentClickTimeIndex,
                currentClickUserData = uiState.currentClickUserData,
                onClickSelectPlan = { date -> viewModel.setEvent(ConfirmPlanEvent.OnClickConfirmButton(date)) }
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
                        onClickPreviousDayButton = { viewModel.setEvent(ConfirmPlanEvent.OnClickPreviousDayButton)},
                        onClickNextDayButton = { viewModel.setEvent(ConfirmPlanEvent.OnClickNextDayButton) }
                    )

                    ConfirmPlanTimeTable(
                        timeTable = uiState.timeTable,
                        onClickTimeTable = { dateIndex, minuteIndex ->
                            viewModel.setEvent(ConfirmPlanEvent.OnClickTimeTable(dateIndex, minuteIndex))
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
                is ConfirmPlanSideEffect.ShowBottomSheet -> {
                    coroutineScope.launch { sheetState.show() }
                }

                is ConfirmPlanSideEffect.HideBottomSheet -> {
                    coroutineScope.launch { sheetState.hide() }
                }
                ConfirmPlanSideEffect.NavigateToNextScreen -> TODO()
                ConfirmPlanSideEffect.NavigateToPreviousScreen -> TODO()
            }

        }
    }
}
