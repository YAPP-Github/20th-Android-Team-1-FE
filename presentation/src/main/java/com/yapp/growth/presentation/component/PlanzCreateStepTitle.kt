package com.yapp.growth.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.growth.presentation.theme.Gray300
import com.yapp.growth.presentation.theme.Gray900
import com.yapp.growth.presentation.theme.PlanzTypography

@Composable
fun PlanzCreateStepTitle(
    modifier: Modifier = Modifier,
    currentStep: Int,
    title: String,
    onExitClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 20.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.CenterStart),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Text(
                        text = currentStep.toString(),
                        style = PlanzTypography.subtitle2.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Gray900
                    )
                    Text(
                        text = " / 5",
                        style = PlanzTypography.subtitle2.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = Gray300
                    )
                }

                Icon(
                    imageVector = ImageVector.vectorResource(id = com.yapp.growth.presentation.R.drawable.ic_exit),
                    tint = Color.Unspecified,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .clickable { onExitClick() },
                )
            }

            Text(
                text = title,
                style = PlanzTypography.h2,
                color = Gray900
            )
        }


    }
}

@Preview(showBackground = true)
@Composable
fun PlanzCreateStepTitlePreview() {
    PlanzCreateStepTitle(
        currentStep = 1,
        title = "약속 테마를 골라주세요!",
        onExitClick = { }
    )
}
