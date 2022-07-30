package com.yapp.growth.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.theme.*

@Composable
fun PlanzDialog(
    title: String,
    content: String,
    positiveButtonText: String,
    negativeButtonText: String,
    onCancelButtonClick: () -> Unit,
    onPositiveButtonClick: () -> Unit,
    onNegativeButtonClick: () -> Unit
) {
    Dialog(onDismissRequest = { onCancelButtonClick() }) {
        Surface(
            modifier = Modifier
                .width(244.dp)
                .wrapContentHeight(),
            shape = RoundedCornerShape(12.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_exit),
                    tint = Color.Unspecified,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .clickable { onCancelButtonClick() }
                        .align(Alignment.End),
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = title,
                    style = PlanzTypography.h2,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = content,
                    color = CoolGray500,
                    style = PlanzTypography.body2,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    onClick = { onPositiveButtonClick() },
                ) {
                    Text(
                        text = positiveButtonText,
                        style = PlanzTypography.subtitle2,
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = negativeButtonText,
                    style = PlanzTypography.caption,
                    color = Gray500,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNegativeButtonClick() }
                )
            }
        }
    }
}

@Composable
fun PlanzAlertDialog(
    title: String,
    content: String,
    positiveButtonText: String,
    negativeButtonText: String,
    onClickNegativeButton: () -> Unit,
    onClickPositiveButton: () -> Unit,
) {
    AlertDialog(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp)),
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = title,
                style = PlanzTypography.subtitle1,
                color = Gray900,
            )
        },
        text = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = content,
                style = PlanzTypography.body2,
                color = Gray900,
            )
        },
        confirmButton = {
            PlanzDialogButton(
                text = positiveButtonText,
                isPositive = true,
                onClick = onClickPositiveButton,
            )
        },
        dismissButton = {
            PlanzDialogButton(
                modifier = Modifier.wrapContentWidth(Alignment.Start),
                text = negativeButtonText,
                isPositive = false,
                onClick = onClickNegativeButton,
            )
        },
        onDismissRequest = onClickNegativeButton,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        )
    )
}

@Composable
fun PlanzDialogButton(
    modifier: Modifier = Modifier,
    text: String,
    isPositive: Boolean = true,
    buttonColor: Color = MainPurple900,
    textColor: Color = Color.White,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier.height(52.dp),
        shape = RoundedCornerShape(10.dp),
        onClick = onClick,
        colors =
            if (isPositive) ButtonDefaults.buttonColors(buttonColor)
            else ButtonDefaults.buttonColors(Gray200),
        elevation = null,
    ) {
        Text(
            text = text,
            color = if (isPositive) textColor else Gray500,
            style = PlanzTypography.button
        )
    }
}

@Preview
@Composable
fun PreviewPlanzDialog() {
    PlanzDialog(
        title = "프리뷰",
        content = "프리뷰입니다.",
        positiveButtonText = "확인",
        negativeButtonText = "취소",
        onCancelButtonClick = {},
        onPositiveButtonClick = {},
        onNegativeButtonClick = {}
    )
}

@Preview
@Composable
fun PreviewDefaultAlertDialog() {
    PlanzAlertDialog(
        title = "알림",
        content = "작업한 내용이 저장되지 않고 홈화면으로\n" +
                "이동합니다. 진행하시겠습니까?",
        positiveButtonText = "확인",
        negativeButtonText = "취소",
        onClickNegativeButton = {},
        onClickPositiveButton = {},
    )
}
