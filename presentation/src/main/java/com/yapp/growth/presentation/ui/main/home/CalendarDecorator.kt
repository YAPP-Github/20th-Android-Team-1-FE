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
import com.yapp.growth.presentation.theme.*
import java.util.*


class CalendarDecorator {
    class TodayDecorator(context: Context) : DayViewDecorator {
        private var date = CalendarDay.today()
        private val boldSpan = StyleSpan(Typeface.BOLD)

        @SuppressLint("UseCompatLoadingForDrawables")
        val drawable = ContextCompat.getDrawable(context, R.drawable.bg_calendar_today)
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

    class SelectDecorator(context: Context, mCalendar: MaterialCalendarView) : DayViewDecorator {
        @SuppressLint("UseCompatLoadingForDrawables")
        private val mCalendar = mCalendar.currentDate.calendar
        private val drawable = ContextCompat.getDrawable(context, R.drawable.bg_calendar_selection)

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

    class OtherDayDecorator(context: Context, mCalendar: MaterialCalendarView) : DayViewDecorator {
        @SuppressLint("UseCompatLoadingForDrawables")
        private val mCalendar = mCalendar.currentDate.calendar
        private val drawable = ContextCompat.getDrawable(context, R.drawable.bg_calendar_selection)
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

    // TODO : 일정이 있는 날만 점 데코레이터 찍기
    class DotDecorator : DayViewDecorator {
        private var date = CalendarDay.from(2022, 5, 21)

        override fun shouldDecorate(day: CalendarDay): Boolean {
            return day == date
        }

        override fun decorate(view: DayViewFacade) {
            view.addSpan(CustomMultipleDotSpan(4F, color = intArrayOf(SubCoral.toArgb(), SubYellow.toArgb(), MainPurple900.toArgb())))
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
            var leftMost = (total - 1) * - 10

            for (i in 0 until total) {
                val oldColor: Int = paint.color
                if (color[i] != 0) {
                    paint.color = color[i]
                }
                canvas.drawCircle(((left + right) / 2 - leftMost).toFloat(), bottom + 10F , radius, paint)
                paint.color = oldColor
                leftMost += 20
            }
        }
    }
}