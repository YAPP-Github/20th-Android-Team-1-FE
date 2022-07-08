package com.yapp.growth.presentation.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.theme.Gray500
import com.yapp.growth.presentation.theme.Gray900
import com.yapp.growth.presentation.theme.MainPurple900
import com.yapp.growth.presentation.theme.Pretendard
import com.yapp.growth.presentation.ui.main.create.date.DateScreen
import com.yapp.growth.presentation.ui.main.create.theme.ThemeScreen
import com.yapp.growth.presentation.ui.main.create.title.TitleScreen
import com.yapp.growth.presentation.ui.main.home.HomeScreen
import com.yapp.growth.presentation.ui.main.manage.ManageScreen
import com.yapp.growth.presentation.ui.main.sample.SampleScreen
import timber.log.Timber

@Composable
fun PlanzScreen(
    navController: NavHostController = rememberNavController(),
) {
    var bottomBarState by rememberSaveable { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            if (bottomBarState) {
                PlanzBottomNavigation(
                    currentDestination = currentDestination,
                    navigateToScreen = { navigationItem ->
                        navigateBottomNavigationScreen(navController, navigationItem)
                    }
                )
            }
        },
        floatingActionButton = {
            if (bottomBarState) {
                CreatePlanFAB(modifier = Modifier.padding(top = 12.dp)) {
                    navController.navigate(PlanzScreenRoute.CREATE_THEME.route)
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
                HomeScreen()
            }

            composable(route = PlanzScreenRoute.CREATE_THEME.route) {
                ThemeScreen(
                    exitCreateScreen = {
                        navController.navigate(PlanzScreenRoute.HOME.route) {
                            popUpTo(PlanzScreenRoute.CREATE_THEME.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    navigateToNextScreen = { planThemeType ->
                        navController.navigate(
                            PlanzScreenRoute.CREATE_TITLE.route
                                .plus("/$planThemeType")
                        )
                    }
                )
            }

            composable(
                route = PlanzScreenRoute.CREATE_TITLE.route
                    .plus("/{$KEY_PLAN_THEME_TYPE}"),
                arguments = listOf(
                    navArgument(KEY_PLAN_THEME_TYPE) { type = NavType.StringType }
                )
            ) {
                TitleScreen(
                    exitCreateScreen = {
                        navController.navigate(PlanzScreenRoute.HOME.route) {
                            popUpTo(PlanzScreenRoute.CREATE_THEME.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    navigateToNextScreen = { theme, title, place ->
                        navController.navigate(
                            PlanzScreenRoute.CREATE_DATE.route
                                .plus("/$theme")
                                .plus("/$title")
                                .plus("/$place")
                        )
                    },
                    navigateToPreviousScreen = { navController.popBackStack() }
                )
            }

            composable(
                route = PlanzScreenRoute.CREATE_DATE.route
                    .plus("/{$KEY_PLAN_THEME_TYPE}")
                    .plus("/{$KEY_PLAN_TITLE}")
                    .plus("/{$KEY_PLAN_PLACE}"),
                arguments = listOf(
                    navArgument(KEY_PLAN_THEME_TYPE) { type = NavType.StringType },
                    navArgument(KEY_PLAN_TITLE) { type = NavType.StringType },
                    navArgument(KEY_PLAN_PLACE) { type = NavType.StringType },
                )
            ) {
                DateScreen()
            }

            composable(route = PlanzScreenRoute.MANAGE_PLAN.route) {
                ManageScreen(navigateToCreateScreen = { navController.navigate(PlanzScreenRoute.CREATE_THEME.route) },
                    navigateToFixPlanScreen = { planId ->
                        /* TODO: 파티장 - 약속 확정 화면 이동(planId) */
                        Timber.w("약속 확정 화면 이동: $planId")
                    },
                    navigateToMemberResponseScreen = { planId ->
                        /* TODO: 멤버 - 현재까지의 응답 화면 이동(planId) */
                        Timber.w("현재까지의 응답 화면 이동: $planId")
                    },
                    navigateToInvitationScreen = { planId ->
                        /* TODO: 확정된 약속 초대장 화면 이동(planId) */
                        Timber.w("확정된 약속 초대장 화면 이동: $planId")
                    }
                )
            }

            composable(route = PlanzScreenRoute.SAMPLE.route) {
                SampleScreen()
            }
        }
    }

    bottomBarState = when (currentDestination?.route) {
        PlanzScreenRoute.HOME.route -> true
        PlanzScreenRoute.MANAGE_PLAN.route -> true
        else -> false
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
                        tint = if (navigationItem.route == PlanzScreenRoute.CREATE_THEME.route) Color.Unspecified else LocalContentColor.current,
                    )
                },
                label = {
                    Text(
                        text = stringResource(navigationItem.title),
                        color = when (navigationItem.route) {
                            PlanzScreenRoute.CREATE_THEME.route -> MainPurple900
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

fun navigateBottomNavigationScreen(
    navController: NavHostController,
    navigationItem: BottomNavigationItem,
) {
    if (navigationItem == BottomNavigationItem.CREATE_PLAN) {
        navController.navigate(navigationItem.route)
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
        route = PlanzScreenRoute.CREATE_THEME.route,
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
    CREATE_THEME("create-theme"),
    CREATE_TITLE("create-title"),
    CREATE_DATE("create-date"),
    MANAGE_PLAN("manage-plan"),
    SAMPLE("sample"),
    RESPOND_PLAN("respond-plan")
}

const val KEY_PLAN_THEME_TYPE = "plan-theme-type"
const val KEY_PLAN_TITLE = "plan-title"
const val KEY_PLAN_PLACE = "plan-place"

const val BLANK_VALUE = "@"
