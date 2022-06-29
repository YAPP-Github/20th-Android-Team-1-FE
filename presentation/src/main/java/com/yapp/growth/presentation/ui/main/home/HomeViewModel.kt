package com.yapp.growth.presentation.ui.main.home

import com.yapp.growth.base.BaseViewModel
import com.yapp.growth.presentation.ui.main.home.HomeContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
) : BaseViewModel<HomeViewState, HomeSideEffect, HomeEvent>(HomeViewState) {

    override fun handleEvents(event: HomeEvent) {

    }
}