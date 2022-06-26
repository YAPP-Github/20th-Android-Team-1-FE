package com.yapp.growth.presentation.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.theme.PlanzTheme
import com.yapp.growth.presentation.ui.login.LoginContract.LoginState
import com.yapp.growth.presentation.ui.login.LoginContract.LoginViewState
import com.yapp.growth.presentation.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.viewState.collect { state ->
                handleEvent(state)
            }
        }

        setContent {
            PlanzTheme {
                LoginScreen(onClick = { viewModel.requestLogin(this@LoginActivity) })
            }
        }
    }

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

    private fun handleEvent(state: LoginViewState) = when (state.loginState) {
        LoginState.SUCCESS -> {
            MainActivity.startActivity(this)
            finish()
        }

        LoginState.REQUIRED -> {
            Toast.makeText(this, R.string.message_kakao_login_failed, Toast.LENGTH_SHORT).show()
        }

        else -> {}
    }
}
