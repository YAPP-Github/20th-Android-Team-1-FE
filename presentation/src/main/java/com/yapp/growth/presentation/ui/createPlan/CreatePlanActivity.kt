package com.yapp.growth.presentation.ui.createPlan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.yapp.growth.presentation.theme.BackgroundColor1
import com.yapp.growth.presentation.theme.PlanzTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreatePlanActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PlanzTheme {
                CreatePlanScreen(
                    exitCreatePlan = { exitCreatePlan() },
                    startShareActivity = { intent -> startActivity(intent) }
                )
            }
        }
    }

    private fun exitCreatePlan() {
        finish()
    }
}
