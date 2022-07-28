
package com.yapp.growth.presentation.firebase

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.google.firebase.dynamiclinks.ktx.androidParameters
import com.google.firebase.dynamiclinks.ktx.component1
import com.google.firebase.dynamiclinks.ktx.component2
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.dynamiclinks.ktx.shortLinkAsync
import com.google.firebase.ktx.Firebase
import com.yapp.growth.presentation.BuildConfig
import timber.log.Timber

fun getDeepLink(scheme: String, key: String?, id: String?): Uri {
    return if (key.isNullOrEmpty()) {
        Uri.parse(BuildConfig.PLANZ_DOMAIN)
    } else {
        Uri.parse(BuildConfig.PLANZ_DOMAIN + "/${scheme}/?${key}=$id")
    }
}

fun onDynamicLinkClick(
    context: Context,
    scheme: SchemeType,
    id: String? = null
) {
    val dynamicLink = Firebase.dynamicLinks.shortLinkAsync {
        link = getDeepLink(scheme.name, scheme.key, id)
        domainUriPrefix = BuildConfig.DOMAIN_URI_PREFIX
        androidParameters(context.packageName) { }

    }.addOnSuccessListener { (shortLink, _) ->
        runCatching {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, shortLink.toString())
            sendIntent.type = "text/plain"
            context.startActivity(Intent.createChooser(sendIntent, "Share"))
        }
        .onFailure {
            Timber.tag("SHORTLINK").e(it)
        }
    }

}
