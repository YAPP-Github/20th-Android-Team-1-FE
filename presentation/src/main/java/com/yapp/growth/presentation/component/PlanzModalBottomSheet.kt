package com.yapp.growth.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetDefaults
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlanzModalBottomSheetLayout(
    sheetState: ModalBottomSheetState,
    sheetContent: @Composable () -> Unit,
    scrimColor: Color = ModalBottomSheetDefaults.scrimColor,
    content: @Composable () -> Unit,
) {
    ModalBottomSheetLayout(
        sheetBackgroundColor = Color.Transparent,
        sheetState = sheetState,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                PlanzModalBottomSheet {
                    sheetContent()
                }
            }
        },
        scrimColor = scrimColor,
        content = content,
    )
}

@Composable
fun PlanzModalBottomSheet(
    sheetContent: @Composable () -> Unit,
) {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .background(color = Color.White)
    )

    sheetContent()

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
            .background(color = Color.White)
    )
}
