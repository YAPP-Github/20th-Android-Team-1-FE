package com.yapp.growth.domain.entity

sealed class Plan(
    open val id: Int,
    open val title: String,
    open val isLeader: Boolean,
    open val category: String, // type?
    open val members: List<String>,
    open val place: String,
) {
    data class WaitingPlan(
        override val id: Int,
        override val title: String,
        override val isLeader: Boolean = false,
        override val category: String,
        override val members: List<String>,
        override val place: String,
        val startTime: Int,
        val endTime: Int,
        val isAlreadyReplied: Boolean,
    ) : Plan(
        id = id,
        title = title,
        isLeader = isLeader,
        category = category,
        members = members,
        place = place,
    )

    data class FixedPlan(
        override val id: Int,
        override val title: String,
        override val isLeader: Boolean = false,
        override val category: String,
        override val members: List<String>,
        override val place: String,
        val date: String,
    ) : Plan(
        id = id,
        title = title,
        isLeader = isLeader,
        category = category,
        members = members,
        place = place,
    )
}
