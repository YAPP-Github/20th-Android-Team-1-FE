package com.yapp.growth.presentation.ui.createPlan

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yapp.growth.presentation.ui.createPlan.date.DateScreen
import com.yapp.growth.presentation.ui.createPlan.theme.ThemeScreen
import com.yapp.growth.presentation.ui.createPlan.timerange.TimeRangeScreen
import com.yapp.growth.presentation.ui.createPlan.title.TitleScreen
import com.yapp.growth.presentation.ui.main.manage.confirm.ConfirmPlanScreen
import com.yapp.growth.presentation.ui.main.manage.respond.result.RespondPlanRejectScreen
import com.yapp.growth.presentation.ui.main.manage.respond.result.RespondPlanCompleteScreen
import com.yapp.growth.presentation.ui.main.manage.respond.RespondPlanScreen

@Composable
fun CreatePlanScreen(
    navController: NavHostController = rememberNavController(),
    exitCreatePlan: () -> Unit,
) {
    Scaffold { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = CreatePlanScreenRoute.THEME.route
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

            composable(route = CreatePlanScreenRoute.RESPOND_PLAN.route) {
                RespondPlanScreen(
                    navigateToPreviousScreen = { navController.popBackStack() }
                )
            }

            composable(route = CreatePlanScreenRoute.CONFIRM_PLAN.route) {
                ConfirmPlanScreen(
                    navigateToPreviousScreen = { navController.popBackStack() }
                )
            }

            composable(route = CreatePlanScreenRoute.RESPOND_PLAN_COMPLETE.route) {
                RespondPlanCompleteScreen(
                    navigateToPreviousScreen = { navController.popBackStack() },
                    onClickCheckButton = {  }
                )
            }

            composable(route = CreatePlanScreenRoute.RESPOND_PLAN_REJECT.route) {
                RespondPlanRejectScreen(
                    navigateToPreviousScreen = { navController.popBackStack() },
                    onClickCheckButton = {  }
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
    RESPOND_PLAN("respond-plan"),
    CONFIRM_PLAN("confirm-plan"),
    RESPOND_PLAN_COMPLETE("respond-plan-complete"),
    RESPOND_PLAN_REJECT("respond-plan-reject"),
}
