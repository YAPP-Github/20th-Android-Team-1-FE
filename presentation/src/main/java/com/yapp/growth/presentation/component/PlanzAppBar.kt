package com.yapp.growth.presentation.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.theme.Gray900
import com.yapp.growth.presentation.theme.PlanzTypography

@Composable
fun PlanzCreateAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onCreateClick: () -> Unit,
) {
    PlanzAppBar(
        modifier = modifier,
        title = title,
        menu = PlanzAppBarMenu.CREATE,
        onClick = onCreateClick,
    )
}

@Composable
fun PlanzExitAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onExitClick: () -> Unit,
) {
    PlanzAppBar(
        modifier = modifier,
        title = title,
        menu = PlanzAppBarMenu.EXIT,
        onClick = onExitClick,
    )
}

@Composable
fun PlanzBackAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onBackClick: () -> Unit,
) {
    PlanzAppBar(
        modifier = modifier,
        title = title,
        menu = PlanzAppBarMenu.Back,
        onClick = onBackClick,
    )
}

@Composable
private fun PlanzAppBar(
    modifier: Modifier = Modifier,
    title: String,
    menu: PlanzAppBarMenu,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = menu.horizontalPadding)
                .align(Alignment.Center),
            textAlign = TextAlign.Center,
            style = PlanzTypography.h3,
            color = Gray900,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Icon(
            imageVector = ImageVector.vectorResource(id = menu.icon),
            tint = Color.Unspecified,
            contentDescription = stringResource(id = menu.contentDescription),
            modifier = Modifier
                .padding(end = menu.horizontalPadding)
                .clip(RoundedCornerShape(30.dp))
                .clickable { onClick() }
                .align(Alignment.CenterEnd),
        )
    }
}

enum class PlanzAppBarMenu(
    val horizontalPadding: Dp,
    @DrawableRes val icon: Int,
    @StringRes val contentDescription: Int,
) {
    CREATE(
        horizontalPadding = 16.dp,
        icon = R.drawable.ic_create,
        contentDescription = R.string.icon_create_content_description
    ),
    EXIT(
        horizontalPadding = 20.dp,
        icon = R.drawable.ic_exit,
        contentDescription = R.string.icon_exit_content_description
    ),
    Back(
        horizontalPadding = 20.dp,
        icon = R.drawable.ic_arrow_left_20,
        contentDescription = R.string.icon_arrow_left_content_description
    )
}

@Preview(showBackground = true)
@Composable
fun PlanzCreateAppBarPreview() {
    PlanzCreateAppBar(
        title = "약속 관리",
        onCreateClick = {},
    )
}

@Preview(showBackground = true)
@Composable
fun PlanzExitAppBarPreview() {
    PlanzExitAppBar(
        title = "약속 잡기",
        onExitClick = {},
    )
}
