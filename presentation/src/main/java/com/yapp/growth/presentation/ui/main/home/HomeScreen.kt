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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.component.PlanzCalendar
import com.yapp.growth.presentation.component.PlanzCalendarSelectMode
import com.yapp.growth.presentation.theme.*
import com.yapp.growth.presentation.ui.main.home.HomeContract.HomeSideEffect
import com.yapp.growth.presentation.ui.main.home.HomeContract.HomeEvent
import com.yapp.growth.presentation.util.advancedShadow
import timber.log.Timber


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToMyPageScreen: () -> Unit,
    navigateToDetailPlanScreen: () -> Unit,
) {

    val viewState by viewModel.viewState.collectAsState()

    // TODO : 이벤트에 따라 사이드 이펙트 적용되게 설정 (정호)
    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeSideEffect.NavigateToMyPageScreen -> {
                    navigateToMyPageScreen()
                }
                is HomeSideEffect.NavigateDetailPlanScreen -> {
                    // TODO : 해당 약속의 인덱스값을 함께 보내주어야 함 (정호)
                    navigateToDetailPlanScreen()
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
            HomeUserProfile(
                userName = "김정호",
                onUserIconClick = { viewModel.setEvent(HomeEvent.OnUserImageClicked) }
            )
        },
        modifier = Modifier.fillMaxSize(),
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(3.dp))
            when (viewState.loginState) {
                HomeContract.LoginState.LOGIN -> HomeTodayPlan(
                    onPlanItemClick = {
                        viewModel.setEvent(HomeEvent.OnTodayPlanItemClicked)
                        Timber.d("Plan Item Clicked")
                    }
                )
                HomeContract.LoginState.NONE -> HomeInduceLogin()
            }
            Spacer(modifier = Modifier.height(16.dp))
            HomeMonthlyPlan()
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// TODO : 클릭 시 내 정보 화면으로 네비게이션 (정호)
@Composable
private fun HomeUserProfile(
    userName: String,
    onUserIconClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier
                .size(30.dp, 30.dp),
            onClick = { onUserIconClick() }) {
            Image(
                painter = painterResource(R.drawable.ic_default_user_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .border(1.dp, MainPurple900, CircleShape),
                contentDescription = null,
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = userName,
            style = PlanzTypography.h3,
            color = Gray900,
        )
    }
}

// TODO : 약속 수 들어가는 로직 넣기 (정호)
@Composable
fun HomeTodayPlan(
    onPlanItemClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .advancedShadow(
                alpha = 0.1f,
                cornersRadius = 12.dp,
                shadowBlurRadius = 10.dp,
                offsetY = 7.dp
            ),
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
                        text = stringResource(id = R.string.home_today_plan),
                        color = Color.Black,
                        style = MaterialTheme.typography.h3,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    HomeTodayPlanCountText(planCount = 5)
                }
                Spacer(modifier = Modifier.height(20.dp))
                HomeTodayPlanList(
                    expanded = expanded,
                    onPlanItemClick = onPlanItemClick
                )
            }
            IconButton(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .size(12.dp, 6.dp)
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
fun HomeInduceLogin() {
    Surface(
        color = Color.Transparent,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .advancedShadow(
                alpha = 0.1f,
                cornersRadius = 12.dp,
                shadowBlurRadius = 10.dp,
                offsetY = 7.dp
            ),
    ) {
        Box(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .background(brush = MainGradient),
            contentAlignment = Alignment.Center,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = stringResource(id = R.string.home_induce_login),
                    color = Color.White,
                    style = MaterialTheme.typography.subtitle2,
                )
                IconButton(
                    modifier = Modifier.size(6.dp, 12.dp),
                    onClick = { /*TODO*/ },
                ) {
                    Icon(
                        tint = Color.Unspecified,
                        imageVector = ImageVector.vectorResource(R.drawable.ic_transparent_arrow_right),
                        contentDescription = null,
                    )
                }
            }
        }
    }
}

