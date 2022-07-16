package com.yapp.growth.presentation.ui.main.manage.respond

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.component.*
import com.yapp.growth.presentation.ui.main.manage.respond.RespondPlanContract.RespondPlanEvent

@Composable
fun RespondPlanScreen(
    viewModel: RespondPlanViewModel = hiltViewModel(),
    exitRespondPlanScreen: () -> Unit,
//    navigateToNextScreen: () -> Unit,
) {
    val responsePlan by viewModel.responsePlan.collectAsState()
    val clickCount by viewModel.clickCount.collectAsState()

    Scaffold(
        topBar = {
            PlanzBackAndClearAppBar(
                title = stringResource(id = R.string.navigation_respond_plan_text),
                onClickBackIcon = exitRespondPlanScreen,
                textIconTitle = stringResource(id = R.string.respond_plan_clear_select_text),
                onClickClearIcon = { /*TODO */ }
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

                LocationAndAvailableColorBox(responsePlan = responsePlan)

                PlanzPlanDateIndicator(
                    responsePlan = responsePlan,
                    onClickPreviousDayButton = { viewModel.setEvent(RespondPlanEvent.OnClickPreviousDayButton)},
                    onClickNextDayButton = { viewModel.setEvent(RespondPlanEvent.OnClickNextDayButton) }
                    )

                PlanzPlanTimeTable(
                    responsePlan = responsePlan,
                    onClickTimeTable = { dateIndex, minuteIndex ->
                        viewModel.setEvent(RespondPlanEvent.OnClickTimeTable(dateIndex, minuteIndex))
                    }
                )
            }

            RespondPlanLowButton(modifier = Modifier.constrainAs(button) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }, clickCount = clickCount,
               onClickNothingPlanButton = { },
               onClickSendPlanButton = { }
            )
        }

    }
}

@Composable
fun RespondPlanLowButton(
    modifier: Modifier,
    clickCount: Int,
    onClickSendPlanButton: () -> Unit,
    onClickNothingPlanButton: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 16.dp)
            .clickable(false) { },
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        color = Color.White,
    ) {
        if (clickCount > 0) {
            PlanzBasicBottomButton(
                modifier = Modifier.wrapContentHeight(),
                text = stringResource(id = R.string.respond_plan_send_plan_title),
                enabled = true
            ) {
                onClickSendPlanButton()
            }
        } else {
            PlanzBasicBottomButton(
                modifier = Modifier.wrapContentHeight(),
                text = stringResource(id = R.string.respond_plan_send_nothing_title),
                enabled = false
            ) {
                onClickNothingPlanButton()
            }
        }
    }
}
