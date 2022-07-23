package com.yapp.growth.data.internal.response

import com.yapp.growth.data.response.CategoryResponse

data class CategoryResponseImpl (
    override val id: Long,
    override val keyword: String
): CategoryResponse
