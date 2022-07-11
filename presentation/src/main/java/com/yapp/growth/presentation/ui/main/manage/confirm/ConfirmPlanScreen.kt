@file:OptIn(ExperimentalMaterialApi::class)

package com.yapp.growth.presentation.ui.main.manage.confirm

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.component.*
import com.yapp.growth.presentation.ui.main.manage.confirm.ConfirmPlanContract.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@Composable
fun ConfirmPlanScreen(
    viewModel: ConfirmPlanViewModel = hiltViewModel(),
    exitConfirmPlanScreen: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val uiState by viewModel.viewState.collectAsState()
    val dates by viewModel.dates.collectAsState()
    val respondUsers by viewModel.respondUser.collectAsState()
    val currentClickTimeIndex by viewModel.currentClickTimeIndex.collectAsState()

    PlanzBottomSheetLayout(
        sheetState = sheetState,
        scrimColor = Color.Transparent,
        sheetContent = {
            ConfirmPlanBottomSheet(
                respondUsers = respondUsers,
                currentClickTimeIndex = currentClickTimeIndex,
                onClickSelectPlan = {  }
            )
        }) {

        Scaffold(
            topBar = {
                PlanzBackAndShareAppBar(
                    title = stringResource(id = R.string.navigation_confirm_plan_text),
                    onClickBackIcon = exitConfirmPlanScreen,
                    onClickShareIcon = { /*TODO*/ }
                )
            }
        ) {

            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (column, button) = createRefs()

                Column(modifier = Modifier.constrainAs(column) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(button.top)
                    height = Dimension.fillToConstraints
                }) {

                    LocationAndAvailableColorBox(respondUsers = respondUsers)

                    PlanzPlanDateIndicator(
                        respondUsers = respondUsers,
                        onClickPreviousDayButton = { viewModel.setEvent(ConfirmPlanEvent.OnClickPreviousDayButton)},
                        onClickNextDayButton = { viewModel.setEvent(ConfirmPlanEvent.OnClickNextDayButton) }
                    )

                    ConfirmPlanTimeTable(
                        respondUsers = respondUsers,
                        onClickTimeTable = { dateIndex, minuteIndex ->
                            viewModel.setEvent(ConfirmPlanEvent.OnClickTimeTable(dateIndex, minuteIndex))
                        },
                        currentClickTimeIndex = currentClickTimeIndex
                    )
                }

            }

        }

        LaunchedEffect(key1 = viewModel.effect) {
            viewModel.effect.collect { event ->
                when (event) {
                    is ConfirmPlanSideEffect.ShowBottomSheet -> {
                        coroutineScope.launch { sheetState.show() }
                    }

                    is ConfirmPlanSideEffect.HideBottomSheet -> {
                        coroutineScope.launch { sheetState.hide() }
                    }
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
            }

        }
    }
}
