package com.yapp.growth.presentation.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.theme.MainGradient
import com.yapp.growth.presentation.theme.PlanzTheme

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .background(brush = MainGradient)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                tint = Color.Unspecified,
                imageVector = ImageVector.vectorResource(R.drawable.ic_splash_logo),
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashPreview() {
    PlanzTheme {
        SplashScreen()
    }
}