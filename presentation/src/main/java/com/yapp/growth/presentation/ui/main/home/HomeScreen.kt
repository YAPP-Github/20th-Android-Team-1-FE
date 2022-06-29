package com.yapp.growth.presentation.ui.main.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.growth.presentation.theme.PlanzTheme
import com.yapp.growth.presentation.ui.main.home.HomeContract.HomeSideEffect
import kotlinx.coroutines.flow.collect


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {

    val viewState by viewModel.viewState.collectAsState()

    // TODO : 이벤트에 따라 사이드 이펙트 적용되게 설정 (정호)
    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeSideEffect.NavigateToInfoScreen -> {
                    // onUserIconClick()
                }
                is HomeSideEffect.NavigateDetailPlanScreen -> {
                    // onDetailPlanClick()
                }
                is HomeSideEffect.OpenBottomSheet -> {
                    // sheet.show()
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun PreviewHomeScreen() {
    PlanzTheme {
        HomeScreen()
    }
}
