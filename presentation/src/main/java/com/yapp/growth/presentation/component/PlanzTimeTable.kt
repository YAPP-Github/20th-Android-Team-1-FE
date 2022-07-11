package com.yapp.growth.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yapp.growth.domain.entity.RespondUsers
import com.yapp.growth.domain.entity.User
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.theme.*

@Composable
fun ConfirmPlanTimeTable(
    respondUsers: RespondUsers,
    onClickTimeTable: (Int, Int) -> Unit,
    currentClickTimeIndex: Pair<Int, Int>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 16.dp)
    ) {
        itemsIndexed(respondUsers.hourList) { hourIndex, hour ->
            LazyRow(modifier = Modifier.fillParentMaxWidth()) {
                item(key = hourIndex) {
                    Column(
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .height(26.dp)
                            .width(40.dp),
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = hour,
                            color = Gray500,
                            style = PlanzTypography.caption
                        )
                    }
                }

                itemsIndexed(respondUsers.avaliableDate) { dateIndex, date ->
                    val minuteIndex = 2 * hourIndex
                    val upperTableClicked = dateIndex == currentClickTimeIndex.first && minuteIndex == currentClickTimeIndex.second
                    val underTableClicked = dateIndex == currentClickTimeIndex.first && minuteIndex.plus(1) == currentClickTimeIndex.second

                    val blockList = respondUsers.timeTable.find { it.date == date }?.blocks

                    val upperTableColor = blockList?.let { block ->
                        block.find { it.index == minuteIndex }?.color ?: 0x00000000
                    } ?:  0x00000000

                    val underTableColor = blockList?.let { block ->
                        block.find { it.index == minuteIndex.plus(1) }?.color ?: 0x00000000
                    } ?:  0x00000000

                    Column {
                        Box(
                            modifier = Modifier
                                .height(26.dp)
                                .fillParentMaxWidth(1f / (respondUsers.avaliableDate.size + 1))
                                .border(
                                    width = 0.5.dp,
                                    color = Gray200,
                                    shape = RectangleShape
                                )
                                .clickable {
                                    onClickTimeTable(dateIndex, minuteIndex)
                                }
                                .background(if (upperTableClicked) SubCoral else Color(upperTableColor)),
                            contentAlignment = Alignment.Center
                        ) {

                        }

                        Box(
                            modifier = Modifier
                                .height(26.dp)
                                .fillParentMaxWidth(1f / (respondUsers.avaliableDate.size + 1))
                                .border(
                                    width = 0.5.dp,
                                    color = Gray200,
                                    shape = RectangleShape
                                )
                                .clickable {
                                    onClickTimeTable(dateIndex, minuteIndex.plus(1))
                                }
                                .background(if (underTableClicked) SubCoral else Color(underTableColor)),
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
fun PlanzPlanTimeTable(
    respondUsers: RespondUsers,
    onClickTimeTable: (Int, Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 16.dp)
    ) {
        itemsIndexed(respondUsers.hourList) { hourIndex, hour ->
            LazyRow(modifier = Modifier.fillParentMaxWidth()) {
                item(key = hourIndex) {
                    Column(
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .height(26.dp)
                            .width(40.dp),
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = hour,
                            color = Gray500,
                            style = PlanzTypography.caption
                        )
                    }
                }

                itemsIndexed(respondUsers.avaliableDate) { dateIndex, date ->
                    val minuteIndex = 2 * hourIndex
                    var upperTableClicked by remember { mutableStateOf(false) }
                    var underTableClicked by remember { mutableStateOf(false) }

                    val blockList = respondUsers.timeTable.find { it.date == date }?.blocks

                    val upperTableColor = blockList?.let { block ->
                        block.find { it.index == minuteIndex }?.color ?: 0x00000000
                    } ?:  0x00000000

                    val underTableColor = blockList?.let { block ->
                        block.find { it.index == minuteIndex.plus(1) }?.color ?: 0x00000000
                    } ?:  0x00000000

                    Column {
                        Box(
                            modifier = Modifier
                                .height(26.dp)
                                .fillParentMaxWidth(1f / (respondUsers.avaliableDate.size + 1))
                                .border(
                                    width = 0.5.dp,
                                    color = Gray200,
                                    shape = RectangleShape
                                )
                                .clickable {
                                    upperTableClicked = !upperTableClicked
                                    onClickTimeTable(dateIndex, minuteIndex)
                                }
                                .background(if (upperTableClicked) SubCoral else Color(upperTableColor)),
                            contentAlignment = Alignment.Center
                        ) {

                        }

                        Box(
                            modifier = Modifier
                                .height(26.dp)
                                .fillParentMaxWidth(1f / (respondUsers.avaliableDate.size + 1))
                                .border(
                                    width = 0.5.dp,
                                    color = Gray200,
                                    shape = RectangleShape
                                )
                                .clickable {
                                    underTableClicked = !underTableClicked
                                    onClickTimeTable(dateIndex, minuteIndex.plus(1))
                                }
                                .background(if (underTableClicked) SubCoral else Color(underTableColor)),
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
fun LocationAndAvailableColorBox(
    modifier: Modifier = Modifier,
    respondUsers: RespondUsers
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 8.dp, bottom = 16.dp, start = 14.dp, end = 20.dp)
    )
    {
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                tint = Color.Unspecified,
                imageVector = ImageVector.vectorResource(R.drawable.ic_location_icon),
                contentDescription = null
            )

            Text(
                text = stringResource(id = R.string.respond_plan_location_text),
                color = CoolGray500,
                style = PlanzTypography.caption,
            )
        }

        Row(
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterEnd),
        ) {

            Column {
                Text(
                    text = "0/${respondUsers.colors.size}",
                    style = PlanzTypography.caption,
                    color = CoolGray300
                )
                Text(
                    text = "가능",
                    style = PlanzTypography.caption,
                    color = CoolGray300
                )
            }

            LazyRow(
                modifier = Modifier
                    .height(36.dp)
                    .wrapContentWidth()
                    .padding(start = 6.dp, end = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                itemsIndexed(respondUsers.colors) { _, color ->
                    Box(
                        modifier = Modifier
                            .width(24.dp)
                            .height(21.dp)
                            .background(Color(color)),
                    )
                }
            }

            Column {
                Text(
                    text = "${respondUsers.colors.size}/${respondUsers.colors.size}",
                    style = PlanzTypography.caption,
                    color = CoolGray300
                )
                Text(
                    text = "가능",
                    style = PlanzTypography.caption,
                    color = CoolGray300
                )
            }
        }

    }

}

@Composable
fun ConfirmPlanBottomSheet(respondUsers: RespondUsers, currentClickTimeIndex: Pair<Int,Int>, currentClickUserData: List<User>, onClickSelectPlan: () -> Unit) {
    if (currentClickTimeIndex.first < 0 || currentClickTimeIndex.second < 0 ) return

    val day = respondUsers.avaliableDate[currentClickTimeIndex.first]
    var hour = respondUsers.hourList[currentClickTimeIndex.second/2]
    if (currentClickTimeIndex.second % 2 != 0 ) hour += "분"

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
                text = "$day $hour",
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

        PlanzBottomBasicButton(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp), text = "약속시간 선택", onClick = {
            onClickSelectPlan()
        })
    }
}
