package com.yapp.growth.data.response

data class FixedPlanResponseImpl(
    override val id: Long,
    override val promiseName: String,
    override val promiseDate: String,
    override val owner: UserResponseImpl,
    override val isOwner: Boolean,
    override val category: CategoryResponseImpl,
    override val members: List<UserResponseImpl>,
    override val placeName: String
): FixedPlanResponse

data class UserResponseImpl (
    override val id: Long,
    override val userName: String
): UserResponse

data class CategoryResponseImpl (
    override val id: Long,
    override val keyword: String
): CategoryResponse