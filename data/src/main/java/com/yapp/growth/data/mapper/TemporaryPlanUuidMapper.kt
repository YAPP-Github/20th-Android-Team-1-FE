package com.yapp.growth.data.mapper

import com.yapp.growth.data.response.TemporaryPlanUuidResponse
import com.yapp.growth.domain.entity.TemporaryPlanUuid

fun TemporaryPlanUuidResponse.toTemporaryPlanUuid(): TemporaryPlanUuid {
    return TemporaryPlanUuid(uuid)
}
