package com.yapp.growth.presentation.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.yapp.growth.presentation.theme.PlanzTheme
import com.yapp.growth.presentation.ui.createPlan.CreatePlanActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlanzTheme {
                PlanzScreen(
                    intentToCreatePlan = { intentToCreatePlan() }
                )
            }
        }
    }

    private fun intentToCreatePlan() {
        val intent = Intent(this, CreatePlanActivity::class.java)
        startActivity(intent)
    }

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}
