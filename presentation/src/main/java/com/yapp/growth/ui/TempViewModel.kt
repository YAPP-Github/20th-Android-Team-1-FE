package com.yapp.growth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.growth.domain.NetworkResult
import com.yapp.growth.domain.usecase.GetUserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TempViewModel @Inject constructor(
    private val getUserListUseCase: GetUserListUseCase
): ViewModel() {

    suspend fun loadInit(): Unit {
        val result = getUserListUseCase.invoke()
        when(result) {
            is NetworkResult.Success -> {
                val info = result.data
            }
            is NetworkResult.Error -> {
                val error = result.exception
            }
        }
    }
}