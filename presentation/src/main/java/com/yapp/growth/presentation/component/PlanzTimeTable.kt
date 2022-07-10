package com.yapp.growth.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.yapp.growth.domain.entity.RespondPlan
import com.yapp.growth.domain.entity.RespondUsers
import com.yapp.growth.presentation.theme.Gray200
import com.yapp.growth.presentation.theme.Gray500
import com.yapp.growth.presentation.theme.PlanzTypography
import com.yapp.growth.presentation.theme.SubCoral

@Composable
fun  ConfirmPlanTimeTable(
    dates: List<RespondPlan>,
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
                    var upperTableClicked by remember { mutableStateOf(dates[dateIndex].timeList[hourIndex]) }
                    var underTableClicked by remember { mutableStateOf(dates[dateIndex].timeList[hourIndex.plus(1)]) }

                    val blockList = respondUsers.timeTable.find { it.date == date }?.blocks
                    val color = blockList?.let { block ->
                        block.find { it.index == hourIndex }?.color ?: 0x00000000
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
                                .background(if (upperTableClicked) SubCoral else Color(color)),
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
                                .background(if (underTableClicked) SubCoral else Color(color)),
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
fun PromisingTimeTable(
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
                    val color = blockList?.let { block ->
                        block.find { it.index == hourIndex }?.color ?: 0x00000000
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
                                .background(if (upperTableClicked) SubCoral else Color(color)),
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
                                .background(if (underTableClicked) SubCoral else Color(color)),
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
fun AvailableColorBox(
    modifier: Modifier = Modifier,
    respondUsers: RespondUsers
) {
    Row(
        modifier = modifier.wrapContentWidth(),
    ) {

        Column {
            Text(
                text = "0/${respondUsers.colors.size}",
                style = PlanzTypography.caption,
                color = Color(0xFF94A3B8)
            )
            Text(
                text = "가능",
                style = PlanzTypography.caption,
                color = Color(0xFF94A3B8)
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
                color = Color(0xFF94A3B8)
            )
            Text(
                text = "가능",
                style = PlanzTypography.caption,
                color = Color(0xFF94A3B8)
            )
        }
    }
}
