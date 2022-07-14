package com.yapp.growth.presentation.ui.createPlan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.yapp.growth.presentation.theme.PlanzTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreatePlanActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlanzTheme {
                CreatePlanScreen(
                    exitCreatePlan = { exitCreatePlan() }
                )
            }
        }
    }

    private fun exitCreatePlan() {
        finish()
    }
}
