package com.yapp.growth.data.internal.response

import com.yapp.growth.data.response.CategoryResponse

data class CategoryResponseImpl (
    override val id: Int,
    override val keyword: String,
    override val type: String,
): CategoryResponse
