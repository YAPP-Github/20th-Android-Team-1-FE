@file:OptIn(ExperimentalMaterialApi::class)

package com.yapp.growth.presentation.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Immutable
class BottomSheetDialogProperties constructor(
    val dismissOnBackPress: Boolean = true,
    val dismissOnClickOutside: Boolean = true,
    val navigationBarColor: Color = Color.Unspecified
) {
    override fun equals(other: Any?): Boolean {
        if (this == other) return true
        if (other !is BottomSheetDialogProperties) return false

        if (dismissOnBackPress != other.dismissOnBackPress) return false
        if (dismissOnClickOutside != other.dismissOnClickOutside) return false
        if (navigationBarColor != other.navigationBarColor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = dismissOnBackPress.hashCode()
        result = 31 * result + dismissOnClickOutside.hashCode()
        result = 31 * result + navigationBarColor.hashCode()
        return result
    }
}

@Composable
fun BottomSheetDialogScreen(
    isBottomSheetCollapsed: Boolean,
    content: @Composable () -> Unit
) {

    val sheetState = rememberBottomSheetState(
        initialValue = if (isBottomSheetCollapsed) BottomSheetValue.Expanded else BottomSheetValue.Collapsed
    )

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )

    val scope = rememberCoroutineScope()
    BottomSheetDialog(sheetState, scaffoldState, scope, content)

}

@Composable
fun BottomSheetDialog(
    sheetState: BottomSheetState,
    scaffoldState: BottomSheetScaffoldState,
    scope: CoroutineScope,
    content: @Composable () -> Unit
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

    }

    BottomSheetScaffold(
        sheetBackgroundColor = Color.Transparent,
        sheetPeekHeight = 0.dp,
        scaffoldState = scaffoldState,
        sheetContent = {
            BottomSheet(sheetState = sheetState, scope = scope, sheetContent = content)
        }
    ) {
        /**
         * TODO
         * 바텀시트로 가려질 화면 Composable
         */
    }
}

@Composable
fun BottomSheet(
    sheetState: BottomSheetState,
    scope: CoroutineScope,
    sheetContent: @Composable () -> Unit
) {
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
                    .background(Color.White),
            ) {
                sheetContent
            }
        }
    }
}
