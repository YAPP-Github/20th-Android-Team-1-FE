package com.yapp.growth.presentation.ui.main.detailPlan

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.component.PlanzExitAppBar
import com.yapp.growth.presentation.theme.*
import com.yapp.growth.presentation.ui.main.detailPlan.DetailPlanContract.*
import kotlinx.coroutines.flow.collect

@Composable
fun DetailPlanScreen(
    viewModel: DetailPlanViewModel = hiltViewModel(),
    exitDetailPlanScreen: () -> Unit,
) {

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is DetailPlanSideEffect.ExitDetailPlanScreen -> {
                    exitDetailPlanScreen()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            PlanzExitAppBar(
                title = stringResource(id = R.string.detail_plan_app_bar_text),
                onExitClick = { viewModel.setEvent(DetailPlanEvent.OnClickExitButton) }
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
                    DetailItem(
                        info = stringResource(id = R.string.detail_plan_info_when),
                        content = "5월 1일 오후 3시"
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    DetailItem(
                        info = stringResource(id = R.string.detail_plan_info_place),
                        content = "강남"
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    DetailItem(
                        info = stringResource(id = R.string.detail_plan_info_member),
                        content = "이성계, 이방원, 왕건, 을지문덕\n서희, 척준경, 을파소"
                    )
                }
            }
            Spacer(modifier = Modifier.height(72.dp))
        }
    }
}

@Composable
fun DetailItem(
    info: String,
    content: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = info,
            style = PlanzTypography.body2,
            color = Gray700,
        )
        Text(
            text = content,
            style = PlanzTypography.body2,
            color = Gray900,
            textAlign = TextAlign.End,
        )
    }
}

@Preview (showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun PreviewDetailPlanScreen() {
    DetailPlanScreen(exitDetailPlanScreen = { })
}