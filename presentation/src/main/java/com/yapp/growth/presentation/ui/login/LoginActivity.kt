package com.yapp.growth.presentation.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.theme.PlanzTheme
import com.yapp.growth.presentation.ui.main.MainActivity
import com.yapp.growth.presentation.ui.login.LoginContract.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PlanzTheme {
                LoginScreen(onClick = { viewModel.setEvent(LoginContract.LoginEvent.OnClickKakaoLoginButton(this@LoginActivity)) })
            }

            LaunchedEffect(key1 = viewModel.effect) {
                viewModel.effect.collect { effect ->
                    when (effect) {
                        is LoginSideEffect.MoveToMain -> {
                            MainActivity.startActivity(this@LoginActivity)
                            finish()
                        }

                        is LoginSideEffect.LoginFailed -> {
                            Toast.makeText(
                                this@LoginActivity,
                                R.string.message_kakao_login_failed,
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                }
            }
        }
    }

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

}
