package com.yapp.growth.presentation.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.theme.MainGradient
import com.yapp.growth.presentation.theme.PlanzTheme
import com.yapp.growth.presentation.ui.splash.SplashContract.LoginState
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit,
    navigateToMain: () -> Unit
) {
    val viewState by viewModel.viewState.collectAsState()

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
            // TODO : 폰트 관련 디자이너님 컨펌 나오면 수정 필요할수도? (정호)
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

        SplashLoad(viewState.loginState, navigateToLogin, navigateToMain)
    }
}

@Composable
fun SplashLoad(loginState: LoginState, navigateToLogin: () -> Unit, navigateToMain: () -> Unit) {
    // TODO : 싱글 액티비티의 경우 아래 로직으로 진행, 아니라면 타 액티비티 Intent 작업 필요 (정호)
    val splashWaitTimeMillis = 2_000L

    LaunchedEffect(loginState) {
        delay(splashWaitTimeMillis)
        when (loginState) {
            LoginState.SUCCESS -> navigateToMain()
            LoginState.REQUIRED -> navigateToLogin()
            LoginState.NONE -> navigateToLogin()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashPreview() {
    PlanzTheme {
        SplashScreen(navigateToLogin = { /*TODO*/ }) {}
    }
}