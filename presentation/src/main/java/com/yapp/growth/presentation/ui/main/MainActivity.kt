package com.yapp.growth.presentation.ui.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import com.yapp.growth.presentation.firebase.DYNAMIC_LINK_PARAM
import com.yapp.growth.presentation.firebase.SchemeType
import com.yapp.growth.presentation.theme.PlanzTheme
import com.yapp.growth.presentation.ui.createPlan.CreatePlanActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val uri = intent.extras?.getParcelable<Uri>(DYNAMIC_LINK_PARAM)
            handleDynamicLinks()

            PlanzTheme {
                PlanzScreen(
                    intentToCreatePlan = { intentToCreatePlan() },
                    uri = uri,
                )
            }
        }
    }

    private fun intentToCreatePlan() {
        val intent = Intent(this, CreatePlanActivity::class.java)
        startActivity(intent)
    }

    companion object {

        fun startActivity(context: Context, uri: Uri?) {
            val intent = Intent(context, MainActivity::class.java)
            if (uri != null) {
                intent.putExtra(DYNAMIC_LINK_PARAM, uri)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP + Intent.FLAG_ACTIVITY_NEW_TASK
            }
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
