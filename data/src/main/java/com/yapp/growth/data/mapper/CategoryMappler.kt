package com.yapp.growth.data.mapper

import com.yapp.growth.data.response.CategoryResponse
import com.yapp.growth.domain.entity.Category

fun CategoryResponse.toCategory(): Category {
    return Category(
        id = this.id,
        keyword = this.keyword
    )
}
