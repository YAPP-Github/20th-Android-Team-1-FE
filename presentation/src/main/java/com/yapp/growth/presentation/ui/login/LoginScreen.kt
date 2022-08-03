package com.yapp.growth.presentation.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.theme.MainPurple900
import com.yapp.growth.presentation.theme.PlanzTypography

@Composable
fun LoginScreen(
    onClickKakaoLogin: () -> Unit,
    onClickNonLogin: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 20.dp, bottom = 32.dp)
        ) {
            Introduce(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                onClickNonLogin = onClickNonLogin
            )

            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.ic_login_image),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 36.dp),
        ) {
            KakaoLoginButton(onClickKakaoLogin = onClickKakaoLogin)
        }

    }
}

@Composable
fun Introduce(modifier: Modifier, onClickNonLogin: () -> Unit) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier
                .align(Alignment.End)
                .clickable {
                    onClickNonLogin()
                },
            text = "둘러보기",
            textDecoration = TextDecoration.Underline,
            style = PlanzTypography.body2,
        )

        Spacer(modifier = Modifier.height(6.dp))
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 6.dp),
                text = stringResource(id = R.string.planz_title),
                style = PlanzTypography.h3,
                color = MainPurple900
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp),
            text = stringResource(id = R.string.login_introduce_text),
            style = PlanzTypography.h1
        )
    }
}

@Composable
fun KakaoLoginButton(
    onClickKakaoLogin: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        onClick = onClickKakaoLogin,
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
                text = stringResource(id = R.string.icon_kakao_login_title),
                modifier = Modifier.padding(start = 6.dp),
                color = Color.Black,
                style = PlanzTypography.h3
            )
        }
    }
}
