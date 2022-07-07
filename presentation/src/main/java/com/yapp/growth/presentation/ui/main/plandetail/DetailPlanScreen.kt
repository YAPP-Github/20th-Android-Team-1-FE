package com.yapp.growth.presentation.ui.main.plandetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.component.PlanzExitAppBar
import com.yapp.growth.presentation.theme.*

@Composable
fun DetailPlanScreen() {
    Scaffold(
        topBar = {
            PlanzExitAppBar(
                title = stringResource(id = R.string.detail_plan_app_bar_text),
                onExitClick = { }
            )
        },
        backgroundColor = BackgroundColor1,
    ) {

        Image(
            modifier = Modifier
                .fillMaxSize(),
            imageVector = ImageVector.vectorResource(R.drawable.ic_detail_plan_character),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier.padding(horizontal = 21.dp)
        ) {
            Text(
                // TODO : 약속 테마
                text = "식사 약속",
                style = PlanzTypography.h1,
                color = MainPurple900,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                // TODO : 약속명
                text = "돼지파티 약속",
                style = PlanzTypography.body2,
                color = Gray500,
            )
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = Color.White)
                    .border(1.dp, Gray200, RoundedCornerShape(8.dp)),
            ) {
                Column(
                    modifier = Modifier
                        .padding(vertical = 24.dp, horizontal = 28.dp)
                ) {
                    // TODO : Row 는 분리하여 재사용성을 높이는게 더 좋을듯
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "날짜/시간", color = Gray700)
                        Text(text = "5월 1일 오후 3시", color = Gray900)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "장소", color = Gray700)
                        Text(text = "강남", color = Gray900)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(text = "참여자", color = Gray700)
                        Spacer(modifier = Modifier.width(32.dp))
                        Column {
                            Text(
                                text = "여윤정, 진희철, 권지명, 윤서연",
                                textAlign = TextAlign.End,
                                modifier = Modifier.fillMaxWidth(),
                                color = Gray900
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "여윤정, 진희철, 권지명, 윤서연",
                                textAlign = TextAlign.End,
                                modifier = Modifier.fillMaxWidth(),
                                color = Gray900
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(72.dp))
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun PreviewDetailPlanScreen() {
    DetailPlanScreen()
}