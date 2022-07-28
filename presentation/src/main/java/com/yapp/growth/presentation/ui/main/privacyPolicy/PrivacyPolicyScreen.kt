package com.yapp.growth.presentation.ui.main.privacyPolicy

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.component.PlanzBackAppBar
import com.yapp.growth.presentation.component.PlanzWebView

@Composable
fun PrivacyPolicyScreen(
    exitPrivacyPolicyScreen: () -> Unit,
) {
    val url = "https://jalynne.notion.site/7172293b473941ffbc4e1eefdfc85c0e"

    Scaffold(
        topBar = {
            PlanzBackAppBar(
                title = stringResource(id = R.string.privacy_policy_app_bar_text),
                onBackClick = exitPrivacyPolicyScreen,
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            PlanzWebView(url = url)
        }
    }
}