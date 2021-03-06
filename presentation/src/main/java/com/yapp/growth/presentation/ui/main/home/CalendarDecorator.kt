package com.yapp.growth.presentation.ui.main.home

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.text.style.ForegroundColorSpan
import android.text.style.LineBackgroundSpan
import android.text.style.StyleSpan
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.spans.DotSpan.DEFAULT_RADIUS
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.theme.Gray300
import com.yapp.growth.presentation.theme.Gray900
import com.yapp.growth.presentation.theme.MainPurple900
import com.yapp.growth.presentation.theme.SubCoral
import com.yapp.growth.presentation.theme.SubYellow
import java.util.*


class CalendarDecorator {

    class TodayLessDecorator(context: Context) : DayViewDecorator {
        private var date = CalendarDay.today()
        private val boldSpan = StyleSpan(Typeface.NORMAL)

        val drawable = ContextCompat.getDrawable(context, R.drawable.bg_calendar_disabled_selection)

        @SuppressLint("UseCompatLoadingForDrawables")
        override fun shouldDecorate(day: CalendarDay): Boolean {
            return day.isBefore(date)
        }

        override fun decorate(view: DayViewFacade) {
            view.apply {
                this.addSpan(object : ForegroundColorSpan(Gray300.toArgb()) {})
                this.addSpan(boldSpan)
                this.setSelectionDrawable(drawable!!)
            }
        }
    }

    class TodayBoldDecorator : DayViewDecorator {
        private var date = CalendarDay.today()
        private val boldSpan = StyleSpan(Typeface.BOLD)

        @SuppressLint("UseCompatLoadingForDrawables")
        override fun shouldDecorate(day: CalendarDay): Boolean {
            return day == date
        }

        override fun decorate(view: DayViewFacade) {
            view.apply {
                this.addSpan(boldSpan)
            }
        }
    }

    class TodayDecorator(context: Context) : DayViewDecorator {
        private var date = CalendarDay.today()
        private val boldSpan = StyleSpan(Typeface.BOLD)

        @SuppressLint("UseCompatLoadingForDrawables")
        val drawable = ContextCompat.getDrawable(context, R.drawable.bg_calendar_enabled)
        override fun shouldDecorate(day: CalendarDay): Boolean {
            return day == date
        }

        override fun decorate(view: DayViewFacade) {
            if (drawable != null) {
                view.apply {
                    this.addSpan(object : ForegroundColorSpan(Color.White.toArgb()) {})
                    this.setSelectionDrawable(drawable)
                    this.addSpan(boldSpan)
                }
            }
        }
    }

    class SelectedDatesDecorator(context: Context, private val selectedDates: List<CalendarDay>) :
        DayViewDecorator {
        @SuppressLint("UseCompatLoadingForDrawables")
        private val drawable = ContextCompat.getDrawable(context, R.drawable.bg_calendar_enabled_selection)
        private val boldSpan = StyleSpan(Typeface.BOLD)

        override fun shouldDecorate(day: CalendarDay): Boolean {
            return selectedDates.contains(day)
        }

        override fun decorate(view: DayViewFacade) {
            if (drawable != null) {
                view.apply {
                    this.addSpan(object : ForegroundColorSpan(Color.White.toArgb()) {})
                    this.setSelectionDrawable(drawable)
                    this.addSpan(boldSpan)
                }
            }
        }
    }

    class SelectDecorator(context: Context, mCalendar: MaterialCalendarView) : DayViewDecorator {
        @SuppressLint("UseCompatLoadingForDrawables")
        private val mCalendar = mCalendar.currentDate.calendar
        private val drawable = ContextCompat.getDrawable(context, R.drawable.bg_calendar_disabled_selection)

        override fun shouldDecorate(day: CalendarDay): Boolean {
            val calendar = day.calendar

            return calendar.get(Calendar.ERA) == mCalendar.get(Calendar.ERA)
                    && calendar.get(Calendar.YEAR) == mCalendar.get(Calendar.YEAR)
                    && calendar.get(Calendar.MONTH) == mCalendar.get(Calendar.MONTH)
        }

