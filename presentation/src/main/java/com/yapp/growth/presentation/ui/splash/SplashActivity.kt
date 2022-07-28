package com.yapp.growth.presentation.ui.splash

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.lifecycleScope
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import com.yapp.growth.presentation.firebase.SchemeType
import com.yapp.growth.presentation.theme.PlanzTheme
import com.yapp.growth.presentation.ui.login.LoginActivity
import com.yapp.growth.presentation.ui.main.MainActivity
import com.yapp.growth.presentation.ui.splash.SplashContract.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

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

        handleDynamicLinks()
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

    private fun handleDynamicLinks() {
        Firebase.dynamicLinks
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData ->
                // Get deep link from result (may be null if no link is found)
                var deepLink: Uri? = null
                if (pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.link
                }

                // Handle the deep link. For example, open the linked
                // content, or apply promotional credit to the user's
                // account.
                deepLink?.let {
                    val segment: String = deepLink.lastPathSegment!!
                    when(segment) {
                        SchemeType.RESPOND.name.lowercase() -> {
                            
                        }
                    }

                }
            }
            .addOnFailureListener(this) {
                Timber.tag("FAIL HANDLE LINKS").e(it)
            }
    }
}
