package com.yapp.growth.domain.entity

sealed class Plan(
    open val id: Long,
    open val title: String,
    open val isLeader: Boolean,
    open val category: String, // type?
    open val members: List<String>,
) {
    data class WaitingPlan(
        override val id: Long,
        override val title: String,
        override val isLeader: Boolean = false,
        override val category: String,
        override val members: List<String>,
        val startTime: String,
        val endTime: String,
    ) : Plan(
        id = id,
        title = title,
        isLeader = isLeader,
        category = category,
        members = members,
    )

    data class FixedPlan(
        override val id: Long,
        override val title: String,
        override val isLeader: Boolean = false,
        override val category: String,
        override val members: List<String>,
        val place: String,
        val date: String,
    ) : Plan(
        id = id,
        title = title,
        isLeader = isLeader,
        category = category,
        members = members,
    )
}
