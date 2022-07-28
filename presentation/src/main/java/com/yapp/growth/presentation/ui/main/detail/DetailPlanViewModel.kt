package com.yapp.growth.presentation.ui.main.detail

import android.annotation.SuppressLint
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.domain.onSuccess
import com.yapp.growth.domain.usecase.GetFixedPlanUseCase
import com.yapp.growth.presentation.ui.main.KEY_PLAN_ID
import com.yapp.growth.presentation.ui.main.detail.DetailPlanContract.DetailPlanEvent
import com.yapp.growth.presentation.ui.main.detail.DetailPlanContract.DetailPlanSideEffect
import com.yapp.growth.presentation.ui.main.detail.DetailPlanContract.DetailPlanViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DetailPlanViewModel @Inject constructor(
    private val getFixedPlanUseCase: GetFixedPlanUseCase,
    private val savedStateHandle: SavedStateHandle,
) :
    BaseViewModel<DetailPlanViewState, DetailPlanSideEffect, DetailPlanEvent>(
        DetailPlanViewState()
    ) {
    companion object {
        const val DEFAULT_PLAN_ID : Long = 1
    }

    init {
        fetchPlan(savedStateHandle.get<Long>(KEY_PLAN_ID) ?: DEFAULT_PLAN_ID)
    }

    override fun handleEvents(event: DetailPlanEvent) {
        when (event) {
            is DetailPlanEvent.OnClickExitButton -> sendEffect({ DetailPlanSideEffect.ExitDetailPlanScreen })
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun fetchPlan(planId: Long) {
        viewModelScope.launch {
            val result = (getFixedPlanUseCase.invoke(planId))

            result.onSuccess {
                updateState {
                    copy(
                        title = it.title,
                        category = it.category.keyword,
                        date = convertDate(it.date),
                        place = it.place,
                        member = convertMemberList(it.members)
                    )
                }
            }
        }
    }

    // 2022-11-28 11:30:00 -> 11월 28일 오전 11시
    @SuppressLint("SimpleDateFormat")
    private fun convertDate(date: String): String {
        val tmp: Date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(date) as Date

        return SimpleDateFormat("M월 d일 aa h시", Locale.KOREA).format(tmp)
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
