package com.yapp.growth.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.yapp.growth.domain.entity.RespondPlan
import com.yapp.growth.presentation.theme.Gray200
import com.yapp.growth.presentation.theme.Gray500
import com.yapp.growth.presentation.theme.PlanzTypography
import com.yapp.growth.presentation.theme.SubCoral

@Composable
fun PromisingTimeTable(
    list: List<RespondPlan>,
    onClickTimeTable: (Int, Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 16.dp)
    ) {
        items(list.first().timeList.size / 2) { hourIndex ->
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
                            text = list.first().hours[hourIndex],
                            color = Gray500,
                            style = PlanzTypography.caption
                        )
                    }
                }

                items(list.size) { dateIndex ->
                    val minuteIndex = 2 * hourIndex
                    val upperTableClicked by mutableStateOf(list[dateIndex].timeList[minuteIndex])
                    val underTableClicked by mutableStateOf(list[dateIndex].timeList[minuteIndex.plus(1)])

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
                                    onClickTimeTable(dateIndex, minuteIndex)
                                }
                                .background(if (upperTableClicked) SubCoral else Color.Transparent),
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
                                    onClickTimeTable(dateIndex, minuteIndex.plus(1))
                                }
                                .background(if (underTableClicked) SubCoral else Color.Transparent),
                            contentAlignment = Alignment.Center
                        ) {

                        }
                    }

                }
            }
        }
    }
}
