package com.yapp.growth.presentation.ui

import androidx.lifecycle.ViewModel
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.usecase.GetUserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TempViewModel @Inject constructor(
    private val getUserListUseCase: GetUserListUseCase,
) : ViewModel() {

    suspend fun loadInit(): Unit {
        when (val result = getUserListUseCase.invoke()) {
            is NetworkResult.Success -> {
                val info = result.data
            }
            is NetworkResult.Error -> {
                val error = result.exception
            }
        }
    }
}