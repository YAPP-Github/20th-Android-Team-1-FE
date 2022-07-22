package com.yapp.growth.data.api

import com.yapp.growth.data.response.FixedPlanResponseImpl
import com.yapp.growth.data.response.UserResponse
import com.yapp.growth.data.internal.response.CreateTimeTableResponseImpl
import com.yapp.growth.data.internal.response.PromisingTimeTableResponseImpl
import com.yapp.growth.data.internal.response.TimeRequestResponseImpl
import com.yapp.growth.data.parameter.TimeRequestParameter
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GrowthApi {

    @GET("/api/promisings/{promisingId}/time-table")
    suspend fun getResponseTimeTable(@Path("promisingId") promisingId: String): PromisingTimeTableResponseImpl

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

    @GET("/api/promisings/session/{uuid}")
    suspend fun getCreateTimeTable(@Path("uuid") uuid: String): CreateTimeTableResponseImpl

    @POST("/api/promisings/session/{uuid}/time-response")
    suspend fun sendTimeCheckedOfDay(@Path("uuid") uuid: String, @Body timeRequestParameter: TimeRequestParameter): TimeRequestResponseImpl

}