@Composable
fun HomeMonthlyPlan() {
    var isCalendarMode by remember { mutableStateOf(true) }
    var currentDate: CalendarDay by remember { mutableStateOf(CalendarDay.today()) }
    var year: Int by remember { mutableStateOf(currentDate.year) }
    var month: Int by remember { mutableStateOf(currentDate.month + 1) }

    Surface(
        color = Color.White,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .advancedShadow(
                alpha = 0.1f,
                cornersRadius = 12.dp,
                shadowBlurRadius = 10.dp
            ),
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = "${year}년 ${String.format("%02d", month)}월",
                        style = PlanzTypography.h3,
                    )
                    Icon(
                        modifier = Modifier
                            .padding(start = 112.dp)
                            .clickable {
                                month--
                                if (month == 0) {
                                    year--
                                    month = 12
                                }
                                currentDate = CalendarDay.from(year, month - 1, 1)
                            },
                        tint = Color.Unspecified,
                        imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_box_left_20),
                        contentDescription = null,
                    )
                    Icon(
                        modifier = Modifier
                            .padding(start = 136.dp)
                            .clickable {
                                month++
                                if (month == 13) {
                                    year++
                                    month = 1
                                }
                                currentDate = CalendarDay.from(year, month - 1, 1)
                            },
                        tint = Color.Unspecified,
                        imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_box_right_20),
                        contentDescription = null,
                    )
                    Icon(
                        modifier = Modifier
                            .align(alignment = Alignment.CenterEnd)
                            .clickable {
                                isCalendarMode = !isCalendarMode
                            },
                        tint = Color.Unspecified,
                        imageVector = if (isCalendarMode) {
                            ImageVector.vectorResource(R.drawable.ic_list)
                        } else {
                            ImageVector.vectorResource(R.drawable.ic_calendar)
                        },
                        contentDescription = null,
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Divider(color = Gray200, thickness = 1.dp)
                if (isCalendarMode) {
                    HomeCalendar(currentDate)
                } else {
                    Spacer(modifier = Modifier.height(20.dp))
                    HomeMonthlyPlanList()
                }
            }
        }
    }
}

// TODO : 추후 공통 컴포넌트로 이동 (정호)
@Composable
fun HomeCalendar(
    currentDate: CalendarDay
) {
    PlanzCalendar(
        currentDate = currentDate,
        selectMode = PlanzCalendarSelectMode.SINGLE,
        onDateSelectedListener = { widget, date, selected ->
            Timber.d(date.toString())
        }
    )
}

@Composable
fun HomeTodayPlanCountText(
    planCount: Int = 0
) {
    Box(
        modifier = Modifier
            .size(28.dp, 18.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color = MainPurple300),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier.fillMaxHeight(),
            text = "$planCount",
            color = MainPurple900,
            style = MaterialTheme.typography.caption,
        )
    }
}

@Composable
fun HomeTodayPlanList(
    expanded: Boolean,
    onPlanItemClick: () -> Unit,
) {
    Column(
        modifier = Modifier.padding(bottom = 36.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        // TODO : API 연동
        if (expanded) {
            for (i in 0 until 3) {
                HomeTodayPlanItem(onPlanItemClick = onPlanItemClick)
            }
        } else {
            HomeTodayPlanItem(onPlanItemClick = onPlanItemClick)
        }
    }
}

@Composable
fun HomeMonthlyPlanList() {
    var expanded by remember { mutableStateOf(false) }
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        if (expanded) {
            // TODO : 예시 화면 (정호)
            for (i in 0 until 10) {
                HomeMonthlyPlanItem("그로스 회의회의")
            }
        } else {
            for (i in 0 until 4) {
                HomeMonthlyPlanItem("그로스 회의회의")
            }
        }
    }
    Spacer(modifier = Modifier.height(24.dp))
    IconButton(
        modifier = Modifier
            .padding(bottom = 9.dp)
            .size(12.dp, 6.dp),
        onClick = { expanded = !expanded },
    )
    {
        Icon(
            tint = Color.Unspecified,
            imageVector =
            if (expanded) {
                ImageVector.vectorResource(R.drawable.ic_transparent_arrow_top)
            } else {
                ImageVector.vectorResource(R.drawable.ic_transparent_arrow_bottom)
            },
            contentDescription = null
        )
    }
}


// TODO : API 연동 및 매개변수 추가(정호)
@Composable
fun HomeTodayPlanItem(
    onPlanItemClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onPlanItemClick
            ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // TODO : 약속 종류에 따라 아이콘 변경 (정호)
            Icon(
                tint = Color.Unspecified,
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_plan_meal),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "6시 30분",
                    color = Gray500,
                    style = MaterialTheme.typography.caption,
                )
                Text(
                    text = "돼지파티 약속 외 1건",
                    color = Color.Black,
                    style = MaterialTheme.typography.subtitle1,
                )
            }
        }
    }
}

// TODO : API 연동 및 매개변수 추가 (정호)
@Composable
fun HomeMonthlyPlanItem(content: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Text(
            text = "7/15",
            color = MainPurple900,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.padding(horizontal = 5.dp)
        )
        Text(
            text = content,
            color = Color.Black,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(horizontal = 54.dp)
        )
        Text(
            text = "6시 30분",
            color = Gray500,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun PreviewHomeScreen() {
    PlanzTheme {
        HomeScreen(
            navigateToMyPageScreen = { },
            navigateToDetailPlanScreen = { },
        )
    }
}
