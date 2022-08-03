
package com.yapp.growth.presentation.firebase

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.google.firebase.dynamiclinks.ktx.*
import com.google.firebase.ktx.Firebase
import com.yapp.growth.presentation.BuildConfig
import com.yapp.growth.presentation.R

const val DYNAMIC_LINK_PARAM = "dynamic_link_param"
const val PLAN_ID_KEY_NAME = "planId"

fun getDeepLink(scheme: String, key: String?, id: String?): Uri {
    return if (key.isNullOrEmpty()) {
        Uri.parse(BuildConfig.PLANZ_FIREBASE_DOMAIN)
    } else {
        Uri.parse(BuildConfig.PLANZ_FIREBASE_DOMAIN + "/${scheme}/?${key}=$id")
    }
}

fun onDynamicLinkClick(
    context: Context,
    scheme: SchemeType = SchemeType.RESPOND,
    id: String? = null,
    thumbNailTitle: String = context.getString(R.string.share_thumbnail_title),
    thumbNailDescription: String = context.getString(R.string.share_thumbnail_description),
    thumbNailImageUrl: String = BuildConfig.BASE_URL + context.getString(R.string.share_plan_share_feed_template_image_url),
) {
    Firebase.dynamicLinks.shortLinkAsync {
        link = getDeepLink(scheme.name, scheme.key, id)
        domainUriPrefix = BuildConfig.PLANZ_FIREBASE_PREFIX
        androidParameters(context.packageName) { }
        iosParameters(context.packageName) {
            setFallbackUrl(Uri.parse("https://jalynne.notion.site/3379be16ecc04914bb98f8a57c980a46"))
        }
        socialMetaTagParameters {
            title = thumbNailTitle
            description = thumbNailDescription
            imageUrl = Uri.parse(thumbNailImageUrl)
        }

    }.addOnSuccessListener { (shortLink, _) ->
        runCatching {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, shortLink.toString())
            sendIntent.type = "text/plain"
            context.startActivity(Intent.createChooser(sendIntent, "Share"))
        }
        .onFailure {

        }
    }

}
