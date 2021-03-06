package com.yapp.growth.presentation.util

import com.prolificinteractive.materialcalendarview.CalendarDay
import java.text.SimpleDateFormat
import java.util.*

internal val PARSE_DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.KOREA)
internal val FULL_DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA)
internal val DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)

fun CalendarDay.toFormatDate(): String {
    return DATE_FORMAT.format(this.date)
}

fun Date.toFormatDate(): String {
    return DATE_FORMAT.format(this)
}

fun CalendarDay.toParseFormatDate(): String {
    return PARSE_DATE_FORMAT.format(this.date)
}

fun Date.toCalculateDiffDay(other: Date): Long {
    return (other.time - this.time) / (60 * 60 * 24 * 1000)
}

fun String.toDate(): Date {
    return PARSE_DATE_FORMAT.parse(this) ?: Date()
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

fun String.getCurrentBlockDate(count: Int): String {
    val currentTime = this
    val calendar = Calendar.getInstance().apply {
        time = PARSE_DATE_FORMAT.parse(currentTime) ?: Date()
    }
    repeat(count) {
        calendar.add(Calendar.MINUTE, 30)
    }
    return PARSE_DATE_FORMAT.format(calendar.time)
}

fun String.toDayOfWeek(): String {
    val currentTime = this
    val calendar = Calendar.getInstance().apply {
        time = PARSE_DATE_FORMAT.parse(currentTime) ?: Date()
    }
    return DayOfWeek.values()[calendar.get(Calendar.DAY_OF_WEEK)].dayOfWeek
}

fun String.to12HourClock(): String {
    val currentTime = this
    val calendar = Calendar.getInstance().apply {
        time = PARSE_DATE_FORMAT.parse(currentTime) ?: Date()
    }
    return AM_PM.values()[calendar.get(Calendar.AM_PM)].am_pm
}

fun String.toPlanDate(): String {
    val dateTimeFormat: SimpleDateFormat
    val currentTime = this
    val calendar = Calendar.getInstance().apply {
        time = PARSE_DATE_FORMAT.parse(currentTime) ?: Date()
    }

    dateTimeFormat = if (calendar.get(Calendar.MINUTE) == 0) {
        SimpleDateFormat("M??? d???(${this.toDayOfWeek()}) ${this.to12HourClock()}HH???", Locale.KOREA)
    } else {
        SimpleDateFormat("M??? d???(${this.toDayOfWeek()}) ${this.to12HourClock()}HH??? mm???", Locale.KOREA)
    }

    val displayText = PARSE_DATE_FORMAT.parse(currentTime) ?: ""
    return dateTimeFormat.format(displayText)
}


enum class DayOfWeek(val dayOfWeek: String) {
    UNKNOWN("??????"), SUNDAY("???"),
    MONDAY("???"), TUESDAY("???"),
    WEDNESDAY("???"), THURSDAY("???"),
    FRIDAY("???"), SATURDAY("???")
}

enum class AM_PM(val am_pm: String) {
    MORNING("??????"), AFTERNOON("??????")
}
