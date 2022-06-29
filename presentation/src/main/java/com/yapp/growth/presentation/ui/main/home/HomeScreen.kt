package com.yapp.growth.presentation.ui.main.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.theme.*
import com.yapp.growth.presentation.ui.main.home.HomeContract.HomeSideEffect
import kotlinx.coroutines.flow.collect


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {

    val viewState by viewModel.viewState.collectAsState()

    // TODO : 이벤트에 따라 사이드 이펙트 적용되게 설정 (정호)
    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeSideEffect.NavigateToInfoScreen -> {
                    // onUserIconClick()
                }
                is HomeSideEffect.NavigateDetailPlanScreen -> {
                    // onDetailPlanClick()
                }
                is HomeSideEffect.OpenBottomSheet -> {
                    // sheet.show()
                }
            }
        }
    }

    Scaffold(
        backgroundColor = BackgroundColor1,
        topBar = {
            HomeAppBar(
                userName = "김정호",
                onUserIconClick = { /* TODO */ }
            )
        },
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            HomeTopBox(loginState = viewState.loginState)
        }
    }
}

// TODO : 클릭 시 내 정보 화면으로 네비게이션 (정호)
@Composable
private fun HomeAppBar(
    modifier: Modifier = Modifier,
    userName: String,
    onUserIconClick: () -> Unit,
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        val (userImage, nameText) = createRefs()

        IconButton(
            modifier = Modifier
                .size(30.dp, 30.dp)
                .constrainAs(userImage) {
                    start.linkTo(parent.start, margin = 22.dp)
                    top.linkTo(parent.top, margin = 14.dp)
                },
            onClick = { onUserIconClick() }) {
            Image(
                painter = painterResource(R.drawable.ic_default_user_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .border(0.79.dp, MainPurple900, CircleShape),
                contentDescription = null,
            )
        }
        Text(
            modifier = Modifier.constrainAs(nameText) {
                start.linkTo(userImage.end, margin = 12.dp)
                top.linkTo(parent.top, margin = 16.dp)
            },
            text = userName,
            style = PlanzTypography.h3,
            color = Gray900,
        )
    }
}

@Composable
fun HomeTopBox(loginState: HomeContract.LoginState) {
    when (loginState) {
        HomeContract.LoginState.LOGIN -> HomeIsLoginBox()
        HomeContract.LoginState.NONE -> HomeIsNotLoginBox()
    }
}

@Composable
fun HomeIsLoginBox() {

}

@Composable
fun HomeIsNotLoginBox() {

}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun PreviewHomeScreen() {
    PlanzTheme {
        HomeScreen()
    }
}
