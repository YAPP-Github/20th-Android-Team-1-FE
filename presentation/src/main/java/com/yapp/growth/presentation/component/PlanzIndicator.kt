package com.yapp.growth.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.yapp.growth.domain.entity.CreateTimeTable
import com.yapp.growth.domain.entity.TimeTable
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.theme.*
import com.yapp.growth.presentation.util.toDay
import com.yapp.growth.presentation.util.toDayOfWeek

@Composable
fun PlanzPlanDateIndicator(
    modifier: Modifier = Modifier,
    timeTable: TimeTable,
    onClickNextDayButton: () -> Unit,
    onClickPreviousDayButton: () -> Unit
) {

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

                PlanzPlanPreviousDayButton(onClick = {
                    onClickPreviousDayButton()
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

                itemsIndexed(timeTable.availableDates) { _, date ->
                    PlanzPlanDayText(date = date)
                }
            }

            Box(modifier = Modifier
                .padding(start = 22.dp, end = 16.dp)
                .constrainAs(rightArrowBox) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }) {

                PlanzPlanNextDayButton(onClick = {
                    onClickNextDayButton()
                })
            }
        }
    }

}

@Composable
fun CreateTimeTableDateIndicator(
    modifier: Modifier = Modifier,
    createTimeTable: CreateTimeTable,
    onClickNextDayButton: () -> Unit,
    onClickPreviousDayButton: () -> Unit
) {

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

                PlanzPlanPreviousDayButton(onClick = {
                    onClickPreviousDayButton()
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
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {

                itemsIndexed(createTimeTable.availableDates) { _, date ->
                    PlanzPlanDayText(date = date)
                }
            }

            Box(modifier = Modifier
                .padding(start = 22.dp, end = 16.dp)
                .constrainAs(rightArrowBox) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }) {

                PlanzPlanNextDayButton(onClick = {
                    onClickNextDayButton()
                })
            }
        }
    }

}

@Composable
fun PlanzPlanPreviousDayButton(onClick: () -> Unit) {
    Icon(
        painter = painterResource(id = R.drawable.ic_arrow_box_left_24),
        contentDescription = stringResource(id = R.string.icon_arrow_box_left_24_content_description),
        tint = Color.Unspecified,
        modifier = Modifier.clickable {
            onClick()
        }
    )
}

@Composable
fun PlanzPlanNextDayButton(onClick: () -> Unit) {
    Icon(
        painter = painterResource(id = R.drawable.ic_arrow_box_right_24),
        contentDescription = stringResource(id = R.string.icon_arrow_box_right_24_content_description),
        tint = Color.Unspecified,
        modifier = Modifier.clickable {
            onClick()
        }
    )
}

@Composable
fun PlanzPlanDayText(date: String) {
    Column {
        Text(
            text = date.toDayOfWeek(),
            color = Gray700,
            style = PlanzTypography.caption,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = date.toDay(),
            color = Gray800,
            style = PlanzTypography.body2
        )
    }
}
