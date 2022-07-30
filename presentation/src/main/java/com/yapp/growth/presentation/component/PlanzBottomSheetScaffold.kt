@file:OptIn(ExperimentalMaterialApi::class)

package com.yapp.growth.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yapp.growth.domain.entity.TimeTable
import com.yapp.growth.domain.entity.User
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.theme.*
import com.yapp.growth.presentation.util.getCurrentBlockDate
import com.yapp.growth.presentation.util.toHour
import com.yapp.growth.presentation.util.toPlanDate

@Composable
fun PlanzBottomSheetScaffoldLayout(
    sheetContent: @Composable () -> Unit,
    scaffoldState: BottomSheetScaffoldState,
    content: @Composable () -> Unit
) {
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetBackgroundColor = Color.Transparent,
        sheetPeekHeight = 0.dp,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                PlanzBottomSheetScaffold {
                    sheetContent()
                }
            }
        }
    ) { padding ->
        content()
    }

}


@Composable
fun PlanzBottomSheetScaffold(
    sheetContent: @Composable () -> Unit
) {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
            .background(color = Color.White)
    )

    sheetContent()

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(28.dp)
            .background(color = Color.White)
    )
}

@Composable
fun FixPlanBottomSheetContent(timeTable: TimeTable, currentClickTimeIndex: Pair<Int,Int>, currentClickUserData: List<User>, onClickSelectPlan: (String) -> Unit) {
    if (currentClickTimeIndex.first < 0 || currentClickTimeIndex.second < 0 ) return

    val day = timeTable.availableDates[currentClickTimeIndex.first].split('T').first()
    val hour = timeTable.minTime.toHour()
    val time = "${day}T${hour}:00"

    val respondUserText = StringBuilder()
    currentClickUserData.forEachIndexed { index, user ->
        if (index == 3) respondUserText.append("\n")
        if (index == 7) respondUserText.append("\n")
        if (index == 0) respondUserText.append(user.userName)
        else respondUserText.append(", ${user.userName}")
    }

    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(start = 20.dp, end = 20.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "약속 날짜",
                style = PlanzTypography.subtitle2,
                color = Gray700
            )

            Text(
                text = time.getCurrentBlockDate(currentClickTimeIndex.second).toPlanDate(),
                style = PlanzTypography.subtitle2,
                color = MainPurple900
            )
        }

        if (respondUserText.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "응답자",
                    style = PlanzTypography.subtitle2,
                    color = Gray700
                )

                Text(
                    text = respondUserText.toString(),
                    style = PlanzTypography.caption,
                    color = Gray800,
                    textAlign = TextAlign.End
                )
            }
        }

        PlanzBasicBottomButton(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp), text = stringResource(id = R.string.fix_plan_fix_button_title), onClick = {
            onClickSelectPlan(time.getCurrentBlockDate(currentClickTimeIndex.second))
        })
    }
}

@Composable
fun MonitorPlanBottomSheetContent(timeTable: TimeTable, currentClickUserData: List<User>, onClickExitIcon: () -> Unit) {

    val respondUserText = StringBuilder()
    currentClickUserData.forEachIndexed { index, user ->
        if (index == 3) respondUserText.append("\n")
        if (index == 7) respondUserText.append("\n")
        if (index == 0) respondUserText.append(user.userName)
        else respondUserText.append(", ${user.userName}")
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
