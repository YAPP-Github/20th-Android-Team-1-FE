package com.yapp.growth.presentation.ui.createPlan

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yapp.growth.presentation.ui.createPlan.date.DateScreen
import com.yapp.growth.presentation.ui.createPlan.theme.ThemeScreen
import com.yapp.growth.presentation.ui.createPlan.timerange.TimeRangeScreen
import com.yapp.growth.presentation.ui.createPlan.title.TitleScreen

@Composable
fun CreatePlanScreen(
    navController: NavHostController = rememberNavController(),
    stopCreatePlan: () -> Unit,
) {
    Scaffold { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = CreatePlanScreenRoute.THEME.route
        ) {
            composable(route = CreatePlanScreenRoute.THEME.route) {
                ThemeScreen(
                    exitCreateScreen = stopCreatePlan,
                    navigateToNextScreen = { planThemeType ->
                        navController.navigate(
                            CreatePlanScreenRoute.TITLE.route
                                .plus("/$planThemeType")
                        )
                    }
                )
            }

            composable(
                route = CreatePlanScreenRoute.TITLE.route
                    .plus("/{$KEY_PLAN_THEME_TYPE}"),
                arguments = listOf(
                    navArgument(KEY_PLAN_THEME_TYPE) { type = NavType.StringType }
                )
            ) {
                TitleScreen(
                    exitCreateScreen = stopCreatePlan,
                    navigateToNextScreen = { theme, title, place ->
                        navController.navigate(
                            CreatePlanScreenRoute.DATE.route
                                .plus("/$theme")
                                .plus("/$title")
                                .plus("/$place")
                        )
                    },
                    navigateToPreviousScreen = { navController.popBackStack() }
                )
            }

            composable(
                route = CreatePlanScreenRoute.DATE.route
                    .plus("/{$KEY_PLAN_THEME_TYPE}")
                    .plus("/{$KEY_PLAN_TITLE}")
                    .plus("/{$KEY_PLAN_PLACE}"),
                arguments = listOf(
                    navArgument(KEY_PLAN_THEME_TYPE) { type = NavType.StringType },
                    navArgument(KEY_PLAN_TITLE) { type = NavType.StringType },
                    navArgument(KEY_PLAN_PLACE) { type = NavType.StringType },
                )
            ) {
                DateScreen(
                    exitCreateScreen = stopCreatePlan,
                    navigateToNextScreen = { theme, title, place, dates ->
                        navController.navigate(
                            CreatePlanScreenRoute.TIME_RANGE.route
                                .plus("/$theme")
                                .plus("/$title")
                                .plus("/$place")
                                .plus("/$dates")
                        )
                    },
                    navigateToPreviousScreen = { navController.popBackStack() }
                )
            }

            composable(
                route = CreatePlanScreenRoute.TIME_RANGE.route
                    .plus("/{$KEY_PLAN_THEME_TYPE}")
                    .plus("/{$KEY_PLAN_TITLE}")
                    .plus("/{$KEY_PLAN_PLACE}")
                    .plus("/{$KEY_PLAN_DATES}"),
                arguments = listOf(
                    navArgument(KEY_PLAN_THEME_TYPE) { type = NavType.StringType },
                    navArgument(KEY_PLAN_TITLE) { type = NavType.StringType },
                    navArgument(KEY_PLAN_PLACE) { type = NavType.StringType },
                    navArgument(KEY_PLAN_DATES) { type = NavType.StringType }
                )
            ) {
                TimeRangeScreen(
                    exitCreateScreen = stopCreatePlan,
                    navigateToNextScreen = { theme, title, place, dates, startHour, endHour ->
//                        navController.navigate(
//                            PlanzScreenRoute.NEXT_SCREEN.route
//                                .plus("/$theme")
//                                .plus("/$title")
//                                .plus("/$place")
//                                .plus("/$dates")
//                                .plus("/$startHour")
//                                .plus("/$endHour")
//                        )
                    },
                    navigateToPreviousScreen = { navController.popBackStack() }
                )
            }
        }
    }
}

@Composable
fun getActivity() = LocalContext.current as ComponentActivity

@Composable
inline fun <reified VM : ViewModel> composableActivityViewModel(
    key: String? = null,
    factory: ViewModelProvider.Factory? = null
): VM = viewModel(
    VM::class.java,
    getActivity(),
    key,
    factory
)

enum class CreatePlanScreenRoute(val route: String) {
    THEME("theme"),
    TITLE("title"),
    DATE("date"),
    TIME_RANGE("time-range")
}

const val KEY_PLAN_THEME_TYPE = "plan-theme-type"
const val KEY_PLAN_TITLE = "plan-title"
const val KEY_PLAN_PLACE = "plan-place"
const val KEY_PLAN_DATES = "plan-dates"

const val BLANK_VALUE = "@"
