package com.yapp.growth.presentation.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.theme.MainPurple900
import com.yapp.growth.presentation.theme.PlanzTypography


@Composable
fun TitleIconAndText() {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            modifier = Modifier.align(Alignment.CenterVertically),
            painter = painterResource(id = R.drawable.ic_login_title_icon),
            contentDescription = null,
            tint = Color.Unspecified
        )
        Text(
            modifier = Modifier.fillMaxWidth().padding(start = 6.dp),
            text = stringResource(id = R.string.planz_title),
            style = PlanzTypography.h3,
            color = MainPurple900
        )
    }
}

@Composable
fun IntroduceText() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 14.dp),
        text = stringResource(id = R.string.login_introduce_text),
        style = PlanzTypography.h1
    )
}

@Composable
fun PlanzImage() {
    Box(
        modifier = Modifier.fillMaxWidth().padding(top = 30.dp, bottom = 54.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_login_image),
            contentDescription = null
        )
    }
}

@Composable
fun KakaoLoginButton(
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xffFEE500)
        ),
        elevation = null
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.icon_kakao),
                contentDescription = null,
                tint = Color.Unspecified
            )

            Text(
                text = "카카오 로그인",
                modifier = Modifier.padding(start = 6.dp),
                color = Color.Black,
                style = PlanzTypography.h3
            )
        }
    }
}
