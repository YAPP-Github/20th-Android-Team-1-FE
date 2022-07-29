package com.yapp.growth.data.source

import com.yapp.growth.data.parameter.TemporaryPlanParameter
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.*

interface PlanzDataSource {

    suspend fun getCreateTimeTable(uuid: String): NetworkResult<CreateTimeTable>
    suspend fun makePlan(uuid: String, timeCheckedOfDays: List<TimeCheckedOfDay>): NetworkResult<Long>

    suspend fun getRespondUsers(promisingId: Long): NetworkResult<TimeTable>
    suspend fun sendRespondPlan(promisingId: Long, timeCheckedOfDays: List<TimeCheckedOfDay>): NetworkResult<Unit>
    suspend fun sendConfirmPlan(promisingId: Long, date: String): NetworkResult<Any>

    suspend fun getWaitingPlans(): NetworkResult<List<Plan.WaitingPlan>>
    suspend fun getFixedPlans(): NetworkResult<List<Plan.FixedPlan>>
    suspend fun getFixedPlan(planId: Long): NetworkResult<Plan.FixedPlan>

    suspend fun createTemporaryPlan(
        temporaryPlanParameter: TemporaryPlanParameter
    ): NetworkResult<TemporaryPlanUuid>

    suspend fun signUp(): NetworkResult<User>
    suspend fun getUserInfo(): NetworkResult<User>
    suspend fun deleteUserInfo(): NetworkResult<Unit>
}
