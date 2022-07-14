package com.yapp.growth.presentation.ui.createPlan.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.component.PlanzCreateStepTitle
import com.yapp.growth.presentation.component.PlanzMainButton
import com.yapp.growth.presentation.model.PlanThemeType
import com.yapp.growth.presentation.theme.*
import com.yapp.growth.presentation.ui.createPlan.CreatePlanContract
import com.yapp.growth.presentation.ui.createPlan.CreatePlanContract.CreatePlanEvent.DecideTheme
import com.yapp.growth.presentation.ui.createPlan.CreatePlanViewModel
import com.yapp.growth.presentation.util.composableActivityViewModel
import kotlinx.coroutines.flow.collect

@Composable
fun ThemeScreen(
    sharedViewModel: CreatePlanViewModel = composableActivityViewModel(),
    viewModel: ThemeViewModel = hiltViewModel(),
    exitCreateScreen: () -> Unit,
    navigateToNextScreen: () -> Unit,
) {
    val uiState by viewModel.viewState.collectAsState()

    Scaffold(
        topBar = {
            PlanzCreateStepTitle(
                currentStep = 1,
                title = stringResource(id = R.string.create_plan_theme_title_text),
                onExitClick = { viewModel.setEvent(ThemeContract.ThemeEvent.OnClickExitButton) }
            )
        }
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {
            Column(
                modifier = Modifier.padding(top = 44.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                PlanThemeType.values().forEach {
                    ThemeChoiceButton(
                        isChosen = uiState.chosenTheme == it,
                        text = stringResource(id = it.themeStringResId),
                        onClick = {
                            viewModel.setEvent(ThemeContract.ThemeEvent.ChoosePlanTheme(it))
                        }
                    )
                }
            }

            PlanzMainButton(
                text = stringResource(id = R.string.create_plan_next_button_text),
                enabled = uiState.chosenTheme != null,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 32.dp),
                onClick = { viewModel.setEvent(ThemeContract.ThemeEvent.OnClickNextButton) }
            )
        }
    }

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ThemeContract.ThemeSideEffect.NavigateToNextScreen -> {
                    uiState.chosenTheme?.let {
                        sharedViewModel.setEvent(DecideTheme(it))
                        navigateToNextScreen()
                    }
                }
                is ThemeContract.ThemeSideEffect.ExitCreateScreen -> {
                    exitCreateScreen()
                }
            }
        }
    }
}

@Composable
fun ThemeChoiceButton(
    isChosen: Boolean = false,
    text: String,
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .padding(horizontal = 20.dp),
        elevation = null,
        shape = RoundedCornerShape(10.dp),
        border = if (isChosen) BorderStroke((0.7).dp, MainPurple900) else null,
        colors = if (isChosen) ButtonDefaults.buttonColors(backgroundColor = MainPurple300)
        else ButtonDefaults.buttonColors(backgroundColor = Gray100),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = PlanzTypography.body1,
                color = if (isChosen) MainPurple900 else Gray500,
            )

            Icon(
                imageVector = if (isChosen) ImageVector.vectorResource(id = R.drawable.ic_check_circle_fill)
                else ImageVector.vectorResource(id = R.drawable.ic_check_circle_outline),
                contentDescription = stringResource(id = R.string.icon_check_content_description),
                tint = Color.Unspecified,
            )
        }
    }
}

@Preview
@Composable
fun ThemeChoiceButtonPreview() {
    ThemeChoiceButton(
        text = "test",
        onClick = {}
    )
}

@Preview
@Composable
fun ThemeChoiceButtonActivePreview() {
    ThemeChoiceButton(
        isChosen = true,
        text = "test",
        onClick = {}
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
    ThemeScreen(
        exitCreateScreen = {},
        navigateToNextScreen = {}
    )
}
