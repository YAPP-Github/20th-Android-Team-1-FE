package com.yapp.growth.presentation.ui.main

import android.app.Activity
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.yapp.growth.domain.entity.Plan
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.component.PlanzModalBottomSheetLayout
import com.yapp.growth.presentation.firebase.PLAN_ID_KEY_NAME
import com.yapp.growth.presentation.firebase.SchemeType
import com.yapp.growth.presentation.theme.*
import com.yapp.growth.presentation.ui.login.LoginActivity
import com.yapp.growth.presentation.ui.main.confirm.FixPlanScreen
import com.yapp.growth.presentation.ui.main.detail.DetailPlanScreen
import com.yapp.growth.presentation.ui.main.home.DayPlanItem
import com.yapp.growth.presentation.ui.main.home.HomeScreen
import com.yapp.growth.presentation.ui.main.manage.ManageScreen
import com.yapp.growth.presentation.ui.main.manage.respond.result.AlreadyConfirmPlanScreen
import com.yapp.growth.presentation.ui.main.manage.respond.result.FulledPlanScreen
import com.yapp.growth.presentation.ui.main.monitor.MonitorPlanScreen
import com.yapp.growth.presentation.ui.main.myPage.MyPageScreen
import com.yapp.growth.presentation.ui.main.privacyPolicy.PrivacyPolicyScreen
import com.yapp.growth.presentation.ui.main.respond.RespondPlanScreen
import com.yapp.growth.presentation.ui.main.respond.result.RespondPlanCompleteScreen
import com.yapp.growth.presentation.ui.main.respond.result.RespondPlanRejectScreen
import com.yapp.growth.presentation.ui.main.terms.TermsScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlanzScreen(
    viewModel: PlanzViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
    intentToCreatePlan: () -> Unit,
    uri: Uri? = null,
) {
    val viewState by viewModel.viewState.collectAsState()
    var bottomBarState by rememberSaveable { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val context = LocalContext.current as Activity
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight
    var statusBarColor: Color by remember { mutableStateOf(Color.White) }

    statusBarColor = when (currentDestination?.route) {
        PlanzScreenRoute.HOME.route -> {
            BackgroundColor1
        }
        PlanzScreenRoute.MY_PAGE.route -> {
            BackgroundColor1
        }
        else -> {
            Color.White
        }
    }

    if (!sheetState.isVisible) {
        viewModel.clearSelectionPlans()
    }

    PlanzModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            PlanzBottomSheetContent(
                selectionDay = viewState.selectionDay,
                selectDayPlans = viewState.selectDayPlans,
                onExitClick = { viewModel.setEvent(PlanzContract.PlanzEvent.OnBottomSheetExitClicked) },
                onPlanItemClick = { viewModel.setEvent(PlanzContract.PlanzEvent.OnPlanItemClicked(it)) }
            )
        }
    ) {
        Scaffold(
            bottomBar = {
                if (bottomBarState) {
                    PlanzBottomNavigation(
                        currentDestination = currentDestination,
                        navigateToScreen = { navigationItem ->
                            navigateBottomNavigationScreen(
                                navController = navController,
                                navigationItem = navigationItem,
                                loginState = viewState.loginState,
                                onNoneLoginBottomNavigationClicked = { viewModel.setEvent(PlanzContract.PlanzEvent.OnNoneLoginBottomNavigationClicked) },
                                moveToCreatePlan = intentToCreatePlan
                            )
                        }
                    )
                }
            },
            floatingActionButton = {
                if (bottomBarState) {
                    CreatePlanFAB(modifier = Modifier.padding(top = 12.dp)) {
                        intentToCreatePlan()
                    }
                }
            },
            isFloatingActionButtonDocked = true,
            floatingActionButtonPosition = FabPosition.Center,
        ) { innerPadding ->
            NavHost(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                startDestination = PlanzScreenRoute.HOME.route
            ) {
                composable(route = PlanzScreenRoute.HOME.route) {
                    HomeScreen(
                        navigateToMyPageScreen = {
                            navController.navigate(PlanzScreenRoute.MY_PAGE.route)
                        },
                        navigateToDetailPlanScreen = { planId ->
                            navController.navigate(
                                PlanzScreenRoute.DETAIL_PLAN.route
                                    .plus("/${planId}")
                            )
                        },
                        showBottomSheet = { calendarDay, fixedPlans ->
                            viewModel.setEvent(PlanzContract.PlanzEvent.ShowBottomSheet(calendarDay,
                                fixedPlans))
                        }
                    )
                }

                composable(route = PlanzScreenRoute.MANAGE_PLAN.route) {
                    ManageScreen(
                        intentToCreateScreen = intentToCreatePlan,
                        navigateToFixPlanScreen = { planId ->
                            navController.navigate(PlanzScreenRoute.CONFIRM_PLAN.route.plus("/${planId}"))
                        },
                        navigateToMemberResponseScreen = { planId ->
                            navController.navigate(PlanzScreenRoute.RESPOND_PLAN.route.plus("/${planId}"))
                        },
                        navigateToMonitorPlanScreen = { planId ->
                            navController.navigate(PlanzScreenRoute.MONITOR_PLAN.route.plus("/${planId}"))
                        },
                        navigateToDetailPlanScreen = { planId ->
                            navController.navigate(PlanzScreenRoute.DETAIL_PLAN.route.plus("/${planId}"))
                        }
                    )
                }

                composable(route = PlanzScreenRoute.RESPOND_PLAN.route.plus("/{$KEY_PLAN_ID}"),
                    arguments = listOf(
                        navArgument(KEY_PLAN_ID) { type = NavType.LongType }
                    )) {
                    RespondPlanScreen(
                        navigateToPreviousScreen = { navController.popBackStack() },
                        navigateToSendCompleteScreen = {
                            navController.navigate(PlanzScreenRoute.RESPOND_PLAN_COMPLETE.route) {
                                popUpTo(PlanzScreenRoute.RESPOND_PLAN.route.plus("/{$KEY_PLAN_ID}")) {
                                    inclusive = true
                                }
                            }
                        },
                        navigateToSendRejectedScreen = {
                            navController.navigate(PlanzScreenRoute.RESPOND_PLAN_REJECT.route) {
                                popUpTo(PlanzScreenRoute.RESPOND_PLAN.route.plus("/{$KEY_PLAN_ID}")) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }

                composable(route = PlanzScreenRoute.MONITOR_PLAN.route.plus("/{$KEY_PLAN_ID}"),
                    arguments = listOf(
                        navArgument(KEY_PLAN_ID) { type = NavType.LongType }
                    )) {
                    MonitorPlanScreen(
                        navigateToPreviousScreen = { navController.popBackStack() },
                    )
                }

                composable(route = PlanzScreenRoute.RESPOND_PLAN_COMPLETE.route) {
                    RespondPlanCompleteScreen(
                        navigateToPreviousScreen = { navController.popBackStack() },
                    )
                }

                composable(route = PlanzScreenRoute.RESPOND_PLAN_REJECT.route) {
                    RespondPlanRejectScreen(
                        userName = viewModel.getUserName(),
                        navigateToPreviousScreen = { navController.popBackStack() },
                    )
                }

                composable(route = PlanzScreenRoute.ALREADY_CONFIRM_PLAN.route) {
                    AlreadyConfirmPlanScreen(
                        navigateToPreviousScreen = { navController.popBackStack() },
                    )
                }

                composable(route = PlanzScreenRoute.FULLED_PLAN.route) {
                    FulledPlanScreen(
                        navigateToPreviousScreen = { navController.popBackStack() },
                    )
                }

                composable(route = PlanzScreenRoute.CONFIRM_PLAN.route.plus("/{$KEY_PLAN_ID}"),
                    arguments = listOf(
                        navArgument(KEY_PLAN_ID) { type = NavType.LongType }
                    )) {
                    FixPlanScreen(
                        navigateToPreviousScreen = { navController.popBackStack() },
                        navigateToNextScreen = { planId ->
                            navController.navigate(PlanzScreenRoute.DETAIL_PLAN.route.plus("/${planId}")) {
                                popUpTo(PlanzScreenRoute.CONFIRM_PLAN.route.plus("/{$KEY_PLAN_ID}")) {
                                    inclusive = true
                                }
                            }
                        },
                    )
                }

                composable(route = PlanzScreenRoute.MY_PAGE.route) {
                    MyPageScreen(
                        exitMyPageScreen = { navController.popBackStack() },
                        navigateToPolicyScreen = { navController.navigate(PlanzScreenRoute.PRIVACY_POLICY.route) },
                        navigateToTermsScreen = { navController.navigate(PlanzScreenRoute.TERMS.route) },
                    )
                }

                composable(route = PlanzScreenRoute.PRIVACY_POLICY.route) {
                    PrivacyPolicyScreen(
                        exitPrivacyPolicyScreen = { navController.popBackStack() }
                    )
                }

                composable(route = PlanzScreenRoute.TERMS.route) {
                    TermsScreen(
                        exitTermsScreen = { navController.popBackStack() }
                    )
                }

                composable(
                    route = PlanzScreenRoute.DETAIL_PLAN.route
                        .plus("/{$KEY_PLAN_ID}"),
                    arguments = listOf(
                        navArgument(KEY_PLAN_ID) { type = NavType.LongType }
                    )
                ) {
                    DetailPlanScreen(exitDetailPlanScreen = { navController.popBackStack() })
                }
            }
        }
    }


    bottomBarState = when (currentDestination?.route) {
        PlanzScreenRoute.HOME.route -> true
        PlanzScreenRoute.MANAGE_PLAN.route -> true
        else -> false
    }

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = statusBarColor,
            darkIcons = useDarkIcons
        )

        systemUiController.setNavigationBarColor(
            color = BackgroundColor1
        )
    }

    LaunchedEffect(key1 = uri) {
        uri?.let { link ->
            val planId: Long = link.getQueryParameter(PLAN_ID_KEY_NAME)?.toLong() ?: -1
            if (planId > 0) {
                viewModel.setEvent(PlanzContract.PlanzEvent.GetUserPlanStatus(planId))
            }

            launch {
                if (!link.toString().contains("kakaolink")) {
                    handleDynamicLinks(context, link)
                }
            }
        }
    }

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collect { effect ->
            when(effect) {
                PlanzContract.PlanzSideEffect.MoveToAlreadyConfirmPlan -> {
                    navController.navigate(PlanzScreenRoute.ALREADY_CONFIRM_PLAN.route)
                }
                PlanzContract.PlanzSideEffect.MoveToFulledPlan -> {
                    navController.navigate(PlanzScreenRoute.FULLED_PLAN.route)
                }
                is PlanzContract.PlanzSideEffect.MoveToLogin -> {
                    LoginActivity.startActivity(context, null)
                    context.finish()
                }
                is PlanzContract.PlanzSideEffect.MoveToConfirmPlan -> {
                    navController.navigate(PlanzScreenRoute.CONFIRM_PLAN.route.plus("/${effect.planId}"))
                }
                is PlanzContract.PlanzSideEffect.MoveToMonitorPlan -> {
                    navController.navigate(PlanzScreenRoute.MONITOR_PLAN.route.plus("/${effect.planId}"))
                }
                is PlanzContract.PlanzSideEffect.MoveToRespondPlan -> {
                    navController.navigate(PlanzScreenRoute.RESPOND_PLAN.route.plus("/${effect.planId}"))
                }
                is PlanzContract.PlanzSideEffect.NavigateDetailPlanScreen -> {
                    navController.navigate(
                        PlanzScreenRoute.DETAIL_PLAN.route
                            .plus("/${effect.planId}")
                    )
                    coroutineScope.launch { sheetState.hide() }
                }
                is PlanzContract.PlanzSideEffect.HideBottomSheet -> {
                    coroutineScope.launch { sheetState.hide() }
                }
                is PlanzContract.PlanzSideEffect.ShowBottomSheet -> {
                    coroutineScope.launch { sheetState.show() }
                }
            }
        }
    }
}

