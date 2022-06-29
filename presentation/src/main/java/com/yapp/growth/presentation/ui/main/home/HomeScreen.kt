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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.theme.*
import com.yapp.growth.presentation.ui.main.home.HomeContract.HomeSideEffect
import kotlinx.coroutines.flow.collect
import timber.log.Timber


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
            Spacer(modifier = Modifier.padding(8.dp))
            HomeBottomBox()
            Spacer(modifier = Modifier.padding(20.dp))
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
                            ItemTodayPlan()
                        }
                    } else {
                        ItemTodayPlan()
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
    Box(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(brush = MainGradient),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 19.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "회원가입하고, 편리한 약속잡기를 경험하세요!",
                color = Color.White,
                style = MaterialTheme.typography.subtitle2,
            )
            IconButton(
                modifier = Modifier.size(5.7.dp, 12.dp),
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

@Composable
fun HomeBottomBox() {
    var isCalendarMode by remember { mutableStateOf(true) }
    var expanded by remember { mutableStateOf(false) }

    var currentDate: CalendarDay by remember { mutableStateOf(CalendarDay.today()) }

    var year: Int by remember { mutableStateOf(currentDate.year) }
    var month: Int by remember { mutableStateOf(currentDate.month + 1) }

    Surface(
        color = Color.White,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp)
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
                Spacer(modifier = Modifier.padding(top = 21.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${year}년 ${month}월",
                        style = PlanzTypography.h3
                    )
                    Spacer(modifier = Modifier.padding(3.dp))
                    OutlinedButton(
                        onClick = {
                            month--
                            if (month == 0) {
                                year--
                                month = 12
                            }
                            currentDate = CalendarDay.from(year, month - 1, 1)
                        },
                        modifier = Modifier.size(25.dp),
                        shape = CircleShape,
                        border = BorderStroke(1.dp, Color.Transparent),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.DarkGray),
                    ) {
                        Icon(
                            tint = Color.Unspecified,
                            imageVector = ImageVector.vectorResource(R.drawable.ic_calendar_arrow_left),
                            contentDescription = null,
                        )
                    }
                    OutlinedButton(
                        onClick = {
                            month++
                            if (month == 13) {
                                year++
                                month = 1
                            }
                            currentDate = CalendarDay.from(year, month - 1, 1)
                        },
                        modifier = Modifier.size(25.dp),
                        shape = CircleShape,
                        border = BorderStroke(1.dp, Color.Transparent),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.DarkGray),
                    ) {
                        Icon(
                            tint = Color.Unspecified,
                            imageVector = ImageVector.vectorResource(R.drawable.ic_calendar_arrow_right),
                            contentDescription = null,
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    OutlinedButton(
                        onClick = {
                            isCalendarMode = !isCalendarMode
                        },
                        modifier = Modifier.size(32.dp),
                        shape = CircleShape,
                        border = BorderStroke(1.dp, Color.Transparent),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.DarkGray),
                    ) {
                        Icon(
                            tint = Color.Unspecified,
                            imageVector =
                            if (isCalendarMode) {
                                ImageVector.vectorResource(R.drawable.ic_list)
                            } else {
                                ImageVector.vectorResource(R.drawable.ic_calendar)
                            },
                            contentDescription = null,
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(top = 12.dp))
                Divider(color = Gray200, thickness = 1.dp)
                Spacer(modifier = Modifier.padding(top = 10.dp))
                if (isCalendarMode) {
                    // PlanCalendar(currentDate)
                } else {
                    Column(
                        modifier = Modifier.padding(top = 11.dp, bottom = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(9.5.dp),
                    ) {
                        if (expanded) {
                            // TODO : 예시 화면 (정호)
                            for (i in 0 until 10) {
                                ItemTextPlan("그로스 회의회의")
                            }
                        } else {
                            for (i in 0 until 4) {
                                ItemTextPlan("그로스 회의회의")
                            }
                        }
                    }
                    IconButton(
                        modifier = Modifier
                            .padding(bottom = 9.dp)
                            .size(12.3.dp, 6.47.dp),
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
            }
        }
    }
}

// TODO : 추후 공통 컴포넌트로 이동 (정호)
@Composable
fun PlanCalendar(
    currentDate: CalendarDay
) {
    AndroidView(
        { MaterialCalendarView(it) },
        update = { views ->
            views.apply {
                this.setOnDateChangedListener { widget, date, selected ->
                    Timber.d(date.toString())
                }
                this.selectionMode = MaterialCalendarView.SELECTION_MODE_SINGLE
                this.selectedDate = CalendarDay.today()
                this.showOtherDates = MaterialCalendarView.SHOW_OTHER_MONTHS
                this.setAllowClickDaysOutsideCurrentMonth(false)
                this.currentDate = currentDate
                this.isDynamicHeightEnabled = true
                this.topbarVisible = false
                this.isPagingEnabled = false
            }
        }
    )
}

// TODO : API 연동 및 매개변수 추가(정호)
@Composable
fun ItemTodayPlan() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = { /*TODO*/ }
            ),
    )
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // TODO : 약속 종류에 따라 아이콘 변경 (정호)
            Icon(
                tint = Color.Unspecified,
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_plan_meal),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.padding(8.dp))
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
fun ItemTextPlan(content: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
    )
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.padding(5.dp))
            Text(
                text = "6/26",
                color = MainPurple900,
                style = MaterialTheme.typography.subtitle2,
            )
            Spacer(modifier = Modifier.padding(20.dp))
            Text(
                text = content,
                color = Color.Black,
                style = MaterialTheme.typography.body2,
            )
            Spacer(modifier = Modifier.weight(1F))
            Text(
                text = "10시 00분",
                color = Gray500,
                style = MaterialTheme.typography.caption,
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun PreviewHomeScreen() {
    PlanzTheme {
        HomeScreen()
    }
}
