@file:OptIn(ExperimentalMaterialApi::class)

package com.yapp.growth.presentation.ui.main.manage.confirm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.component.*
import com.yapp.growth.presentation.theme.Gray800
import com.yapp.growth.presentation.theme.PlanzTypography
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

    PlanzBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = { /*TODO*/ }) {

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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(start = 14.dp, end = 20.dp, bottom = 16.dp)
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                        ) {

                            Text(
                                modifier = Modifier.align(Alignment.CenterStart),
                                text = stringResource(id = R.string.respond_plan_time_with_team_text),
                                color = Gray800,
                                style = PlanzTypography.subtitle2,
                            )

                            AvailableColorBox(
                                modifier = Modifier.align(Alignment.CenterEnd),
                                respondUsers = respondUsers
                            )
                        }
                    }

                    PromisingDateIndicator(
                        respondUsers = respondUsers,
                        onClickPreviousDayButton = { viewModel.setEvent(ConfirmPlanEvent.OnClickPreviousDayButton)},
                        onClickNextDayButton = { viewModel.setEvent(ConfirmPlanEvent.OnClickNextDayButton) }
                    )

                    ConfirmPlanTimeTable(
                        dates = dates,
                        respondUsers = respondUsers,
                        onClickTimeTable = { dateIndex, minuteIndex ->
                            viewModel.setEvent(ConfirmPlanEvent.OnClickTimeTable(dateIndex, minuteIndex))
                        }
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
            }

        }
    }
}
