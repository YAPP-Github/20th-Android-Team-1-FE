package com.yapp.growth.presentation.component


import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import com.yapp.growth.presentation.ui.main.home.CalendarDecorator

@Composable
fun PlanzCalendar(
    currentDate: CalendarDay,
    selectMode: PlanzCalendarSelectMode,
    selectedDates: List<CalendarDay> = emptyList(),
    monthlyDates: Map<CalendarDay, Int> = emptyMap(),
    onDateSelectedListener: OnDateSelectedListener,
) {
    val context = LocalContext.current

    AndroidView(
        modifier = Modifier
            .padding(bottom = 12.dp),
        factory = {
            MaterialCalendarView(it).apply {
                this.isDynamicHeightEnabled = true
                this.topbarVisible = false
                this.isPagingEnabled = false

                when(selectMode) {
                    PlanzCalendarSelectMode.SINGLE -> {
                        this.selectionMode = MaterialCalendarView.SELECTION_MODE_SINGLE
                        this.showOtherDates = MaterialCalendarView.SHOW_OTHER_MONTHS
                        this.setAllowClickDaysOutsideCurrentMonth(false)
                    }
                    PlanzCalendarSelectMode.MULTIPLE -> {
                        this.selectionMode = MaterialCalendarView.SELECTION_MODE_MULTIPLE
                        this.showOtherDates = MaterialCalendarView.SHOW_DEFAULTS
                    }
                }
            }
        },
        update = { views ->
            views.apply {
                this.currentDate = currentDate
                this.setOnDateChangedListener(onDateSelectedListener)
                this.removeDecorators()

                if(selectedDates.isNotEmpty()) {
                    for(selectDate in selectedDates) {
                        this.setDateSelected(selectDate, true)
                    }
                }

                this.addDecorator(CalendarDecorator.SelectDecorator(context, this))
                this.addDecorator(CalendarDecorator.SundayDecorator())

                when(selectMode) {
                    PlanzCalendarSelectMode.SINGLE -> {
                        monthlyDates.forEach {
                            if (it.key != CalendarDay.today()) {
                                when (it.value) {
                                    1 -> this.addDecorator(CalendarDecorator.SingleDotDecorator(it.key))
                                    2 -> this.addDecorator(CalendarDecorator.DoubleDotDecorator(it.key))
                                    else -> this.addDecorator(CalendarDecorator.TripleDotDecorator(it.key))
                                }
                            }
                        }
                        this.addDecorator(CalendarDecorator.TodayDecorator(context))
                        this.addDecorator(CalendarDecorator.OtherMonthDisableSelectionDecorator(context, this))
                    }
                    PlanzCalendarSelectMode.MULTIPLE -> {
                        this.addDecorator(CalendarDecorator.SelectedDatesDecorator(context, this.selectedDates))
                        this.addDecorator(CalendarDecorator.TodayBoldDecorator())
                        this.addDecorator(CalendarDecorator.TodayLessDecorator(context))
                    }
                }
            }
        }
    )
}

enum class PlanzCalendarSelectMode {
    SINGLE,
    MULTIPLE
}
