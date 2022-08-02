package com.yapp.growth.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.theme.Gray500
import com.yapp.growth.presentation.theme.MainPurple900
import com.yapp.growth.presentation.theme.PlanzTypography


@Composable
fun PlanzError(
    modifier: Modifier = Modifier.fillMaxSize(),
    retryVisible: Boolean = false,
    onClickRetry: () -> Unit = { },
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.ic_failed_character_53),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = R.string.planz_error_text_01),
                color = Gray500,
                style = PlanzTypography.body2,
            )

            if (retryVisible) {
                Spacer(modifier = Modifier.height(28.dp))
                Button(
                    modifier = Modifier
                        .width(97.dp)
                        .height(34.dp),
                    border = BorderStroke(width = 1.dp, color = MainPurple900),
                    shape = RoundedCornerShape(7.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MainPurple900),
                    onClick = { onClickRetry() }
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Icon(
                           painter = painterResource(id = R.drawable.ic_refresh_16),
                           contentDescription = null,
                        )

                        Text(
                            text = stringResource(R.string.planz_error_retry_button_title),
                            style = PlanzTypography.caption,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewPlanError() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        PlanzError(retryVisible = true, onClickRetry = { })
    }
}