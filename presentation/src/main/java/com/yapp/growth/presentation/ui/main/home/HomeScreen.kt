package com.yapp.growth.presentation.ui.main.home

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
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

// TODO : 약속 수 들어가는 로직 넣기 (정호)
@Composable
fun HomeIsLoginBox() {
    var expanded by remember { mutableStateOf(false) }
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp),
    ) {
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                ),
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "오늘의 약속",
                        color = Color.Black,
                        style = MaterialTheme.typography.h3,
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Box(
                        modifier = Modifier
                            .size(27.dp, 18.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(color = MainPurple300),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            modifier = Modifier.fillMaxHeight(),
                            text = "0",
                            color = MainPurple900,
                            style = MaterialTheme.typography.caption,
                        )
                    }
                }

                Column(
                    modifier = Modifier.padding(top = 22.dp, bottom = 36.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                ) {
                    // TODO : API 연동
                    if (expanded) {
                        for (i in 0 until 3) {
                            // ItemTodayPlan()
                        }
                    } else {
                        // ItemTodayPlan()
                    }
                }
            }

            IconButton(
                modifier = Modifier
                    .padding(bottom = 9.dp)
                    .size(12.3.dp, 6.47.dp)
                    .align(Alignment.BottomCenter),
                onClick = { expanded = !expanded }) {
                Icon(
                    tint = Color.Unspecified,
                    imageVector = (
                            if (expanded) {
                                ImageVector.vectorResource(R.drawable.ic_transparent_arrow_top)
                            } else {
                                ImageVector.vectorResource(R.drawable.ic_transparent_arrow_bottom)
                            }),
                    contentDescription = null,
                )
            }
        }
    }
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
