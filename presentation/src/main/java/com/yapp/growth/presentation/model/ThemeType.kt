package com.yapp.growth.presentation.model

import com.yapp.growth.presentation.R

enum class PlanThemeType(val themeStringResId: Int) {
    MEAL(R.string.create_plan_theme_list_meal),
    MEETING(R.string.create_plan_theme_list_meeting),
    TRIP(R.string.create_plan_theme_list_trip),
    ETC(R.string.create_plan_theme_list_etc)
}