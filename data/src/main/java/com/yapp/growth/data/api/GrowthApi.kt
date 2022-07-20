package com.yapp.growth.data.api

import com.yapp.growth.data.response.FixedPlanResponseImpl
import com.yapp.growth.data.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface GrowthApi {

    @GET("/users")
    suspend fun getUsers(): List<UserResponse>


    // TODO : /api/promises/date/<date>
    @GET("/api/promises/date/{dateTime}")
    suspend fun getOneDayFixedPlanList(
        @Path("dateTime") dateTime: String
    ): List<FixedPlanResponseImpl>

    @GET("/api/promises/month/{dateTime}")
    suspend fun getMonthlyFixedPlanList(
        @Path("dateTime") dateTime: String
    ): List<FixedPlanResponseImpl>

    @GET("/api/promises/user")
    suspend fun getAllFixedPlanList(): List<FixedPlanResponseImpl>
}