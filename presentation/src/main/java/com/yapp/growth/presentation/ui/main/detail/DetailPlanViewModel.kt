package com.yapp.growth.presentation.ui.main.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.domain.onSuccess
import com.yapp.growth.domain.usecase.GetFixedPlanUseCase
import com.yapp.growth.presentation.ui.main.KEY_PLAN_ID
import com.yapp.growth.presentation.ui.main.detail.DetailPlanContract.DetailPlanEvent
import com.yapp.growth.presentation.ui.main.detail.DetailPlanContract.DetailPlanSideEffect
import com.yapp.growth.presentation.ui.main.detail.DetailPlanContract.DetailPlanViewState
import com.yapp.growth.presentation.util.toDayAndHour
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPlanViewModel @Inject constructor(
    private val getFixedPlanUseCase: GetFixedPlanUseCase,
    savedStateHandle: SavedStateHandle,
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

    private fun fetchPlan(planId: Long) {
        viewModelScope.launch {
            val result = (getFixedPlanUseCase.invoke(planId))

            result.onSuccess {
                updateState {
                    copy(
                        title = it.title,
                        category = it.category.keyword,
                        date = it.date.toDayAndHour(),
                        place = it.place,
                        member = convertMemberList(it.members)
                    )
                }
            }
        }
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
