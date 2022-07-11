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
    PlanzBasicButton(
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
    PlanzBasicButton(
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
    Row(
        modifier = modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        BackButton(
            onBackClick = onBackClick,
        )

        PlanzBasicButton(
            text = text,
            enabled = enabled,
            modifier = Modifier.fillMaxWidth(),
            onClick = onClick,
        )
    }
}

@Composable
fun PlanzBasicButton(
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
fun PlanzBottomBasicButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    buttonColor: Color = MainPurple900,
    textColor: Color = Color.White,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier.height(44.dp),
        shape = RoundedCornerShape(6.dp),
        onClick = { if (enabled) onClick() },
        colors = when (enabled) {
            true -> ButtonDefaults.buttonColors(backgroundColor = buttonColor)
            else -> ButtonDefaults.buttonColors(backgroundColor = Gray300)
        },
        elevation = null,
    ) {
        Text(
            text = text,
            color = textColor,
            style = PlanzTypography.subtitle2
        )
    }
}

@Composable
private fun BackButton(
    onBackClick: () -> Unit,
) {
    Button(
        modifier = Modifier
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
