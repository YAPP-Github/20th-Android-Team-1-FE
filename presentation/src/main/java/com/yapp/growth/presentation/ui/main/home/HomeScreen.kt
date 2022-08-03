package com.yapp.growth.presentation.ui.main.home

import android.app.Activity
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.yapp.growth.base.LoadState
import com.yapp.growth.domain.entity.Plan
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.component.PlanzCalendar
import com.yapp.growth.presentation.component.PlanzCalendarSelectMode
import com.yapp.growth.presentation.component.PlanzError
import com.yapp.growth.presentation.component.PlanzErrorSnackBar
import com.yapp.growth.presentation.component.PlanzLoading
import com.yapp.growth.presentation.theme.BackgroundColor1
import com.yapp.growth.presentation.theme.Gray200
import com.yapp.growth.presentation.theme.Gray500
import com.yapp.growth.presentation.theme.Gray900
import com.yapp.growth.presentation.theme.MainGradient
import com.yapp.growth.presentation.theme.MainPurple300
import com.yapp.growth.presentation.theme.MainPurple900
import com.yapp.growth.presentation.theme.PlanzTypography
import com.yapp.growth.presentation.ui.login.LoginActivity
import com.yapp.growth.presentation.ui.main.MainContract
import com.yapp.growth.presentation.ui.main.MainViewModel
import com.yapp.growth.presentation.ui.main.home.HomeContract.HomeEvent
import com.yapp.growth.presentation.ui.main.home.HomeContract.HomeSideEffect
import com.yapp.growth.presentation.util.advancedShadow
import com.yapp.growth.presentation.util.composableActivityViewModel
import com.yapp.growth.presentation.util.toDate
import com.yapp.growth.presentation.util.toHour
import com.yapp.growth.presentation.util.toHourAndMinute
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomeScreen(
    mainViewModel: MainViewModel = composableActivityViewModel(),
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToMyPageScreen: () -> Unit,
    navigateToDetailPlanScreen: (Int) -> Unit,
    showBottomSheet: (CalendarDay, List<Plan.FixedPlan>) -> Unit,
) {
    val viewState by viewModel.viewState.collectAsState()
    val currentDate by viewModel.currentDate.collectAsState()
    val context = LocalContext.current as Activity
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.setEvent(HomeEvent.InitHomeScreen)
    }

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeSideEffect.ShowSnackBar -> {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(effect.msg)
                    }
                }
                is HomeSideEffect.MoveToLogin -> {
                    LoginActivity.startActivity(context, null)
                    context.finish()
                }
                is HomeSideEffect.NavigateToMyPageScreen -> {
                    navigateToMyPageScreen()
                }
                is HomeSideEffect.NavigateDetailPlanScreen -> {
                    navigateToDetailPlanScreen(effect.planId)
                }
                is HomeSideEffect.ShowBottomSheet -> {
                    showBottomSheet(viewState.selectionDay, viewState.selectDayPlans)
                }
            }
        }
    }

    LaunchedEffect(key1 = mainViewModel.effect) {
        mainViewModel.effect.collect { effect ->
            when (effect) {
                is MainContract.MainSideEffect.RefreshScreen -> {
                    viewModel.setEvent(HomeEvent.InitHomeScreen)
                }
            }
        }
    }

    when (viewState.loadState) {
        LoadState.LOADING -> {
            Box(modifier = Modifier.fillMaxSize()) {
                PlanzLoading()
            }
        }
        LoadState.ERROR -> {
            Box(modifier = Modifier.fillMaxSize()) {
                PlanzError()
            }
        }
        LoadState.SUCCESS -> {
            Scaffold(
                backgroundColor = BackgroundColor1,
                topBar = {
                    HomeUserProfile(
                        userName = viewState.userName,
                        onUserIconClick = { viewModel.setEvent(HomeEvent.OnUserImageClicked) }
                    )
                },
                modifier = Modifier.fillMaxSize(),
                snackbarHost = { snackbarHostState ->
                    SnackbarHost(hostState = snackbarHostState) { snackbarData ->
                        PlanzErrorSnackBar(message = snackbarData.message)
                    }
                }
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
                            loadState = viewState.todayPlanLoadState,
                            expanded = viewState.isTodayPlanExpanded,
                            todayPlans = viewState.todayPlans,
                            planCount = viewState.todayPlans.size,
                            onPlanItemClick = { viewModel.setEvent(HomeEvent.OnPlanItemClicked(it)) },
                            onExpandedClick = { viewModel.setEvent(HomeEvent.OnTodayPlanExpandedClicked) }
                        )
                        HomeContract.LoginState.NONE -> HomeInduceLogin(
                            OnInduceLoginClick = { viewModel.setEvent(HomeEvent.OnInduceLoginClicked) }
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    HomeMonthlyPlan(
                        loadState = viewState.monthlyPlanLoadState,
                        expanded = viewState.isMonthlyPlanExpanded,
                        calendarPlans = viewState.calendarPlans,
                        monthlyPlans = viewState.monthlyPlans,
                        mode = viewState.monthlyPlanMode,
                        currentDate = currentDate,
                        onModeClick = { viewModel.setEvent(HomeEvent.OnMonthlyPlanModeClicked) },
                        onDateClick = { viewModel.setEvent(HomeEvent.OnCalendarDayClicked(it)) },
                        onPreviousClick = { viewModel.setEvent(HomeEvent.OnMonthlyPreviousClicked) },
                        onNextClick = { viewModel.setEvent(HomeEvent.OnMonthlyNextClicked) },
                        onPlanItemClick = { viewModel.setEvent(HomeEvent.OnPlanItemClicked(it)) },
                        onExpandedClick = { viewModel.setEvent(HomeEvent.OnMonthlyPlanExpandedClicked) }
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
private fun HomeUserProfile(
    userName: String,
    onUserIconClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .height(44.dp)
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable { onUserIconClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(R.drawable.ic_default_user_image_32),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .border(1.dp, MainPurple900, CircleShape),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = userName,
                style = PlanzTypography.h3,
                color = Gray900,
            )
        }
    }
}

@Composable
fun HomeTodayPlan(
    loadState: LoadState,
    expanded: Boolean,
    todayPlans: List<Plan.FixedPlan>,
    planCount: Int,
    onPlanItemClick: (Int) -> Unit,
    onExpandedClick: () -> Unit,
) {
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
                    when (loadState) {
                        LoadState.LOADING -> {
                            Box(modifier = Modifier.size(16.dp)) {
                                PlanzLoading()
                            }
                        }
                        LoadState.ERROR -> {
                            Icon(
                                tint = Color.Unspecified,
                                imageVector = ImageVector.vectorResource(R.drawable.ic_blue_error),
                                contentDescription = null,
                            )
                        }
                        LoadState.SUCCESS -> {
                            HomeTodayPlanCountText(planCount)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                if (todayPlans.isNotEmpty())
                    HomeDayPlanList(
                        expanded = expanded,
                        dayPlans = todayPlans,
                        onPlanItemClick = onPlanItemClick
                    )
            }
            if (todayPlans.size >= 2) {
                IconButton(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .size(12.dp, 6.dp)
                        .align(Alignment.BottomCenter),
                    onClick = { onExpandedClick() }) {
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
}


@Composable
fun HomeInduceLogin(
    OnInduceLoginClick: () -> Unit,
) {
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
            )
    ) {
        Box(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .background(brush = MainGradient)
                .clickable { OnInduceLoginClick() },
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
                Icon(
                    modifier = Modifier.size(6.dp, 12.dp),
                    tint = Color.Unspecified,
                    imageVector = ImageVector.vectorResource(R.drawable.ic_transparent_arrow_right),
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
fun HomeMonthlyPlan(
    loadState: LoadState,
    expanded: Boolean,
    calendarPlans: List<Plan.FixedPlan>,
    monthlyPlans: List<Plan.FixedPlan>,
    mode: HomeContract.MonthlyPlanModeState,
    currentDate: CalendarDay,
    onModeClick: () -> Unit,
    onDateClick: (CalendarDay) -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onPlanItemClick: (Int) -> Unit,
    onExpandedClick: () -> Unit,
) {
    val year: Int = currentDate.year
    val month: Int = currentDate.month + 1

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
                            .clickable { onPreviousClick() },
                        tint = Color.Unspecified,
                        imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_box_left_20),
                        contentDescription = null,
                    )
                    Icon(
                        modifier = Modifier
                            .padding(start = 136.dp)
                            .clickable { onNextClick() },
                        tint = Color.Unspecified,
                        imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_box_right_20),
                        contentDescription = null,
                    )
                    Icon(
                        modifier = Modifier
                            .align(alignment = Alignment.CenterEnd)
                            .clickable { onModeClick() },
                        tint = Color.Unspecified,
                        imageVector = if (mode == HomeContract.MonthlyPlanModeState.CALENDAR) {
                            ImageVector.vectorResource(R.drawable.ic_list)
                        } else {
                            ImageVector.vectorResource(R.drawable.ic_calendar)
                        },
                        contentDescription = null,
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Divider(color = Gray200, thickness = 1.dp)
                when (loadState) {
                    LoadState.LOADING -> {
                        Box(modifier = Modifier.height(264.dp)) {
                            PlanzLoading()
                        }
                    }
                    LoadState.ERROR -> {
                        Box(modifier = Modifier.height(264.dp)) {
                            PlanzError()
                        }
                    }
                    LoadState.SUCCESS -> {
                        if (mode == HomeContract.MonthlyPlanModeState.CALENDAR) {
                            HomeCalendar(
                                currentDate = currentDate,
                                calendarPlans = calendarPlans,
                                onDateClick = onDateClick
                            )
                        } else {
                            Spacer(modifier = Modifier.height(10.dp))
                            if (monthlyPlans.isNotEmpty()) {
                                HomeMonthlyPlanList(
                                    monthlyPlans = monthlyPlans,
                                    expanded = expanded,
                                    onExpandedClick = onExpandedClick,
                                    onPlanItemClick = onPlanItemClick
                                )
                            } else {
                                Spacer(modifier = Modifier.height(40.dp))
                                Image(
                                    painter = painterResource(R.drawable.ic_calendar_with_chracter),
                                    contentScale = ContentScale.Crop,
                                    contentDescription = null,
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = stringResource(id = R.string.home_no_plan),
                                    color = Gray500,
                                    style = MaterialTheme.typography.body2,
                                )
                                Spacer(modifier = Modifier.height(52.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun HomeCalendar(
    currentDate: CalendarDay,
    calendarPlans: List<Plan.FixedPlan>,
    onDateClick: (CalendarDay) -> Unit,
) {

    val calendarPlanDates = (calendarPlans.groupingBy {
        CalendarDay.from(it.date.toDate())
    }.eachCount().filter { it.value >= 1 })

    PlanzCalendar(
        currentDate = currentDate,
        selectMode = PlanzCalendarSelectMode.SINGLE,
        onDateSelectedListener = { widget, date, selected ->
            onDateClick(date)
        },
        monthlyDates = calendarPlanDates
    )
}

@Composable
fun HomeTodayPlanCountText(
    planCount: Int = 0,
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
fun HomeDayPlanList(
    expanded: Boolean,
    dayPlans: List<Plan.FixedPlan>,
    onPlanItemClick: (Int) -> Unit,
) {
    Column(
        modifier = Modifier.padding(bottom = 36.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        if (expanded) {
            for (todayPlan in dayPlans) {
                DayPlanItem(
                    id = todayPlan.id,
                    date = todayPlan.date,
                    category = todayPlan.category.keyword,
                    title = todayPlan.title,
                    onPlanItemClick = onPlanItemClick
                )
            }
        } else {
            DayPlanItem(
                id = dayPlans[0].id,
                date = dayPlans[0].date,
                title = (if (dayPlans.size == 1) {
                    dayPlans[0].title
                } else {
                    "${dayPlans[0].title} 외 ${dayPlans.size - 1}건"
                }),
                category = dayPlans[0].category.keyword,
                onPlanItemClick = onPlanItemClick
            )
        }
    }
}

@Composable
fun HomeMonthlyPlanList(
    monthlyPlans: List<Plan.FixedPlan>,
    expanded: Boolean,
    onPlanItemClick: (Int) -> Unit,
    onExpandedClick: () -> Unit,
) {
    Column {
        if (expanded) {
            for (monthlyPlan in monthlyPlans) {
                Timber.d(monthlyPlan.toString())
                HomeMonthlyPlanItem(
                    id = monthlyPlan.id,
                    date = monthlyPlan.date,
                    title = monthlyPlan.title,
                    onPlanItemClick = onPlanItemClick
                )
            }
        } else {
            if (monthlyPlans.size < 5) {
                for (monthlyPlan in monthlyPlans) {
                    HomeMonthlyPlanItem(
                        id = monthlyPlan.id,
                        date = monthlyPlan.date,
                        title = monthlyPlan.title,
                        onPlanItemClick = onPlanItemClick
                    )
                }
            } else {
                for (i in 0 until 4) {
                    HomeMonthlyPlanItem(
                        id = monthlyPlans[i].id,
                        date = monthlyPlans[i].date,
                        title = monthlyPlans[i].title,
                        onPlanItemClick = onPlanItemClick
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
    if (monthlyPlans.size >= 5) {
        IconButton(
            modifier = Modifier
                .padding(bottom = 9.dp)
                .size(12.dp, 6.dp),
            onClick = { onExpandedClick() },
        ) {
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


@Composable
fun DayPlanItem(
    id: Int,
    date: String,
    category: String,
    title: String,
    onPlanItemClick: (Int) -> Unit,
) {
    val tmp = date.toDate()
    val calendar: Calendar = Calendar.getInstance()
    calendar.time = tmp

    val minute = calendar.get(Calendar.MINUTE)
    val time = if (minute == 0) tmp.toHour() else tmp.toHourAndMinute()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onPlanItemClick(id) }),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                tint = Color.Unspecified,
                imageVector = (
                        when (category) {
                            stringResource(R.string.create_plan_theme_list_meal) -> ImageVector.vectorResource(
                                id = R.drawable.ic_plan_meal
                            )
                            stringResource(R.string.create_plan_theme_list_trip) -> ImageVector.vectorResource(
                                id = R.drawable.ic_plan_trip
                            )
                            stringResource(R.string.create_plan_theme_list_meeting) -> ImageVector.vectorResource(
                                id = R.drawable.ic_plan_meeting
                            )
                            else -> ImageVector.vectorResource(id = R.drawable.ic_plan_etc)
                        }),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = time,
                    color = Gray500,
                    style = MaterialTheme.typography.caption,
                )
                Text(
                    text = title,
                    color = Color.Black,
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun HomeMonthlyPlanItem(
    id: Int,
    date: String,
    title: String,
    onPlanItemClick: (Int) -> Unit,
) {
    val time = SimpleDateFormat("aa h시", Locale.KOREA).format(date.toDate())
    val dates = SimpleDateFormat("M/d", Locale.KOREA).format(date.toDate())

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clickable { onPlanItemClick(id) },
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = dates,
            color = MainPurple900,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.padding(horizontal = 5.dp)
        )
        Text(
            text = title,
            color = Color.Black,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(horizontal = 54.dp)
        )
        Text(
            text = time,
            color = Gray500,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}
