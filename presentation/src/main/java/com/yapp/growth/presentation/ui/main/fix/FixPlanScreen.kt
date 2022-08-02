@file:OptIn(ExperimentalMaterialApi::class)

package com.yapp.growth.presentation.ui.main.fix

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.growth.base.LoadState
import com.yapp.growth.domain.entity.TimeTable
import com.yapp.growth.domain.entity.User
import com.yapp.growth.presentation.BuildConfig
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.component.*
import com.yapp.growth.presentation.firebase.SchemeType
import com.yapp.growth.presentation.firebase.onDynamicLinkClick
import com.yapp.growth.presentation.theme.Gray700
import com.yapp.growth.presentation.theme.Gray800
import com.yapp.growth.presentation.theme.MainPurple900
import com.yapp.growth.presentation.theme.PlanzTypography
import com.yapp.growth.presentation.ui.main.fix.FixPlanContract.FixPlanEvent
import com.yapp.growth.presentation.ui.main.fix.FixPlanContract.FixPlanSideEffect
import com.yapp.growth.presentation.util.getCurrentBlockDate
import com.yapp.growth.presentation.util.toHour
import com.yapp.growth.presentation.util.toPlanDate
import kotlinx.coroutines.launch

@Composable
fun FixPlanScreen(
    viewModel: FixPlanViewModel = hiltViewModel(),
    navigateToPreviousScreen: () -> Unit,
    navigateToNextScreen: (Long) -> Unit,
) {
    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
    val coroutineScope = rememberCoroutineScope()
    val uiState by viewModel.viewState.collectAsState()
    val context = LocalContext.current

    PlanzBottomSheetScaffoldLayout(
        scaffoldState = scaffoldState,
        sheetContent = {
            if (uiState.bottomSheet == FixPlanContract.FixPlanViewState.BottomSheet.RESPONDENT) {
                PlanzRespondentBottomSheetContent(respondents = uiState.respondents)
            } else {
                FixPlanParticipantBottomSheetContent(
                    timeTable = uiState.timeTable,
                    currentClickTimeIndex = uiState.currentClickTimeIndex,
                    currentClickUserData = uiState.currentClickUserData,
                    onClickSelectPlan = { date -> viewModel.setEvent(FixPlanEvent.OnClickFixButton(date)) }
                )
            }
        }
    ) {

        Scaffold(
            topBar = {
                PlanzUserAndShareAppBar(
                    title = if (uiState.loadState == LoadState.SUCCESS) uiState.timeTable.promisingName else stringResource(
                        R.string.fix_plan_title),
                    onClickBackIcon = { viewModel.setEvent(FixPlanEvent.OnClickBackButton) },
                    onClickUserIcon = { viewModel.setEvent(FixPlanEvent.OnClickUserIcon)},
                    onClickShareIcon = {
                        onDynamicLinkClick(
                            context, SchemeType.RESPOND,
                            uiState.planId.toString(),
                            thumbNailTitle = context.getString(R.string.share_thumbnail_title),
                            thumbNailDescription = context.getString(R.string.share_thumbnail_description),
                            thumbNailImageUrl = BuildConfig.BASE_URL + context.getString(R.string.share_plan_share_feed_template_image_url)
                        )
                    }
                )
            }
        ) { padding ->

            when (uiState.loadState) {
                LoadState.LOADING -> PlanzLoading()
                LoadState.ERROR -> {
                    PlanzError(
                        retryVisible = true,
                        onClickRetry = {
                            viewModel.setEvent(FixPlanEvent.OnClickErrorRetryButton)
                        },
                    )
                }
                LoadState.SUCCESS -> {
                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    ) {
                        val (column, button) = createRefs()

                        Column(modifier = Modifier.constrainAs(column) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(button.top)
                            height = Dimension.fillToConstraints
                        }) {

                            LocationAndAvailableColorBox(timeTable = uiState.timeTable)

                            PlanzPlanDateIndicator(
                                timeTable = uiState.timeTable,
                                onClickPreviousDayButton = { viewModel.setEvent(FixPlanEvent.OnClickPreviousDayButton) },
                                onClickNextDayButton = { viewModel.setEvent(FixPlanEvent.OnClickNextDayButton) }
                            )

                            FixPlanTimeTable(
                                timeTable = uiState.timeTable,
                                onClickTimeTable = { dateIndex, minuteIndex ->
                                    viewModel.setEvent(
                                        FixPlanEvent.OnClickTimeTable(dateIndex, minuteIndex)
                                    )
                                },
                                currentClickTimeIndex = uiState.currentClickTimeIndex
                            )
                        }
                    }
                }
            }
        }

    }

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is FixPlanSideEffect.ShowBottomSheet -> {
                    coroutineScope.launch { sheetState.expand() }
                }

                is FixPlanSideEffect.HideBottomSheet -> {
                    coroutineScope.launch { sheetState.collapse() }
                }
                is FixPlanSideEffect.NavigateToNextScreen -> {
                    navigateToNextScreen(effect.planId)
                }
                FixPlanSideEffect.NavigateToPreviousScreen -> navigateToPreviousScreen()
            }

        }
    }

    BackHandler(enabled = sheetState.isExpanded) {
        coroutineScope.launch { sheetState.collapse() }
    }
}

@Composable
fun FixPlanParticipantBottomSheetContent(
    timeTable: TimeTable,
    currentClickTimeIndex: Pair<Int, Int>,
    currentClickUserData: List<User>,
    onClickSelectPlan: (String) -> Unit,
) {
    if (currentClickTimeIndex.first < 0 || currentClickTimeIndex.second < 0) return

    val day = timeTable.availableDates[currentClickTimeIndex.first].split('T').first()
    val hour = timeTable.minTime.toHour()
    val time = "${day}T${hour}:00"

    val respondUserText = StringBuilder()
    currentClickUserData.forEachIndexed { index, user ->
        when (index) {
            0, 3, 7 -> respondUserText.append(user.userName)
            2, 6 -> respondUserText.append(", ${user.userName.plus("\n")}")
            else -> respondUserText.append(", ${user.userName}")
        }
    }

    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(start = 20.dp, end = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "약속 날짜",
                style = PlanzTypography.subtitle2,
                color = Gray700
            )

            Text(
                text = time.getCurrentBlockDate(currentClickTimeIndex.second).toPlanDate(),
                style = PlanzTypography.subtitle2,
                color = MainPurple900
            )
        }

        if (respondUserText.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "응답자",
                    style = PlanzTypography.subtitle2,
                    color = Gray700
                )

                Text(
                    text = respondUserText.toString(),
                    style = PlanzTypography.caption,
                    color = Gray800,
                    textAlign = TextAlign.End
                )
            }
        }

        PlanzBasicBottomButton(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
            text = stringResource(id = R.string.fix_plan_fix_button_title),
            onClick = {
                onClickSelectPlan(time.getCurrentBlockDate(currentClickTimeIndex.second))
            })
    }
}
