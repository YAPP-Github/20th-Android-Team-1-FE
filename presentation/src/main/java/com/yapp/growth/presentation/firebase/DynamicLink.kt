package com.yapp.growth.presentation.firebase

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.google.firebase.dynamiclinks.ktx.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.dynamiclinks.ktx.component1
import com.google.firebase.dynamiclinks.ktx.component2
import timber.log.Timber

fun getDeepLink(scheme: String, key: String?, id: String?): Uri {
    return if (key.isNullOrEmpty()) {
        Uri.parse("https://app.planz.today/")
    } else {
        Uri.parse("https://app.planz.today/${scheme}/?${key}=$id")
    }
}

fun onDynamicLinkClick(
    context: Context,
    scheme: SchemeType,
    id: String? = null
) {
    val dynamicLink = Firebase.dynamicLinks.shortLinkAsync {
        link = getDeepLink(scheme.name, scheme.key, id)
        domainUriPrefix = "https://app.planz.today/link"
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
