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
import androidx.compose.runtime.saveable.rememberSaveable
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
                                .background(if (upperTableClicked) SubCoral else Color(color) ),
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
