package com.yapp.growth.presentation.ui.login

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.lifecycleScope
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.firebase.DYNAMIC_LINK_PARAM
import com.yapp.growth.presentation.firebase.SchemeType
import com.yapp.growth.presentation.theme.PlanzTheme
import com.yapp.growth.presentation.ui.main.MainActivity
import com.yapp.growth.presentation.ui.login.LoginContract.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private val viewModel: LoginViewModel by viewModels()
    private var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            uri = intent.extras?.getParcelable<Uri>(DYNAMIC_LINK_PARAM)
            handleDynamicLinks()
        }

        setContent {
            PlanzTheme {
                LoginScreen(
                    onClickKakaoLogin = { viewModel.setEvent(LoginEvent.OnClickKakaoLoginButton(this@LoginActivity)) },
                    onClickNonLogin = { viewModel.setEvent(LoginEvent.OnClickNonLoginButton) }
                )
            }

            LaunchedEffect(key1 = viewModel.effect) {
                viewModel.effect.collect { effect ->
                    when (effect) {
                        is LoginSideEffect.MoveToMain -> {
                            MainActivity.startActivity(this@LoginActivity, uri)
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

        fun startActivity(context: Context, uri: Uri?) {
            val intent = Intent(context, LoginActivity::class.java)
            if (uri != null) intent.putExtra(DYNAMIC_LINK_PARAM, uri)
            context.startActivity(intent)
        }
    }

    private fun handleDynamicLinks() {
        Firebase.dynamicLinks
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData ->

                var deepLink: Uri? = null
                if (pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.link
                }

                deepLink?.let { link ->
                    when (deepLink.lastPathSegment!!) {
                        SchemeType.RESPOND.name -> {

                        }
                    }
                }
            }
    }

}
