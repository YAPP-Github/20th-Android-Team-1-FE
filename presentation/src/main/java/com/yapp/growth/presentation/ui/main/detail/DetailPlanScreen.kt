package com.yapp.growth.presentation.ui.main.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.growth.base.LoadState
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.component.PlanzError
import com.yapp.growth.presentation.component.PlanzExitAppBar
import com.yapp.growth.presentation.component.PlanzLoading
import com.yapp.growth.presentation.theme.*
import com.yapp.growth.presentation.ui.main.detail.DetailPlanContract.*

@Composable
fun DetailPlanScreen(
    viewModel: DetailPlanViewModel = hiltViewModel(),
    exitDetailPlanScreen: () -> Unit,
) {

    val viewState by viewModel.viewState.collectAsState()

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
        backgroundColor = Color.White,
    ) { padding ->
        when (viewState.loadState) {
            LoadState.LOADING -> {
                Surface(modifier = Modifier.fillMaxSize()) {
                    PlanzLoading()
                }
            }
            LoadState.ERROR -> {
                Surface(modifier = Modifier.fillMaxSize()) {
                    PlanzError()
                }
            }
            LoadState.SUCCESS -> {
                Box(
                    modifier = Modifier
                        .padding(top = 84.dp, start = 20.dp, end = 20.dp)
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFFBFCFF))
                            .border(
                                width = 1.dp,
                                color = Gray200,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(vertical = 30.dp, horizontal = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(7.dp),
                        ) {
                            Text(
                                text = viewState.category,
                                style = PlanzTypography.h1,
                                color = MainPurple900,
                            )
                            Text(
                                text = viewState.title,
                                style = PlanzTypography.body2,
                                color = Gray500,
                            )
                        }

                        Divider(color = Gray200, thickness = 1.dp)

                        Column(
                            modifier = Modifier.padding(horizontal = 6.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            DetailItem(
                                info = stringResource(id = R.string.detail_plan_info_when),
                                content = viewState.date
                            )
                            DetailItem(
                                info = stringResource(id = R.string.detail_plan_info_place),
                                content = viewState.place
                            )
                            DetailItem(
                                info = stringResource(id = R.string.detail_plan_info_member),
                                content = viewState.member
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Icon(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(bottom = 20.dp),
                        tint = Color.Unspecified,
                        imageVector = ImageVector.vectorResource(R.drawable.icon_plan_detail),
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
fun DetailItem(
    info: String,
    content: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
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
        )
    }
}

@Preview
@Composable
fun PreviewDetailPlanScreen() {
    Scaffold(
        topBar = {
            PlanzExitAppBar(
                title = stringResource(id = R.string.detail_plan_app_bar_text),
                onExitClick = { }
            )
        },
        backgroundColor = Color.White,
    ) { padding ->

        Box(
            modifier = Modifier
                .padding(top = 84.dp, start = 20.dp, end = 20.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFFBFCFF))
                    .border(
                        width = 1.dp,
                        color = Gray200,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(vertical = 30.dp, horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(7.dp),
                ) {
                    Text(
                        text = "식사약속",
                        style = PlanzTypography.h1,
                        color = MainPurple900,
                    )
                    Text(
                        text = "이곳은 타이틀입니다.",
                        style = PlanzTypography.body2,
                        color = Gray500,
                    )
                }

                Divider(color = Gray200, thickness = 1.dp)

                Column(
                    modifier = Modifier.padding(horizontal = 6.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    DetailItem(
                        info = stringResource(id = R.string.detail_plan_info_when),
                        content = "2022.02.22"
                    )
                    DetailItem(
                        info = stringResource(id = R.string.detail_plan_info_place),
                        content = "강남역"
                    )
                    DetailItem(
                        info = stringResource(id = R.string.detail_plan_info_member),
                        content = "이곳은 참여자 영역입니다."
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 42.dp),
                tint = Color.Unspecified,
                imageVector = ImageVector.vectorResource(R.drawable.icon_plan_detail),
                contentDescription = null
            )
        }
    }
}
