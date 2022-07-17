package com.yapp.growth.data.api

import com.yapp.growth.data.internal.response.PromisingTimeTableResponseImpl
import com.yapp.growth.data.response.UserResponse222
import retrofit2.http.GET
import retrofit2.http.Path

interface GrowthApi {

    @GET("/users")
    suspend fun getUsers(): List<UserResponse222>

    @GET("/api/promisings/{promisingId}/time-table")
    suspend fun getResponseTimeTable(@Path("promisingId") promisingId: String): PromisingTimeTableResponseImpl

}
