package com.yapp.growth.data.internal.response

import com.squareup.moshi.Json
import com.yapp.growth.data.response.SampleTitleResponse

class SampleTitleResponseImpl(
    @Json(name = "name")
    override val title: String,
) : SampleTitleResponse
