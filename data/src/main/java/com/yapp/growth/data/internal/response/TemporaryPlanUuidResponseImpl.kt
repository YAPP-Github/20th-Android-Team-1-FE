package com.yapp.growth.data.internal.response

import com.yapp.growth.data.response.TemporaryPlanUuidResponse

data class TemporaryPlanUuidResponseImpl(
    override val uuid: String,
) : TemporaryPlanUuidResponse
