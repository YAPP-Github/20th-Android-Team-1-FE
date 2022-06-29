package com.yapp.growth.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.growth.presentation.theme.*

@Composable
fun PlanzMainButton(
    text: String,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    PlanzButton(
        text = text,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        onClick = onClick,
    )
}

@Composable
fun PlanzSecondButton(
    text: String,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    PlanzButton(
        text = text,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        buttonColor = MainPurple300,
        textColor = MainPurple900,
        onClick = onClick,
    )
}

@Composable
fun PlanzButtonWithBack(
    text: String,
    enabled: Boolean = true,
    modifier: Modifier,
    onClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    Row(modifier = modifier.padding(horizontal = 16.dp)) {
        PlanzBackButton(
            modifier = modifier,
            onBackClick = onBackClick
        )
        Spacer(modifier = Modifier.width(10.dp))
        PlanzButton(
            text = text,
            enabled = enabled,
            modifier = modifier
                .fillMaxWidth(),
            onClick = onClick,
        )
    }
}

@Composable
private fun PlanzButton(
    text: String,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    buttonColor: Color = MainPurple900,
    textColor: Color = Gray100,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier.height(52.dp),
        shape = RoundedCornerShape(10.dp),
        onClick = { if (enabled) onClick() },
        colors = when (enabled) {
            true -> ButtonDefaults.buttonColors(backgroundColor = buttonColor)
            else -> ButtonDefaults.buttonColors(backgroundColor = Gray300)
        },
        elevation = null,
    ) {
        Text(
            text = text,
            color = if (enabled) textColor else Gray500,
            style = PlanzTypography.button
        )
    }
}

@Composable
private fun PlanzBackButton(
    modifier: Modifier,
    onBackClick: () -> Unit,
) {
    Button(
        modifier = modifier
            .width(52.dp)
            .height(52.dp),
        shape = RoundedCornerShape(10.dp),
        onClick = { onBackClick() },
        colors = ButtonDefaults.buttonColors(backgroundColor = Gray200),
        elevation = null,
    ) {
        Icon(
            painter = painterResource(id = com.yapp.growth.presentation.R.drawable.ic_arrow_back),
            contentDescription = "back arrow",
            tint = Color.Unspecified,
        )
    }
}

@Preview
@Composable
fun PlanzMainButtonPreview() {
    PlanzMainButton(
        text = "메인 버튼",
        enabled = true,
        modifier = Modifier,
        onClick = {}
    )
}

@Preview
@Composable
fun PlanzSecondButtonPreview() {
    PlanzSecondButton(
        text = "세컨드 버튼",
        enabled = true,
        modifier = Modifier,
        onClick = {}
    )
}

@Preview
@Composable
fun PlanzDisabledButtonPreview() {
    PlanzMainButton(
        text = "비활성 버튼",
        enabled = false,
        modifier = Modifier,
        onClick = {}
    )
}

@Preview
@Composable
fun PlanzWithBackButtonPreview() {
    PlanzButtonWithBack(
        text = "다음",
        enabled = false,
        modifier = Modifier,
        onClick = {},
        onBackClick = {}
    )
}