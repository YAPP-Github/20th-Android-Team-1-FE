package com.yapp.growth.data.api

import com.yapp.growth.data.internal.response.FixedPlanResponseImpl
import com.yapp.growth.data.internal.response.CreateTimeTableResponseImpl
import com.yapp.growth.data.internal.response.PromisingTimeTableResponseImpl
import com.yapp.growth.data.internal.response.TimeRequestResponseImpl
import com.yapp.growth.data.parameter.ConfirmPlanParameter
import com.yapp.growth.data.parameter.TimeCheckedOfDaysParameter
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GrowthApi {

    @GET("/api/promisings/{promisingId}/time-table")
    suspend fun getResponseTimeTable(@Path("promisingId") promisingId: String): PromisingTimeTableResponseImpl

    @GET("/api/promises/user")
    suspend fun getAllFixedPlanList(): List<FixedPlanResponseImpl>

    @GET("/api/promises/{promiseId}")
    suspend fun getFixedPlan(@Path("promiseId") pId: Long): FixedPlanResponseImpl

    @GET("/api/promisings/session/{uuid}")
    suspend fun getCreateTimeTable(@Path("uuid") uuid: String): CreateTimeTableResponseImpl

    @POST("/api/promisings/session/{uuid}/time-response")
    suspend fun sendTimeCheckedOfDay(@Path("uuid") uuid: String, @Body timeCheckedOfDaysParameter: TimeCheckedOfDaysParameter): TimeRequestResponseImpl

    @POST("/api/promisings/{promisingId}/confirmation")
    suspend fun sendConfirmPlan(@Path("promisingId") promisingId: String, @Body confirmPlanParameter: ConfirmPlanParameter): Any

    @POST("/api/promisings/{promisingId}/time-response")
    suspend fun sendRespondPlan(@Path("promisingId") promisingId: String, @Body timeCheckedOfDaysParameter: TimeCheckedOfDaysParameter): Unit

}
