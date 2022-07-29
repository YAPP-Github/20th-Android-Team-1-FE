package com.yapp.growth.data.api

import com.yapp.growth.data.internal.response.*
import com.yapp.growth.data.parameter.FixPlanParameter
import com.yapp.growth.data.parameter.TemporaryPlanParameter
import com.yapp.growth.data.parameter.TimeCheckedOfDaysParameter
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GrowthApi {

    // Waiting Plans

    @GET("/api/promisings/{promisingId}/time-table")
    suspend fun getResponseTimeTable(
        @Path("promisingId") promisingId: String,
    ): PromisingTimeTableResponseImpl

    @GET("/api/promisings/session/{uuid}")
    suspend fun getCreateTimeTable(
        @Path("uuid") uuid: String,
    ): CreateTimeTableResponseImpl

    @POST("/api/promisings/session/{uuid}/time-response")
    suspend fun makePlan(
        @Path("uuid") uuid: String,
        @Body timeCheckedOfDaysParameter: TimeCheckedOfDaysParameter,
    ): TimeRequestResponseImpl

    @POST("/api/promisings/{promisingId}/confirmation")
    suspend fun sendFixPlan(
        @Path("promisingId") planId: String,
        @Body fixPlanParameter: FixPlanParameter,
    ): FixedPlanResponseImpl

    @POST("/api/promisings/{promisingId}/time-response")
    suspend fun sendRespondPlan(
        @Path("promisingId") planId: String,
        @Body timeCheckedOfDaysParameter: TimeCheckedOfDaysParameter,
    )

    @POST("/api/promisings/{promisingId}/time-response/rejection")
    suspend fun sendRejectPlan(
        @Path("promisingId") planId: String
    )

    @GET("/api/promisings/user")
    suspend fun getWaitingPlans(): List<WaitingPlanResponseImpl>

    // Fixed Plans

    @GET("/api/promises/{promiseId}")
    suspend fun getFixedPlan(
        @Path("promiseId") planId: Long,
    ): FixedPlanResponseImpl

    @GET("/api/promises/user")
    suspend fun getFixedPlans(): List<FixedPlanResponseImpl>

    @POST("/api/promisings")
    suspend fun createTemporaryPlan(
        @Body temporaryPlanParameter: TemporaryPlanParameter,
    ): TemporaryPlanUuidResponseImpl

    // User

    @POST("/api/users/sign-up")
    suspend fun signUp(): UserResponseImpl

    @GET("/api/users/info")
    suspend fun getUserInfo(): UserResponseImpl

    // Plan Status
    @GET("/api/promisings/{promisingId}/status")
    suspend fun getUserPlanStatus(
        @Path("promisingId") planId: String,
    ): UserPlanStatusResponseImpl

}
