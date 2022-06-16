package com.yapp.growth.presentation.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val MainPurple      = Color(0xFF8886FF)
val MainPurpleLight = Color(0xFFE0E3FD)
val SubYellow       = Color(0xFFFEDB78)
val SubCoral        = Color(0XFFFF7F77)
val MainGradient    = Brush.linearGradient(
                            colors  = listOf(Color(0xFF8886FF), Color(0xFF6671F6)),
                            start   = Offset.Zero,
                            end     = Offset.Infinite)

// TODO :: 디자이너분께서 색상 관련 자세한 정보 주시면 추후 수정
val Gray900         = Color(0XFF020202)
val Gray700         = Color(0XFF3E414B)
val Gray500         = Color(0XFF6A707A)
val Gray400         = Color(0XFF9CA3AD)
val Gray300         = Color(0XFFCDD2D9)
val Gray200         = Color(0XFFE8EAED)
val Gray100         = Color(0XFFF3F5F8)