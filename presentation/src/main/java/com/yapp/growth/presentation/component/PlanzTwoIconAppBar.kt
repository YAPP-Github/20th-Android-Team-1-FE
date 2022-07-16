package com.yapp.growth.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.growth.presentation.theme.Gray900
import com.yapp.growth.presentation.theme.PlanzTypography

@Composable
fun PlanzBackAndShareAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onClickBackIcon: () -> Unit,
    onClickShareIcon: () -> Unit
) {
    PlanzTwoIconAppBar(
        modifier = modifier,
        title = title,
        leftMenu = PlanzAppBarMenu.BACK,
        rightMenu = PlanzAppBarMenu.SHARE,
        onClickLeftIcon = onClickBackIcon,
        onClickRightIcon = onClickShareIcon
    )
}

@Composable
private fun PlanzTwoIconAppBar(
    modifier: Modifier = Modifier,
    title: String,
    leftMenu: PlanzAppBarMenu,
    rightMenu: PlanzAppBarMenu,
    onClickLeftIcon: () -> Unit,
    onClickRightIcon: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {

        Icon(
            imageVector = ImageVector.vectorResource(id = leftMenu.icon),
            tint = Color.Unspecified,
            contentDescription = stringResource(id = leftMenu.contentDescription),
            modifier = Modifier
                .padding(start = leftMenu.horizontalPadding)
                .clip(RoundedCornerShape(30.dp))
                .clickable { onClickLeftIcon() }
                .align(Alignment.CenterStart),
        )

        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            textAlign = TextAlign.Center,
            style = PlanzTypography.h3,
            color = Gray900,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Icon(
            imageVector = ImageVector.vectorResource(id = rightMenu.icon),
            tint = Color.Unspecified,
            contentDescription = stringResource(id = rightMenu.contentDescription),
            modifier = Modifier
                .padding(end = rightMenu.horizontalPadding)
                .clip(RoundedCornerShape(30.dp))
                .clickable { onClickRightIcon() }
                .align(Alignment.CenterEnd),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PlanzBackAppBarPreview() {
    PlanzTwoIconAppBar(
        title = "약속확정",
        leftMenu = PlanzAppBarMenu.BACK,
        rightMenu = PlanzAppBarMenu.SHARE,
        onClickLeftIcon = {},
        onClickRightIcon = {}
    )
}
