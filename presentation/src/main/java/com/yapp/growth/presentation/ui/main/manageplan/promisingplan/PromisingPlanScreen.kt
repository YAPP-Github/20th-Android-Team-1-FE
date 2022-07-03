package com.yapp.growth.presentation.ui.main.manageplan.promisingplan

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.growth.domain.entity.Promising
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.component.PlanzExitAppBar
import com.yapp.growth.presentation.theme.*
import java.text.DateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun PromisingPlanScreen(
    viewModel: PromisingPlanViewModel = hiltViewModel(),
    exitCreateScreen: () -> Unit,
//    navigateToNextScreen: () -> Unit,
) {
    val uiState by viewModel.viewState.collectAsState()
    val time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("M/d"))

    Scaffold(
        topBar = {
            PlanzExitAppBar(title = "약속 잡기") {

            }
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
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
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Gray200,
                        shape = RectangleShape
                    )
                    .background(Gray100)
                    .padding(top = 6.dp, bottom = 6.dp)
            ) {

                LazyRow(
                    modifier = Modifier.fillMaxWidth().padding(start = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    item {
                        PromisingPlanLeftButton(onClick = {
                            viewModel.setEvent(PromisingContract.PromisingEvent.OnClickNextButton)
                        })
                    }

                    items(viewModel.timeList.size) {
                        PromisingPlanDayText(date = time)
                    }

                    item {
                        PromisingPlanRightButton(onClick = {

                        })
                    }
                }
            }

            PromisingTimeTable(
                list = viewModel.timeList,
                cal = viewModel.cal,
                df = viewModel.df
            )
        }


    }
}

@Composable
fun PromisingTimeTable(list: List<Promising>, cal: Calendar, df: DateFormat) {
    val stateCal by rememberSaveable { mutableStateOf(cal) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp)
    ) {
        items(list.first().timeList.size / 2) { index ->
            LazyRow(modifier = Modifier.fillParentMaxWidth()) {
                item(key = index) {
                    Column(
                        modifier = Modifier
                            .height(26.dp)
                            .width(40.dp),
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = df.format(stateCal.time),
                            color = Gray500,
                            style = PlanzTypography.caption
                        )
                        stateCal.add(Calendar.HOUR, 1)
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
                                ),
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
                                ),
                            contentAlignment = Alignment.Center,

                            ) {


                        }
                    }

                }
            }
        }
    }
}

@Composable
fun PromisingPlanLeftButton(onClick: () -> Unit) {
    Icon(
        painter = painterResource(id = R.drawable.ic_promising_arrow_back),
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
        painter = painterResource(id = R.drawable.ic_promising_arrow_forward),
        contentDescription = "right arrow",
        tint = Color.Unspecified
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
