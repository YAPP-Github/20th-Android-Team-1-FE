package com.yapp.growth.presentation.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * How to use?
 * val sheetState = rememberBottomSheetState(
 *      initialValue = BottomSheetValue.Collapsed
 * )
 * val scaffoldState = rememberBottomSheetScaffoldState(
 *      bottomSheetState = sheetState
 * )
 * val scope = rememberCoroutineScope()
 *      BottomSheetScreen(
 *          sheetState = sheetState, scaffoldState = scaffoldState, scope = scope
 *      )
 */

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetScreen(
    sheetState: BottomSheetState, scaffoldState: BottomSheetScaffoldState, scope: CoroutineScope
) {
    BottomSheetScaffold(sheetBackgroundColor = Color.Transparent,
        sheetPeekHeight = 0.dp,
        scaffoldState = scaffoldState,
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.7f))
                    .clickable(sheetState.isExpanded) {
                        scope.launch {
                            sheetState.collapse()
                        }
                    }
            ) {
                Surface(
                    modifier = Modifier
                        .height(200.dp)
                        .align(Alignment.BottomCenter)
                        .clickable(false) { },
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                    ) {
                        Text(
                            modifier = Modifier
                                .clickable(sheetState.isExpanded) {
                                    scope.launch { sheetState.collapse() }
                                },
                            text = "Bottom sheet",
                            fontSize = 40.sp
                        )
                    }
                }
            }
        }) {
        HomeButton(sheetState, scope)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeButton(sheetState: BottomSheetState, scope: CoroutineScope) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = {
            scope.launch {
                if (sheetState.isCollapsed) {
                    sheetState.expand()
                } else {
                    sheetState.collapse()
                }
            }
        }) {
            Text(text = "Bottom sheet state: ${sheetState.currentValue}")
        }

    }
}
