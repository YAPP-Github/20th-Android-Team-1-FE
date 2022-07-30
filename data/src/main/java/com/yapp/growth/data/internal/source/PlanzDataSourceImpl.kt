package com.yapp.growth.data.internal.source

import com.yapp.growth.data.api.GrowthApi
import com.yapp.growth.data.api.handleApi
import com.yapp.growth.data.mapper.*
import com.yapp.growth.data.parameter.FixPlanParameter
import com.yapp.growth.data.parameter.TemporaryPlanParameter
import com.yapp.growth.data.parameter.TimeCheckedOfDayParameter
import com.yapp.growth.data.parameter.TimeCheckedOfDaysParameter
import com.yapp.growth.data.source.PlanzDataSource
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.*
import javax.inject.Inject

internal class PlanzDataSourceImpl @Inject constructor(
    private val retrofitApi: GrowthApi,
) : PlanzDataSource {

    override suspend fun getCreateTimeTable(uuid: String): NetworkResult<CreateTimeTable> =
        handleApi {
            retrofitApi.getCreateTimeTable(uuid).toCreateTimeTable()
        }

    override suspend fun makePlan(
        uuid: String,
        timeCheckedOfDays: List<TimeCheckedOfDay>,
    ): NetworkResult<Long> =
        handleApi {
            val parameter = TimeCheckedOfDaysParameter(
                unit = 0.5f,
                timeTable = timeCheckedOfDays.map {
                    TimeCheckedOfDayParameter(
                        date = it.date,
                        times = it.timeList
                    )
                }
            )
            retrofitApi.makePlan(uuid, parameter).toLong()
        }

    override suspend fun getRespondUsers(planId: Long): NetworkResult<TimeTable> =
        handleApi {
            retrofitApi.getResponseTimeTable(planId.toString()).toTimeTable()
        }

    override suspend fun sendRespondPlan(
        planId: Long,
        timeCheckedOfDays: List<TimeCheckedOfDay>,
    ): NetworkResult<Unit> =
        handleApi {
            val parameter = TimeCheckedOfDaysParameter(
                unit = 0.5f,
                timeTable = timeCheckedOfDays.map {
                    TimeCheckedOfDayParameter(
                        date = it.date,
                        times = it.timeList
                    )
                }
            )
            retrofitApi.sendRespondPlan(planId.toString(), parameter)
        }

    override suspend fun sendRejectPlan(planId: Long): NetworkResult<Unit> =
        handleApi {
            retrofitApi.sendRejectPlan(planId.toString())
        }

    override suspend fun sendFixPlan(planId: Long, date: String): NetworkResult<Plan.FixedPlan> =
        handleApi {
            retrofitApi.sendFixPlan(planId.toString(), FixPlanParameter(date)).toFixedPlan()
        }

    override suspend fun getFixedPlans(): NetworkResult<List<Plan.FixedPlan>> =
        handleApi {
            retrofitApi.getFixedPlans().map { it.toFixedPlan() }
        }

    override suspend fun getFixedPlan(planId: Long): NetworkResult<Plan.FixedPlan> =
        handleApi {
            retrofitApi.getFixedPlan(planId).toFixedPlan()
        }

    override suspend fun getPlanCategories(): NetworkResult<List<Category>> =
        handleApi {
            retrofitApi.getCategories().map { it.toCategory() }
        }

    override suspend fun getSampleTitle(categoryId: Int): NetworkResult<String> =
        handleApi {
            retrofitApi.getSampleTitle(categoryId).title
        }

    override suspend fun getWaitingPlans(): NetworkResult<List<Plan.WaitingPlan>> =
        handleApi {
            retrofitApi.getWaitingPlans().map { it.toWaitingPlan() }
        }

    override suspend fun createTemporaryPlan(temporaryPlanParameter: TemporaryPlanParameter): NetworkResult<TemporaryPlanUuid> =
        handleApi {
            retrofitApi.createTemporaryPlan(temporaryPlanParameter).toTemporaryPlanUuid()
        }

    override suspend fun signUp(): NetworkResult<User> =
        handleApi {
            retrofitApi.signUp().toUser()
        }

    override suspend fun getUserInfo(): NetworkResult<User> =
        handleApi {
            retrofitApi.getUserInfo().toUser()
        }

    override suspend fun deleteUserInfo(): NetworkResult<Unit> =
        handleApi {
            retrofitApi.deleteUserInfo()
        }
}
