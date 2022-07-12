package com.yapp.growth.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.theme.Gray900
import com.yapp.growth.presentation.theme.MainPurple900
import com.yapp.growth.presentation.theme.PlanzTypography
import com.yapp.growth.presentation.theme.SubCoral

@Composable
fun PlanzSnackBar(
    message: String,
) {
    Snackbar(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 20.dp),
        backgroundColor = Gray900
    ) {
        Text(
            modifier = Modifier.padding(vertical = 12.dp),
            text = message,
            style = PlanzTypography.caption,
            color = MainPurple900
        )
    }
}

@Composable
fun PlanzErrorSnackBar(
    message: String,
) {
    Snackbar(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 20.dp),
        backgroundColor = Gray900
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_error),
                contentDescription = stringResource(id = R.string.icon_error_content_description),
                tint = Color.Unspecified,
            )
            Text(
                text = message,
                style = PlanzTypography.caption,
                color = SubCoral
            )
        }
    }
}
