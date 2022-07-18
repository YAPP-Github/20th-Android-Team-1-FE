package com.yapp.growth.presentation.util

import com.prolificinteractive.materialcalendarview.CalendarDay
import java.text.SimpleDateFormat
import java.util.*

internal val PARSE_DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA)

//fun Date.toDay(): String {
//    val dfDay: DateFormat = SimpleDateFormat("M/d", Locale.KOREA)
//    return dfDay.format(this)
//}
//
//fun Date.toHour(): String {
//    val dfHour: DateFormat = SimpleDateFormat("HH:mm", Locale.KOREA)
//    return dfHour.format(this)
//}

fun CalendarDay.toFormatDate(): String {
    return PARSE_DATE_FORMAT.format(this.date)
}

fun Date.toCalculateDiffDay(other: Date): Long {
    return (other.time - this.time) / (60 * 60 * 24 * 1000)
}

fun String.toDay(): String {
    val dateTimeFormat = SimpleDateFormat("M/d", Locale.KOREA)
    val displayText = PARSE_DATE_FORMAT.parse(this) ?: ""
    return dateTimeFormat.format(displayText)
}

fun String.toHour(): String {
    val dateTimeFormat = SimpleDateFormat("HH:mm", Locale.KOREA)
    val displayText = PARSE_DATE_FORMAT.parse(this) ?: ""
    return dateTimeFormat.format(displayText)
}
