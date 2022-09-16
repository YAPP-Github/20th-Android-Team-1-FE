package com.yapp.growth.presentation.ui.createPlan.date

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.component.PlanzButtonWithBack
import com.yapp.growth.presentation.component.PlanzCalendar
import com.yapp.growth.presentation.component.PlanzCalendarSelectMode
import com.yapp.growth.presentation.component.PlanzCreateStepTitleWithDescription
import com.yapp.growth.presentation.component.PlanzErrorSnackBar
import com.yapp.growth.presentation.theme.PlanzTypography
import com.yapp.growth.presentation.ui.createPlan.CreatePlanContract.CreatePlanEvent.DecideDates
import com.yapp.growth.presentation.ui.createPlan.CreatePlanViewModel
import com.yapp.growth.presentation.ui.createPlan.date.DateContract.DateEvent
import com.yapp.growth.presentation.ui.createPlan.date.DateContract.DateSideEffect
import com.yapp.growth.presentation.ui.main.home.CalendarDecorator
import com.yapp.growth.presentation.util.composableActivityViewModel
import com.yapp.growth.presentation.util.toParseFormatDate
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun DateScreen(
    sharedViewModel: CreatePlanViewModel = composableActivityViewModel(),
    viewModel: DateViewModel = hiltViewModel(),
    exitCreateScreen: () -> Unit,
    navigateToNextScreen: () -> Unit,
    navigateToPreviousScreen: () -> Unit,
) {
    val context = LocalContext.current
    val viewState by viewModel.viewState.collectAsState()
    val currentDate by viewModel.currentDate.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            PlanzCreateStepTitleWithDescription(
                currentStep = 3,
                title = stringResource(id = R.string.create_plan_date_title_text),
                description = stringResource(id = R.string.create_plan_date_max_day_text),
                onExitClick = { viewModel.setEvent(DateEvent.OnClickExitButton) }
            )
        },
        snackbarHost = { snackbarHostState ->
            SnackbarHost(hostState = snackbarHostState) { snackbarData ->
                PlanzErrorSnackBar(
                    message = snackbarData.message,
                    bottomPadding = 100
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column {
                Spacer(modifier = Modifier.height(36.dp))
                DateCalendar(
                    currentDate = currentDate,
                    context = context,
                    onDateSelect = { viewModel.setEvent(DateEvent.OnDateSelected(it)) },
                    onMonthlyPreviousClick = { viewModel.setEvent(DateEvent.OnMonthlyPreviousClicked) },
                    onMonthlyNextClick = { viewModel.setEvent(DateEvent.OnMonthlyNextClicked) },
                    onPreviousClick = { viewModel.setEvent(DateEvent.OnPreviousDateClicked) },
                    onOverDateSelect = { viewModel.setEvent(DateEvent.OnDateOverSelected) },
                    selectedDates = viewState.dates
                )
            }

            PlanzButtonWithBack(
                text = stringResource(id = R.string.create_plan_next_button_text),
                enabled = viewState.dates.isNotEmpty(),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 32.dp),
                onClick = { viewModel.setEvent(DateEvent.OnClickNextButton) },
                onBackClick = { viewModel.setEvent(DateEvent.OnClickBackButton) }
            )
        }
    }

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is DateSideEffect.ExitCreateScreen -> { exitCreateScreen() }
                is DateSideEffect.NavigateToNextScreen -> {
                    sharedViewModel.setEvent(
                        DecideDates(viewState.dates.map { it.toParseFormatDate() })
                    )
                    navigateToNextScreen()
                }
                is DateSideEffect.NavigateToPreviousScreen -> { navigateToPreviousScreen() }
                is DateSideEffect.ShowSnackBar -> {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                        scaffoldState.snackbarHostState.showSnackbar(effect.msg)
                    }
                }
            }
        }
    }
}

@Composable
fun DateCalendar(
    currentDate: CalendarDay,
    context: Context,
    selectedDates: List<CalendarDay>,
    onDateSelect: (List<CalendarDay>) -> Unit,
    onMonthlyPreviousClick: () -> Unit,
    onMonthlyNextClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onOverDateSelect: () -> Unit,
) {
    val year: Int = currentDate.year
    val month: Int = currentDate.month + 1

    Column(
        modifier = Modifier.padding(horizontal = 32.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false),
                    onClick = { onMonthlyPreviousClick() }
                ),
                tint = Color.Unspecified,
                imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_box_left_24),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "${year}년 ${String.format("%02d", month)}월",
                style = PlanzTypography.h3,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false),
                    onClick = { onMonthlyNextClick() }
                ),
                tint = Color.Unspecified,
                imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_box_right_24),
                contentDescription = null,
            )
        }
        PlanzCalendar(
            currentDate = currentDate,
            selectedDates = selectedDates,
            selectMode = PlanzCalendarSelectMode.MULTIPLE,
            onDateSelectedListener = { widget, date, _ ->
                if (date.isBefore(CalendarDay.today())) {
                    widget.setDateSelected(date, false)
                    widget.addDecorator(CalendarDecorator.TodayLessDecorator(context))
                    onPreviousClick()
                } else {
                    if (widget.selectedDates.size > 12) {
                        widget.setDateSelected(date, false)
                        onOverDateSelect()
                    }
                    widget.addDecorator(
                        CalendarDecorator.SelectedDatesDecorator(context, widget.selectedDates)
                    )
                }

                onDateSelect(widget.selectedDates)
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DateScreenPreview() {
    DateScreen(
        exitCreateScreen = {},
        navigateToNextScreen = { },
        navigateToPreviousScreen = {}
    )
}
