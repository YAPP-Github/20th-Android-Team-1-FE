@file:OptIn(ExperimentalMaterialApi::class)

package com.yapp.growth.presentation.component

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PlanzBottomSheetScaffoldLayout(
    sheetContent: @Composable () -> Unit,
    scaffoldState: BottomSheetScaffoldState,
    content: @Composable () -> Unit,
    onBackPressed: (() -> Unit)? = null,
) {
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetBackgroundColor = Color.Transparent,
        sheetPeekHeight = 0.dp,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                PlanzBottomSheetScaffold(
                    sheetContent = sheetContent,
                    onBackPressed = onBackPressed
                )
            }
        }
    ) { padding ->
        content()
    }

}

@Composable
fun PlanzBottomSheetScaffold(
    sheetContent: @Composable () -> Unit,
    onBackPressed: (() -> Unit)? = null,
) {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
            .background(color = Color.White)
    )

    sheetContent()

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(28.dp)
            .background(color = Color.White)
    )

    BackHandler(onBackPressed != null) {
        onBackPressed?.invoke()
    }
}
