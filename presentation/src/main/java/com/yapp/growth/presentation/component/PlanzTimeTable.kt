package com.yapp.growth.presentation.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer
import com.yapp.growth.domain.entity.CreateTimeTable
import com.yapp.growth.domain.entity.TimeCheckedOfDay
import com.yapp.growth.domain.entity.TimeTable
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.theme.*

@Composable
fun FixPlanTimeTable(
    timeTable: TimeTable,
    onClickTimeTable: (Int, Int) -> Unit,
    currentClickTimeIndex: Pair<Int, Int>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(BackgroundColor2)
            .padding(start = 16.dp)
    ) {
        itemsIndexed(timeTable.hourList) { hourIndex, hour ->
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

                itemsIndexed(timeTable.availableDates) { dateIndex, date ->
                    val minuteIndex = 2 * hourIndex
                    val upperTableClicked = dateIndex == currentClickTimeIndex.first && minuteIndex == currentClickTimeIndex.second
                    val underTableClicked = dateIndex == currentClickTimeIndex.first && minuteIndex.plus(1) == currentClickTimeIndex.second

                    val blockList = timeTable.timeTableDate.find { it.date == date }?.timeTableUnits

                    val upperTableColor = blockList?.let { block ->
                        block.find { it.index == minuteIndex }?.color ?: 0x00000000
                    } ?:  0x00000000

                    val underTableColor = blockList?.let { block ->
                        block.find { it.index == minuteIndex.plus(1) }?.color ?: 0x00000000
                    } ?:  0x00000000

                    Column(modifier = Modifier
                        .wrapContentSize()
                        .border(
                            width = 0.5.dp,
                            color = Gray200,
                            shape = RectangleShape
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .height(26.dp)
                                .fillParentMaxWidth(1f / (timeTable.availableDates.size + 1))
                                .border(
                                    width = if (upperTableClicked) 3.dp else 0.dp,
                                    color = if (upperTableClicked) SubCoral else Color.Transparent,
                                    shape = RectangleShape
                                )
                                .clickable {
                                    onClickTimeTable(dateIndex, minuteIndex)
                                }
                                .background(if (upperTableClicked) SubCoral.copy(0.5f) else Color.Transparent),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(modifier = Modifier
                                .height(26.dp)
                                .fillParentMaxWidth()
                                .background(Color(upperTableColor))
                            )
                        }

                        val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                        Canvas(
                            Modifier
                                .fillParentMaxWidth(1f / (timeTable.availableDates.size + 1))
                                .height(1.dp)
                        ) {
                            drawLine(
                                color = Gray200,
                                start = Offset(0f, 0f),
                                end = Offset(size.width, 0f),
                                pathEffect = pathEffect
                            )
                        }

                        Box(
                            modifier = Modifier
                                .height(26.dp)
                                .fillParentMaxWidth(1f / (timeTable.availableDates.size + 1))
                                .border(
                                    width = if (underTableClicked) 3.dp else 0.5.dp,
                                    color = if (underTableClicked) SubCoral else Gray200,
                                    shape = RectangleShape
                                )
                                .clickable {
                                    onClickTimeTable(dateIndex, minuteIndex.plus(1))
                                }
                                .background(if (underTableClicked) SubCoral.copy(0.5f) else Color.Transparent),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(modifier = Modifier
                                .height(26.dp)
                                .fillParentMaxWidth()
                                .background(Color(underTableColor))
                            )
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun PlanzPlanTimeTable(
    timeTable: TimeTable,
    timeCheckedOfDays: List<TimeCheckedOfDay>,
    onClickTimeTable: (Int, Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(BackgroundColor2)
            .padding(start = 16.dp)
    ) {
        itemsIndexed(timeTable.hourList) { hourIndex, hour ->
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

                itemsIndexed(timeTable.availableDates) { dateIndex, date ->
                    val minuteIndex = 2 * hourIndex
                    val upperTableClicked = timeCheckedOfDays.find { it.date == date }?.timeList?.get(minuteIndex) ?: false
                    val underTableClicked = timeCheckedOfDays.find { it.date == date }?.timeList?.get(minuteIndex.plus(1)) ?: false

                    val blockList = timeTable.timeTableDate.find { it.date == date }?.timeTableUnits

                    val upperTableColor = blockList?.let { block ->
                        block.find { it.index == minuteIndex }?.color ?: 0x00000000
                    } ?:  0x00000000

                    val underTableColor = blockList?.let { block ->
                        block.find { it.index == minuteIndex.plus(1) }?.color ?: 0x00000000
                    } ?:  0x00000000

                    Column(modifier = Modifier
                        .wrapContentSize()
                        .border(
                            width = 0.5.dp,
                            color = Gray200,
                            shape = RectangleShape
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .height(26.dp)
                                .fillParentMaxWidth(1f / (timeTable.availableDates.size + 1))
                                .border(
                                    width = if (upperTableClicked) 3.dp else 0.dp,
                                    color = if (upperTableClicked) SubCoral else Color.Transparent,
                                    shape = RectangleShape
                                )
                                .clickable {
                                    onClickTimeTable(dateIndex, minuteIndex)
                                }
                                .background(if (upperTableClicked) SubCoral.copy(0.5f) else Color.Transparent),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(modifier = Modifier
                                .height(26.dp)
                                .fillParentMaxWidth()
                                .background(Color(upperTableColor))
                            )
                        }

                        val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                        Canvas(
                            Modifier
                                .fillParentMaxWidth(1f / (timeTable.availableDates.size + 1))
                                .height(1.dp)
                        ) {
                            drawLine(
                                color = Gray200,
                                start = Offset(0f, 0f),
                                end = Offset(size.width, 0f),
                                pathEffect = pathEffect
                            )
                        }

                        Box(
                            modifier = Modifier
                                .height(26.dp)
                                .fillParentMaxWidth(1f / (timeTable.availableDates.size + 1))
                                .border(
                                    width = if (underTableClicked) 3.dp else 0.dp,
                                    color = if (underTableClicked) SubCoral else Color.Transparent,
                                    shape = RectangleShape
                                )
                                .clickable {
                                    onClickTimeTable(dateIndex, minuteIndex.plus(1))
                                }
                                .background(if (underTableClicked) SubCoral.copy(0.5f) else Color.Transparent),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(modifier = Modifier
                                .height(26.dp)
                                .fillParentMaxWidth()
                                .background(Color(underTableColor))
                            )
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun CreateTimeTable(
    createTimeTable: CreateTimeTable,
    timeCheckedOfDays: List<TimeCheckedOfDay>,
    onClickTimeTable: (Int, Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(BackgroundColor2)
            .padding(start = 16.dp)
    ) {
        itemsIndexed(createTimeTable.hourList) { hourIndex, hour ->
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

                itemsIndexed(createTimeTable.availableDates) { dateIndex, date ->
                    val minuteIndex = 2 * hourIndex
                    val upperTableClicked = timeCheckedOfDays.find { it.date == date }?.timeList?.get(minuteIndex) ?: false
                    val underTableClicked = timeCheckedOfDays.find { it.date == date }?.timeList?.get(minuteIndex.plus(1)) ?: false

                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 0.5.dp,
                            color = Gray200,
                            shape = RectangleShape
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .height(26.dp)
                                .fillParentMaxWidth(1f / (createTimeTable.availableDates.size + 1))
                                .clickable {
                                    onClickTimeTable(dateIndex, minuteIndex)
                                }
                                .background(if (upperTableClicked) MainPurple900 else Color.Transparent),
                        )

                        val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                        Canvas(
                            Modifier
                                .fillParentMaxWidth(1f / (createTimeTable.availableDates.size + 1))
                                .height(1.dp)
                        ) {
                            drawLine(
                                color = Gray200,
                                start = Offset(0f, 0f),
                                end = Offset(size.width, 0f),
                                pathEffect = pathEffect
                            )
                        }

                        Box(
                            modifier = Modifier
                                .height(26.dp)
                                .fillParentMaxWidth(1f / (createTimeTable.availableDates.size + 1))
                                .clickable {
                                    onClickTimeTable(dateIndex, minuteIndex.plus(1))
                                }
                                .background(if (underTableClicked) MainPurple900 else Color.Transparent),
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun ShimmerLocationAndAvailableColorBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 20.dp, start = 20.dp, end = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .shimmer(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Box(
                    modifier = Modifier
                        .width(130.dp)
                        .height(16.dp)
                        .background(Gray200)
                )
                Box(
                    modifier = Modifier
                        .width(110.dp)
                        .height(16.dp)
                        .background(Gray200)
                )
            }
            Box(
                modifier = Modifier
                    .width(110.dp)
                    .height(16.dp)
                    .background(Gray200)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun LocationAndAvailableColorBox(
    modifier: Modifier = Modifier,
    timeTable: TimeTable,
    onClickAvailableColorBox: (() -> Unit)? = null,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 20.dp, start = 20.dp, end = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterStart),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Row(
                modifier = Modifier.wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Icon(
                    tint = Color.Unspecified,
                    imageVector = ImageVector.vectorResource(R.drawable.ic_location_18),
                    contentDescription = null
                )

                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = timeTable.placeName.ifBlank { stringResource(R.string.common_plan_location_black_title) },
                    color = CoolGray500,
                    style = PlanzTypography.caption,
                )
            }

            Row(
                modifier = Modifier.wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Icon(
                    tint = Color.Unspecified,
                    imageVector = ImageVector.vectorResource(R.drawable.ic_memer_18),
                    contentDescription = null
                )

                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = timeTable.owner.userName,
                    color = CoolGray500,
                    style = PlanzTypography.caption,
                )

                Divider(
                    modifier = Modifier
                        .width(1.dp)
                        .height(10.dp),
                    color = Gray500,
                )

                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = timeTable.category.type,
                    color = CoolGray500,
                    style = PlanzTypography.caption,
                )
            }
        }

        Row(
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterEnd)
                .clickable(onClickAvailableColorBox != null) { onClickAvailableColorBox?.invoke() },
        ) {

            Column {
                Text(
                    text = "0/${timeTable.users.size}",
                    style = PlanzTypography.caption,
                    color = CoolGray300
                )
                Text(
                    text = stringResource(R.string.common_plan_colorBox_available_text),
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
                itemsIndexed(timeTable.colors) { _, color ->

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
                    text = "${timeTable.users.size}/${timeTable.users.size}",
                    style = PlanzTypography.caption,
                    color = CoolGray300
                )
                Text(
                    text = stringResource(R.string.common_plan_colorBox_available_text),
                    style = PlanzTypography.caption,
                    color = CoolGray300
                )
            }
        }
    }
}
