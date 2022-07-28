package com.yapp.growth.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.theme.CoolGray500
import com.yapp.growth.presentation.theme.Gray500
import com.yapp.growth.presentation.theme.PlanzTypography

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