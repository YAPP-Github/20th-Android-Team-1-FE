package com.yapp.growth.presentation.ui.splash

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.lifecycleScope
import com.yapp.growth.presentation.theme.PlanzTheme
import com.yapp.growth.presentation.ui.login.LoginActivity
import com.yapp.growth.presentation.ui.main.MainActivity
import com.yapp.growth.presentation.ui.splash.SplashContract.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val content: View = findViewById(android.R.id.content)
            content.viewTreeObserver.addOnPreDrawListener { false }
        }

        setSplashScreen()

        lifecycleScope.launch {
            viewModel.checkValidLoginToken()
        }
    }

    private fun setSplashScreen() {
        setContent {
            PlanzTheme {
                SplashScreen()
            }

            LaunchedEffect(key1 = viewModel.effect) {
                viewModel.effect.collect { effect ->
                    when (effect) {
                        is SplashSideEffect.MoveToMain -> moveToMain()
                        is SplashSideEffect.LoginFailed -> moveToLogin()
                    }
                }
            }
        }
    }

    private fun moveToMain() {
        MainActivity.startActivity(this)
        finish()
    }

    private fun moveToLogin() {
        LoginActivity.startActivity(this)
        finish()
    }

    companion object {
        private const val SPLASH_TIME = 1_000L
    }
}
