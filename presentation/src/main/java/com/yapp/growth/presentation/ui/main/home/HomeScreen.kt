package com.yapp.growth.presentation.ui.main.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF7F7F8)
    ) {
        Text(text = "Home")
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}