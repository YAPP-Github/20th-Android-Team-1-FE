package com.yapp.growth.presentation.ui.main.manage

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.yapp.growth.domain.entity.Plan
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.component.PlanzCreateAppBar
import com.yapp.growth.presentation.theme.*
import com.yapp.growth.presentation.ui.main.manage.ManageContract.ManageEvent
import com.yapp.growth.presentation.ui.main.manage.ManageContract.ManageSideEffect

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ManageScreen(
    viewModel: ManageViewModel = hiltViewModel(),
    intentToCreateScreen: () -> Unit,
    navigateToFixPlanScreen: (Int) -> Unit,
    navigateToMemberResponseScreen: (Int) -> Unit,
    navigateToMonitorPlanScreen: (Int) -> Unit,
    navigateToDetailPlanScreen: (Int) -> Unit,
) {
    val viewState by viewModel.viewState.collectAsState()

    val pagerState = rememberPagerState(
        initialPage = 0,
    )
    val tabIndex = pagerState.currentPage

    Scaffold(
        topBar = {
            PlanzCreateAppBar(
                title = stringResource(id = R.string.manage_plan_app_bar_text),
                onCreateClick = { viewModel.setEvent(ManageEvent.OnClickCreateButton) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            ManageTabRow(
                tabIndex = tabIndex,
                waitingPlans = viewState.waitingPlans,
                fixedPlans = viewState.fixedPlans,
                onTabClick = { index ->
                    viewModel.setEvent(ManageEvent.OnClickTab(index))
                }
            )

            ManagePager(
                pagerState = pagerState,
                pageCount = ManageTapMenu.values().size,
                waitingPlans = viewState.waitingPlans,
                fixedPlans = viewState.fixedPlans,
                onWaitingItemClick = { planId ->
                    viewModel.setEvent(ManageEvent.OnClickWaitingPlan(planId))
                },
                onFixedItemClick = { planId ->
                    viewModel.setEvent(ManageEvent.OnClickFixedPlan(planId))
                },
                onCreateButtonClick = { viewModel.setEvent(ManageEvent.OnClickCreateButton) }
            )
        }
    }

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ManageSideEffect.NavigateToCreateScreen -> {
                    intentToCreateScreen()
                }
                is ManageSideEffect.NavigateToFixPlanScreen -> {
                    navigateToFixPlanScreen(effect.planId)
                }
                is ManageSideEffect.NavigateToMemberResponseScreen -> {
                    navigateToMemberResponseScreen(effect.planId)
                }
                is ManageSideEffect.NavigateToMonitorPlanScreen -> {
                    navigateToMonitorPlanScreen(effect.planId)
                }
                is ManageSideEffect.NavigateToDetailPlanScreen -> {
                    navigateToDetailPlanScreen(effect.planId)
                }
                is ManageSideEffect.SwitchTab -> {
                    pagerState.animateScrollToPage(effect.tabIndex)
                }
            }
        }
    }
}

@Composable
fun ManageTabRow(
    tabIndex: Int,
    waitingPlans: List<Plan.WaitingPlan>,
    fixedPlans: List<Plan.FixedPlan>,
    onTabClick: (Int) -> Unit,
) {
    TabRow(
        selectedTabIndex = tabIndex,
        backgroundColor = Color.Transparent,
        indicator = @Composable { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier
                    .tabIndicatorOffset(tabPositions[tabIndex])
                    .padding(horizontal = 36.dp),
                color = Gray900
            )
        },
        divider = @Composable { TabRowDefaults.Divider(color = Gray200) }
    ) {
        ManageTapMenu.values().forEachIndexed { index, menu ->
            val planCount = when (menu) {
                ManageTapMenu.WAITING_PLAN -> waitingPlans.size
                ManageTapMenu.FIXED_PLAN -> fixedPlans.size
            }

            Tab(
                selected = tabIndex == index,
                onClick = { onTabClick(index) },
                text = {
                    Text(
                        text = stringResource(id = menu.textId) + "($planCount)",
                        style = PlanzTypography.subtitle1,
                        color = Color.Unspecified
                    )
                },
                selectedContentColor = Gray900,
                unselectedContentColor = Gray300
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ManagePager(
    pagerState: PagerState,
    pageCount: Int,
    waitingPlans: List<Plan.WaitingPlan>,
    fixedPlans: List<Plan.FixedPlan>,
    onWaitingItemClick: (Int) -> Unit,
    onFixedItemClick: (Int) -> Unit,
    onCreateButtonClick: () -> Unit,
) {
    HorizontalPager(
        count = pageCount,
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
    ) { tabIndex ->
        when (tabIndex) {
            ManageTapMenu.WAITING_PLAN.ordinal -> {
                if (waitingPlans.isNotEmpty()) {
                    ManagePlansList(
                        plans = waitingPlans,
                        type = ManageTapMenu.WAITING_PLAN,
                        onItemClick = onWaitingItemClick
                    )
                } else ManageEmptyView(onCreateButtonClick = onCreateButtonClick)
            }
            ManageTapMenu.FIXED_PLAN.ordinal -> {
                if (fixedPlans.isNotEmpty()) {
                    ManagePlansList(
                        plans = fixedPlans,
                        type = ManageTapMenu.FIXED_PLAN,
                        onItemClick = onFixedItemClick
                    )
                } else ManageEmptyView(onCreateButtonClick = onCreateButtonClick)
            }
        }
    }
}

@Composable
fun ManagePlansList(
    plans: List<Plan>,
    type: ManageTapMenu,
    onItemClick: (Int) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(24.dp))
        }

        items(plans.size) { index ->
            ManagePlansItem(
                plan = plans[index],
                type = type,
                onItemClick = onItemClick
            )
        }

        item {
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun ManagePlansItem(
    plan: Plan,
    type: ManageTapMenu,
    onItemClick: (Int) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onItemClick(plan.id) },
        shape = RoundedCornerShape(12.dp),
        elevation = 0.dp,
        border = BorderStroke(1.dp, Gray200),
        backgroundColor = Color(0xFFFBFCFF)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.wrapContentWidth(),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Text(
                        text = plan.title,
                        style = PlanzTypography.subtitle1,
                        color = Gray900
                    )
                    ManageLeaderBadge(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        isLeader = plan.isLeader
                    )
                }

                Text(
                    text = when (type) {
                        ManageTapMenu.FIXED_PLAN -> {
                            if (plan.members.size < 5) {
                                val members = plan.members.toString()
                                members.substring(1..members.length - 2)
                            } else {
                                plan.members[0] + " 외 ${plan.members.size - 1}명"
                            }
                        }
                        ManageTapMenu.WAITING_PLAN -> {
                            "${plan.members.size}" + stringResource(id = R.string.manage_plan_completed_member_count_text)
                        }
                    },
                    style = PlanzTypography.caption,
                    color = Gray500
                )
            }

            if (type == ManageTapMenu.WAITING_PLAN && plan.isLeader) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_right),
                    contentDescription = "",
                    tint = Color.Unspecified
                )
            }
        }
    }
}