@Composable
fun PlanzBottomNavigation(
    currentDestination: NavDestination?,
    navigateToScreen: (BottomNavigationItem) -> Unit,
) {
    BottomNavigation(
        backgroundColor = Color.White,
        modifier = Modifier.height(72.dp),
        elevation = 24.dp
    ) {
        BottomNavigationItem.values().forEach { navigationItem ->
            BottomNavigationItem(
                modifier = Modifier.padding(8.dp),
                icon = {
                    Icon(
                        modifier = Modifier.padding(4.dp),
                        imageVector = ImageVector.vectorResource(id = navigationItem.icon),
                        contentDescription = null,
                        tint = if (navigationItem.route == PlanzScreenRoute.CREATE_PLAN.route) Color.Unspecified
                        else LocalContentColor.current,
                    )
                },
                label = {
                    Text(
                        text = stringResource(navigationItem.title),
                        color = when (navigationItem.route) {
                            PlanzScreenRoute.CREATE_PLAN.route -> MainPurple900
                            currentDestination?.route -> Gray900
                            else -> Gray500
                        },
                        style = TextStyle(
                            fontFamily = Pretendard,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp,
                            lineHeight = 12.sp
                        )
                    )
                },
                selected = currentDestination?.hierarchy?.any { it.route == navigationItem.route } == true,
                onClick = { navigateToScreen(navigationItem) },
                selectedContentColor = Gray900,
                unselectedContentColor = Gray500,
            )
        }
    }
}

