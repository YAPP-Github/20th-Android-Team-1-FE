package com.yapp.growth.data.internal.source

import com.yapp.growth.data.api.GrowthApi
import com.yapp.growth.data.api.handleApi
import com.yapp.growth.data.mapper.toTemporaryPlanUuid
import com.yapp.growth.data.parameter.TemporaryPlanParameter
import com.yapp.growth.data.source.TemporaryPlanDataSource
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.entity.TemporaryPlanUuid
import timber.log.Timber
import javax.inject.Inject

class TemporaryPlanDataSourceImpl @Inject constructor(
    private val api: GrowthApi,
): TemporaryPlanDataSource {
    override suspend fun createTemporaryPlan(
        temporaryPlanParameter: TemporaryPlanParameter
    ): NetworkResult<TemporaryPlanUuid> = handleApi {
        Timber.w("title = ${temporaryPlanParameter.promisingName}")
        Timber.w("startHour = ${temporaryPlanParameter.minTime}")
        Timber.w("endHour = ${temporaryPlanParameter.maxTime}")
        Timber.w("categoryId = ${temporaryPlanParameter.categoryId}")
        Timber.w("availableDates = ${temporaryPlanParameter.availableDates}")
        Timber.w("place = ${temporaryPlanParameter.placeName}")
        api.createTemporaryPlan(temporaryPlanParameter).toTemporaryPlanUuid()
    }
}
