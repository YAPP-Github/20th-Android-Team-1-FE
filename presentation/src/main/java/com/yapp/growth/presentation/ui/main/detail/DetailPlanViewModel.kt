package com.yapp.growth.presentation.ui.main.detail

import android.annotation.SuppressLint
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.presentation.ui.main.detail.DetailPlanContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class DetailPlanViewModel @Inject constructor() :
    BaseViewModel<DetailPlanViewState, DetailPlanSideEffect, DetailPlanEvent>(
        DetailPlanViewState()
    ) {
    companion object {
        const val DEFAULT_PLAN_ID = 1
    }

    init {
        getPlanState(DEFAULT_PLAN_ID)
    }

    override fun handleEvents(event: DetailPlanEvent) {
        when (event) {
            is DetailPlanEvent.OnClickExitButton -> sendEffect({ DetailPlanSideEffect.ExitDetailPlanScreen })
        }
    }

    // TODO : GET /api/promises/<promiseId>
    @SuppressLint("SimpleDateFormat")
    private fun getPlanState(planId: Int) {

        // TODO : 테스트용 더미값입니다 (정호)
        val date = "2022-11-28 11:30:00"
        val members = listOf(
            "을릉도동남쪽",
            "뱃길따라이백",
            "리외로운섬하",
            "나새들의고향",
            "김갑수",
            "김울먹",
            "제발목숨만은",
        )

        updateState {
            copy(
                title = "스무자스무자스무자스무자스무자스무자스무",
                category = "식사",
                date = convertDate(date),
                place = "스무자스무자스무자스무자스무자스무자스무",
                member = convertMemberList(members)
            )
        }
    }

    // 2022-11-28 11:30:00 -> 11월 28일 오전 11시
    @SuppressLint("SimpleDateFormat")
    private fun convertDate(date: String): String {
        val tmp: Date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date) as Date
        var result = SimpleDateFormat("M월 d일 aa h시").format(tmp)

        if (result.contains("AM")) {
            result = result.replace("AM", "오전")
        } else {
            result = result.replace("PM", "오후")
        }

        return result
    }

    private fun convertMemberList(members: List<String>): String {
        var result = ""
        var stringNumber = members[0].length

        for (i in members.indices) {
            result += members[i]

            if (i == members.size - 1) {
                break
            } else {
                stringNumber += members[i + 1].length

                if (stringNumber > 20) {
                    result += "\n"
                    stringNumber = members[i + 1].length
                } else {
                    result += ", "
                    stringNumber += 2
                }
            }
        }

        return result
    }
}