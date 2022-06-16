package com.yapp.growth.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
            Spacer(Modifier.height(18.dp))
            // TODO : 폰트 관련 디자이너님 컨펌 나오면 수정 필요할수도?
            Text(
                text = stringResource(id = R.string.splash_app_name),
                color = Color.White,
                style = MaterialTheme.typography.h1
            )
            Spacer(Modifier.height(9.dp))
            Text(
                text = stringResource(id = R.string.splash_introduce),
                color = Color.White,
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Preview(widthDp = 360, heightDp = 640)
@Composable
private fun SplashPreview() {
    PlanzTheme {
        SplashScreen()
    }
}