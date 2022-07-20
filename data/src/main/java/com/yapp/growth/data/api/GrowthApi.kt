package com.yapp.growth.data.api

import com.yapp.growth.data.internal.response.CreateTimeTableResponseImpl
import com.yapp.growth.data.internal.response.PromisingTimeTableResponseImpl
import retrofit2.http.GET
import retrofit2.http.Path

interface GrowthApi {

    @GET("/api/promisings/{promisingId}/time-table")
    suspend fun getResponseTimeTable(@Path("promisingId") promisingId: String): PromisingTimeTableResponseImpl

    @GET("/api/promisings/session/{uuid}")
    suspend fun getCreateTimeTable(@Path("uuid") uuid: String): CreateTimeTableResponseImpl

}
