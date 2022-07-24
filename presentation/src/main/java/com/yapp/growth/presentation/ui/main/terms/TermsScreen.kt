package com.yapp.growth.presentation.ui.main.terms

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
fun TermsScreen(
    exitTermsScreen: () -> Unit,
) {

    val url = "https://jalynne.notion.site/3379be16ecc04914bb98f8a57c980a46"

    Scaffold(
        topBar = {
            PlanzBackAppBar(
                title = stringResource(id = R.string.terms_app_bar_text),
                onBackClick = exitTermsScreen,
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