@Composable
fun ManageLeaderBadge(
    modifier: Modifier = Modifier,
    isLeader: Boolean,
) {
    Card(
        modifier = modifier
            .wrapContentWidth()
            .height(20.dp),
        shape = RoundedCornerShape(4.dp),
        elevation = 0.dp,
        backgroundColor = if (isLeader) MainPurple200 else Color(0xFFFFECEB)
    ) {
        Text(
            modifier = modifier
                .padding(horizontal = 8.dp)
                .wrapContentHeight(),
            text = stringResource(
                id = if (isLeader) R.string.manage_plan_leader_badge
                else R.string.manage_plan_follower_badge
            ),
            style = PlanzTypography.caption,
            color = if (isLeader) MainPurple900 else SubCoral
        )
    }
}

@Composable
fun ManageEmptyView(
    onCreateButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.image_empty_invitation),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(id = R.string.manage_plan_empty_view_text),
            style = PlanzTypography.body2,
            color = CoolGray300,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(20.dp))
        ManageCreateButton(onCreateButtonClick = onCreateButtonClick)
    }
}

@Composable
fun ManageCreateButton(
    onCreateButtonClick: () -> Unit,
) {
    OutlinedButton(
        modifier = Modifier
            .height(36.dp)
            .width(96.dp),
        border = BorderStroke(1.dp, MainPurple900),
        shape = RoundedCornerShape(8.dp),
        onClick = onCreateButtonClick
    ) {
        Text(
            text = stringResource(id = R.string.manage_plan_empty_view_create_button_text),
            style = PlanzTypography.caption,
            color = MainPurple900
        )
    }
}

enum class ManageTapMenu(@StringRes val textId: Int) {
    WAITING_PLAN(R.string.manage_plan_waiting_plan_text),
    FIXED_PLAN(R.string.manage_plan_fixed_plan_text)
}

@Preview
@Composable
fun WaitingPlanItemPreview() {
    ManagePlansItem(
        plan = Plan.WaitingPlan(
            id = 0,
            title = "plan title",
            isLeader = true,
            category = "식사",
            members = listOf("member1", "member2", "member3", "member4"),
            place = "place",
            startTime = 0,
            endTime = 24,
            isAlreadyReplied = false,
        ),
        type = ManageTapMenu.WAITING_PLAN,
        onItemClick = {}
    )
}

@Preview
@Composable
fun FixedPlanItemPreview() {
    ManagePlansItem(
        plan = Plan.FixedPlan(
            id = 0,
            title = "plan title",
            isLeader = false,
            category = "식사",
            members = listOf("member1", "member2", "member3", "member4", "member5"),
            place = "",
            date = "",
        ),
        type = ManageTapMenu.FIXED_PLAN,
        onItemClick = {}
    )
}

@Preview
@Composable
fun ManageLevelBadgePreview() {
    ManageLeaderBadge(isLeader = true)
}

@Preview
@Composable
fun ManageScreenPreview() {
    ManageScreen(
        intentToCreateScreen = {},
        navigateToFixPlanScreen = {},
        navigateToMemberResponseScreen = {},
        navigateToMonitorPlanScreen = {},
        navigateToDetailPlanScreen = {}
    )
}

@Preview
@Composable
fun ManageCreateButtonPreview() {
    ManageCreateButton(onCreateButtonClick = {})
}