@Composable
fun CreatePlanFAB(
    modifier: Modifier = Modifier,
    navigateToManageScreen: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        IconButton(onClick = { navigateToManageScreen() }) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_fab_create),
                contentDescription = null,
                tint = Color.Unspecified,
            )
        }
    }

}

@Composable
fun PlanzBottomSheetContent(
    selectionDay: CalendarDay,
    selectDayPlans: List<Plan.FixedPlan>,
    onExitClick: () -> Unit,
    onPlanItemClick: (Int) -> Unit,
) {
    val month = selectionDay.month + 1
    val day = selectionDay.day

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${month}월 ${day}일 약속",
                style = PlanzTypography.h3
            )
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_exit),
                tint = Color.Unspecified,
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(30.dp))
                    .clickable { onExitClick() },
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        PlanzPlanList(
            dayPlans = selectDayPlans,
            onPlanItemClick = onPlanItemClick
        )
    }
}

@Composable
fun PlanzPlanList(
    dayPlans: List<Plan.FixedPlan>,
    onPlanItemClick: (Int) -> Unit,
) {
    Column(
        modifier = Modifier.padding(bottom = 36.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        for (plan in dayPlans) {
            DayPlanItem(
                id = plan.id,
                date = plan.date,
                category = plan.category.keyword,
                title = plan.title,
                onPlanItemClick = onPlanItemClick
            )
        }
    }
}


fun navigateBottomNavigationScreen(
    navController: NavHostController,
    navigationItem: BottomNavigationItem,
    loginState: PlanzContract.LoginState,
    onNoneLoginBottomNavigationClicked: () -> Unit,
    moveToCreatePlan: () -> Unit,
) {
    if(loginState == PlanzContract.LoginState.NONE) {
        onNoneLoginBottomNavigationClicked()
        return
    }

    if (navigationItem == BottomNavigationItem.CREATE_PLAN) {
        moveToCreatePlan()
    } else {
        navController.navigate(navigationItem.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}

private fun handleDynamicLinks(activity: Activity, uri: Uri) {
    Firebase.dynamicLinks
        .getDynamicLink(activity.intent)
        .addOnSuccessListener(activity) { pendingDynamicLinkData ->

            var deepLink: Uri? = null
            if (pendingDynamicLinkData != null) {
                deepLink = pendingDynamicLinkData.link
            }

            deepLink?.let { link ->
                when (deepLink.lastPathSegment!!) {
                    SchemeType.RESPOND.name -> {

                    }
                }

            }
        }
        .addOnFailureListener(activity) {

        }
}

enum class BottomNavigationItem(
    val route: String,
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
) {
    HOME(
        route = PlanzScreenRoute.HOME.route,
        icon = R.drawable.ic_navigation_home,
        title = R.string.navigation_home_text
    ),
    CREATE_PLAN(
        route = PlanzScreenRoute.CREATE_PLAN.route,
        icon = R.drawable.ic_navigation_blank,
        title = R.string.navigation_create_plan_text
    ),
    MANAGE_PLAN(
        route = PlanzScreenRoute.MANAGE_PLAN.route,
        icon = R.drawable.ic_navigation_manage,
        title = R.string.navigation_manage_plan_text
    )
}

enum class PlanzScreenRoute(val route: String) {
    HOME("home"),
    MANAGE_PLAN("manage-plan"),
    CREATE_PLAN("create-plan"),
    MY_PAGE("my-page"),
    PRIVACY_POLICY("privacy-policy"),
    TERMS("terms"),
    DETAIL_PLAN("detail-plan"),
    RESPOND_PLAN("respond-plan"),
    CONFIRM_PLAN("confirm-plan"),
    MONITOR_PLAN("monitor-plan"),
    RESPOND_PLAN_COMPLETE("respond-plan-complete"),
    RESPOND_PLAN_REJECT("respond-plan-reject"),
    ALREADY_CONFIRM_PLAN("already-confirm-plan"),
    FULLED_PLAN("fulled-plan"),
}

const val KEY_PLAN_ID = "plan-id"
