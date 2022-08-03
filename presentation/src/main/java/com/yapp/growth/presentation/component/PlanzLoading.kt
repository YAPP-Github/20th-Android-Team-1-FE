package com.yapp.growth.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.yapp.growth.presentation.theme.MainPurple900

@Composable
fun PlanzLoading(
    modifier: Modifier = Modifier.fillMaxSize(),
) {
    Box(modifier = modifier) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = MainPurple900
        )
    }
}