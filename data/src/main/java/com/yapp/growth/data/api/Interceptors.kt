package com.yapp.growth.data.api

import com.kakao.beauty.data.api.internal.response.ErrorResponseImpl
import com.kakao.beauty.data.api.response.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

internal class Interceptors @Inject constructor(
    private val networkSettings: NetworkSettings
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            return chain.proceed(chain.request().newBuilder().apply {

            }.build())
        } catch (e: Throwable) {
            /**
             * Non-IOException subtypes thrown from interceptor never notify Callback
             * See https://github.com/square/okhttp/issues/5151
             */
            if (e is IOException) {
                throw e
            } else {
                throw IOException(e)
            }
        }
    }

}