package com.yapp.growth.data.api

import com.yapp.growth.data.internal.response.*
import com.yapp.growth.data.parameter.TimeRequestParameter
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GrowthApi {

    @GET("/api/promisings/{promisingId}/time-table")
    suspend fun getResponseTimeTable(
        @Path("promisingId") promisingId: String,
    ): PromisingTimeTableResponseImpl

    @GET("/api/promisings/session/{uuid}")
    suspend fun getCreateTimeTable(
        @Path("uuid") uuid: String,
    ): CreateTimeTableResponseImpl

    @POST("/api/promisings/session/{uuid}/time-response")
    suspend fun sendTimeCheckedOfDay(
        @Path("uuid") uuid: String,
        @Body timeRequestParameter: TimeRequestParameter,
    ): TimeRequestResponseImpl

    @GET("/api/promisings/user")
    suspend fun getWaitingPlans(): List<WaitingPlanResponseImpl>

    @GET("/api/promises/user")
    suspend fun getFixedPlans(): List<FixedPlanResponseImpl>
}
