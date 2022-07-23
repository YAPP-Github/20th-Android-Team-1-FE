package com.yapp.growth.data.mapper

import com.yapp.growth.data.parameter.TemporaryPlanParameter
import com.yapp.growth.domain.entity.TemporaryPlan
import timber.log.Timber

fun TemporaryPlan.toTemporaryPlanParameter(): TemporaryPlanParameter {
    val s = "2022-01-01T" + String.format("%02d", startHour) + ":00:00"
    val e = "2022-01-01T" + String.format("%02d", endHour) + ":00:00"
    Timber.w("mapper")
    Timber.w("startHour: $s")
    Timber.w("endHour: $e")
    return TemporaryPlanParameter(
        promisingName = title,
        minTime = s,
        maxTime = e,
        categoryId = categoryId,
        availableDates = availableDates,
        placeName = place,
    )
}