        override fun decorate(view: DayViewFacade) {
            if (drawable != null) {
                view.apply {
                    this.addSpan(object : ForegroundColorSpan(Gray900.toArgb()) {})
                    this.setSelectionDrawable(drawable)
                }
            }
        }
    }

    class OtherMonthDisableSelectionDecorator(context: Context, mCalendar: MaterialCalendarView) : DayViewDecorator {
        @SuppressLint("UseCompatLoadingForDrawables")
        private val mCalendar = mCalendar.currentDate.calendar
        private val drawable = ContextCompat.getDrawable(context, R.drawable.bg_calendar_disabled_selection)
        override fun shouldDecorate(day: CalendarDay): Boolean {
            val calendar = day.calendar

            return calendar.get(Calendar.ERA) == mCalendar.get(Calendar.ERA)
                    && calendar.get(Calendar.YEAR) == mCalendar.get(Calendar.YEAR)
                    && calendar.get(Calendar.MONTH) != mCalendar.get(Calendar.MONTH)
        }

        override fun decorate(view: DayViewFacade) {
            if (drawable != null) {
                view.apply {
                    this.addSpan(object : ForegroundColorSpan(Gray300.toArgb()) {})
                    this.setSelectionDrawable(drawable)
                }
            }
        }
    }

    class SundayDecorator : DayViewDecorator {
        private val calendar = Calendar.getInstance()
        override fun shouldDecorate(day: CalendarDay): Boolean {
            day.copyTo(calendar)
            val weekDay = calendar.get(Calendar.DAY_OF_WEEK)
            return (weekDay == Calendar.SUNDAY)
        }

        override fun decorate(view: DayViewFacade) {
            view.addSpan(object : ForegroundColorSpan(SubCoral.toArgb()) {})
        }
    }

    class SingleDotDecorator(private val calendarDay: CalendarDay) : DayViewDecorator {

        override fun shouldDecorate(day: CalendarDay): Boolean {
            return calendarDay == day
        }

        override fun decorate(view: DayViewFacade) {
            view.addSpan(CustomMultipleDotSpan(4F, color = intArrayOf(SubCoral.toArgb())))
        }
    }

    class DoubleDotDecorator(private val calendarDay: CalendarDay) : DayViewDecorator {

        override fun shouldDecorate(day: CalendarDay): Boolean {
            return calendarDay == day
        }

        override fun decorate(view: DayViewFacade) {
            view.addSpan(CustomMultipleDotSpan(4F, color = intArrayOf(SubCoral.toArgb(), SubYellow.toArgb())))
        }
    }

    class TripleDotDecorator(private val calendarDay: CalendarDay) : DayViewDecorator {

        override fun shouldDecorate(day: CalendarDay): Boolean {
            return calendarDay == day
        }

        override fun decorate(view: DayViewFacade) {
            view.addSpan(
                CustomMultipleDotSpan(
                    4F,
                    color = intArrayOf(
                        SubCoral.toArgb(),
                        SubYellow.toArgb(),
                        MainPurple900.toArgb()
                    )
                )
            )
        }
    }

    class CustomMultipleDotSpan : LineBackgroundSpan {
        private val radius: Float
        private var color = IntArray(0)

        constructor() {
            radius = DEFAULT_RADIUS
            color[0] = 0
        }

        constructor(color: Int) {
            radius = DEFAULT_RADIUS
            this.color[0] = 0
        }

        constructor(radius: Float) {
            this.radius = radius
            color[0] = 0
        }

        constructor(radius: Float, color: IntArray) {
            this.radius = radius
            this.color = color
        }

        override fun drawBackground(
            canvas: Canvas,
            paint: Paint,
            left: Int,
            right: Int,
            top: Int,
            baseline: Int,
            bottom: Int,
            charSequence: CharSequence,
            start: Int,
            end: Int,
            lineNum: Int
        ) {
            val total = if (color.size > 5) 5 else color.size
            var leftMost = (total - 1) * -10

            for (i in 0 until total) {
                val oldColor: Int = paint.color
                if (color[i] != 0) {
                    paint.color = color[i]
                }
                canvas.drawCircle(
                    ((left + right) / 2 - leftMost).toFloat(),
                    bottom + 10F,
                    radius,
                    paint
                )
                paint.color = oldColor
                leftMost += 20
            }
        }
    }
}
