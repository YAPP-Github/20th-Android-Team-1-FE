package com.yapp.growth.presentation.ui.createPlan

import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yapp.growth.presentation.ui.createPlan.date.DateScreen
import com.yapp.growth.presentation.ui.createPlan.share.ShareScreen
import com.yapp.growth.presentation.ui.createPlan.theme.ThemeScreen
import com.yapp.growth.presentation.ui.createPlan.timerange.TimeRangeScreen
import com.yapp.growth.presentation.ui.createPlan.title.TitleScreen

@Composable
fun CreatePlanScreen(
    navController: NavHostController = rememberNavController(),
    exitCreatePlan: () -> Unit,
    startShareActivity: (Intent) -> Unit,
) {
    Scaffold { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = CreatePlanScreenRoute.SHARE.route
        ) {
            composable(route = CreatePlanScreenRoute.THEME.route) {
                ThemeScreen(
                    exitCreateScreen = exitCreatePlan,
                    navigateToNextScreen = { navController.navigate(CreatePlanScreenRoute.TITLE.route) }
                )
            }

            composable(route = CreatePlanScreenRoute.TITLE.route) {
                TitleScreen(
                    exitCreateScreen = exitCreatePlan,
                    navigateToNextScreen = { navController.navigate(CreatePlanScreenRoute.DATE.route) },
                    navigateToPreviousScreen = { navController.popBackStack() }
                )
            }

            composable(route = CreatePlanScreenRoute.DATE.route) {
                DateScreen(
                    exitCreateScreen = exitCreatePlan,
                    navigateToNextScreen = { navController.navigate(CreatePlanScreenRoute.TIME_RANGE.route) },
                    navigateToPreviousScreen = { navController.popBackStack() }
                )
            }

            composable(route = CreatePlanScreenRoute.TIME_RANGE.route) {
                TimeRangeScreen(
                    exitCreateScreen = exitCreatePlan,
                    navigateToNextScreen = { /* navController.navigate(CreatePlanScreenRoute.NEXT_SCREEN.route) */ },
                    navigateToPreviousScreen = { navController.popBackStack() }
                )
            }

            composable(route = CreatePlanScreenRoute.SHARE.route) {
                ShareScreen(
                    finishCreatePlan = exitCreatePlan,
                    startShareActivity = { shareIntent -> startShareActivity(shareIntent) }
                )
            }
        }
    }
}

enum class CreatePlanScreenRoute(val route: String) {
    THEME("theme"),
    TITLE("title"),
    DATE("date"),
    TIME_RANGE("time-range"),
    SHARE("share-plan"),
}
