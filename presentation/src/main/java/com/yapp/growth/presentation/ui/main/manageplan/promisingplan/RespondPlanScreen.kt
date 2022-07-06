package com.yapp.growth.presentation.ui.main.manageplan.promisingplan

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.growth.domain.entity.RespondPlan
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.component.PlanzBackAndClearAppBar
import com.yapp.growth.presentation.component.PlanzBottomBasicButton
import com.yapp.growth.presentation.theme.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PromisingPlanScreen(
    viewModel: RespondPlanViewModel = hiltViewModel(),
    exitResponseScreen: () -> Unit,
//    navigateToNextScreen: () -> Unit,
) {
    val uiState by viewModel.viewState.collectAsState()

    Scaffold(
        topBar = {
            PlanzBackAndClearAppBar(
                title = stringResource(id = R.string.navigation_respond_plan_text),
                onClickBackIcon = exitResponseScreen,
                textIconTitle = stringResource(id = R.string.respond_plan_clear_select_text),
                onClickClearIcon = {}
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
                        .padding(start = 20.dp, end = 20.dp, bottom = 24.dp)
                ) {
                    Text(
                        text = "팀원들과의 시간",
                        color = Gray800,
                        style = PlanzTypography.subtitle2,
                    )

                    /**TODO
                     * 0/5 ~ 5/5 명 가능 UI 구현
                     */
                }

                PromisingDateIndicator(times = viewModel.timeList)
                PromisingTimeTable(list = viewModel.timeList, state = uiState)
            }

            PromisingLowButton(modifier = Modifier.constrainAs(button) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }, state = uiState)
        }

    }
}

@Composable
fun PromisingDateIndicator(modifier: Modifier = Modifier, times: List<RespondPlan>) {
    val df: DateFormat = SimpleDateFormat("M/d", Locale.KOREA)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Gray200,
                shape = RectangleShape
            )
            .background(Gray100)
            .padding(top = 6.dp, bottom = 6.dp)
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (leftArrowBox, rightArrowBox, dateRow) = createRefs()

            Box(modifier = Modifier
                .padding(start = 40.dp, end = 20.dp)
                .constrainAs(leftArrowBox) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                }) {

                PromisingPlanLeftButton(onClick = {
                    println("Left Click")
                })
            }

            LazyRow(
                modifier = Modifier
                    .constrainAs(dateRow) {
                        top.linkTo(parent.top)
                        start.linkTo(leftArrowBox.end)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(rightArrowBox.start)
                        width = Dimension.fillToConstraints
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                items(times.size) {
                    PromisingPlanDayText(date = df.format(times[it].date))
                }
            }

            Box(modifier = Modifier
                .padding(start = 22.dp, end = 16.dp)
                .constrainAs(rightArrowBox) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }) {

                PromisingPlanRightButton(onClick = {
                    println("Right Click")
                })
            }
        }
    }

}

@Composable
fun PromisingTimeTable(
    viewModel: RespondPlanViewModel = hiltViewModel(),
    list: List<RespondPlan>,
    state: RespondPlanContract.PromisingViewState
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 16.dp)
    ) {
        items(list.first().timeList.size / 2) { index ->
            LazyRow(modifier = Modifier.fillParentMaxWidth()) {
                item(key = index) {
                    Column(
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .height(26.dp)
                            .width(40.dp),
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = list.first().hours[index],
                            color = Gray500,
                            style = PlanzTypography.caption
                        )
                    }
                }

                items(list.size) { dateIndex ->
                    val ind = 2 * index
                    Column {
                        Box(
                            modifier = Modifier
                                .height(26.dp)
                                .fillParentMaxWidth(1f / (list.size + 1))
                                .border(
                                    width = 0.5.dp,
                                    color = Gray200,
                                    shape = RectangleShape
                                )
                                .clickable {
                                    viewModel.setEvent(RespondPlanContract.PromisingEvent.OnClickTimeTable(state.selectTimes))
                                },
                            contentAlignment = Alignment.Center
                        ) {

                        }

                        Box(
                            modifier = Modifier
                                .height(26.dp)
                                .fillParentMaxWidth(1f / (list.size + 1))
                                .border(
                                    width = 0.5.dp,
                                    color = Gray200,
                                    shape = RectangleShape
                                )
                                .clickable {
                                    viewModel.setEvent(RespondPlanContract.PromisingEvent.OnClickTimeTable(state.selectTimes))
                                },
                            contentAlignment = Alignment.Center
                        ) {

                        }
                    }

                }
            }
        }
    }
}

@Composable
fun PromisingLowButton(modifier: Modifier, state: RespondPlanContract.PromisingViewState) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 16.dp)
            .clickable(false) { },
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        color = Color.White,
    ) {
        if (state.selectTimes) {
            PlanzBottomBasicButton(modifier = Modifier.wrapContentHeight(), text = "약속시간 보내기", enabled = true) {

            }
        } else {
            PlanzBottomBasicButton(modifier = Modifier.wrapContentHeight(), text = "되는시간 없어요", enabled = false) {

            }
        }
    }
}

@Composable
fun PromisingPlanLeftButton(onClick: () -> Unit) {
    Icon(
        painter = painterResource(id = R.drawable.ic_arrow_box_left_24),
        contentDescription = "left arrow",
        tint = Color.Unspecified,
        modifier = Modifier.clickable {
            onClick()
        }
    )
}

@Composable
fun PromisingPlanRightButton(onClick: () -> Unit) {
    Icon(
        painter = painterResource(id = R.drawable.ic_arrow_box_right_24),
        contentDescription = "right arrow",
        tint = Color.Unspecified,
        modifier = Modifier.clickable {
            onClick()
        }
    )
}

@Composable
fun PromisingPlanDayText(date: String) {
    Column {
        Text(
            text = "월",
            color = Gray700,
            style = PlanzTypography.caption,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = date,
            color = Gray800,
            style = PlanzTypography.body2
        )
    }
}
