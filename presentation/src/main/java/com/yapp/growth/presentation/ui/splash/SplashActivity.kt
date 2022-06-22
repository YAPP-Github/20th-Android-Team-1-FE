package com.yapp.growth.presentation.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.yapp.growth.presentation.ui.main.MainActivity
import com.yapp.growth.presentation.theme.PlanzTheme
import com.yapp.growth.presentation.ui.login.LoginActivity
import com.yapp.growth.presentation.ui.splash.SplashContract.LoginState
import com.yapp.growth.presentation.ui.splash.SplashContract.SplashViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
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
            delay(SPLASH_TIME)
            viewModel.viewState.collect { state ->
                handleIntent(state)
            }
        }
    }

    private fun setSplashScreen() {
        setContent {
            PlanzTheme {
                SplashScreen()
            }
        }
    }

    private fun handleIntent(state: SplashViewState) = when (state.loginState) {
        LoginState.SUCCESS -> moveToMain()
        LoginState.REQUIRED -> moveToLogin()
        LoginState.NONE -> moveToMain()
    }

    private fun moveToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun moveToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        private const val SPLASH_TIME = 1_000L
    }
}