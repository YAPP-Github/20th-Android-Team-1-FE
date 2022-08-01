package com.yapp.growth.data.source

import com.yapp.growth.data.parameter.TemporaryPlanParameter
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.*

interface PlanzDataSource {

    suspend fun getCreateTimeTable(uuid: String): NetworkResult<CreateTimeTable>
    suspend fun makePlan(uuid: String, timeCheckedOfDays: List<TimeCheckedOfDay>): NetworkResult<Long>

    suspend fun getRespondUsers(planId: Long): NetworkResult<TimeTable>
    suspend fun sendRespondPlan(planId: Long, timeCheckedOfDays: List<TimeCheckedOfDay>): NetworkResult<Unit>

    suspend fun sendRejectPlan(planId: Long): NetworkResult<Unit>
    suspend fun sendFixPlan(planId: Long, date: String): NetworkResult<Plan.FixedPlan>

    suspend fun getWaitingPlans(): NetworkResult<List<Plan.WaitingPlan>>
    suspend fun getFixedPlans(): NetworkResult<List<Plan.FixedPlan>>
    suspend fun getFixedPlan(planId: Long): NetworkResult<Plan.FixedPlan>
    suspend fun getDayFixedPlans(dateTime: String): NetworkResult<List<Plan.FixedPlan>>
    suspend fun getMonthlyFixedPlans(dateTime: String): NetworkResult<List<Plan.FixedPlan>>

    suspend fun getPlanCategories(): NetworkResult<List<Category>>
    suspend fun getSampleTitle(categoryId: Int): NetworkResult<String>

    suspend fun createTemporaryPlan(
        temporaryPlanParameter: TemporaryPlanParameter
    ): NetworkResult<TemporaryPlanUuid>

    suspend fun signUp(): NetworkResult<User>
    suspend fun getUserInfo(): NetworkResult<User>
    suspend fun getUserPlanStatus(planId: Long): NetworkResult<UserPlanStatus>
    suspend fun deleteUserInfo(): NetworkResult<Unit>
}
