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
        back = null,
        menu = PlanzAppBarMenu.CREATE,
        onMenuClick = onCreateClick,
        onBackClick = { },
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
        onMenuClick = onExitClick,
        onBackClick = { },
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
        back = PlanzAppBarMenu.BACK,
        onMenuClick = { },
        onBackClick = onBackClick,
    )
}

@Composable
private fun PlanzAppBar(
    modifier: Modifier = Modifier,
    title: String,
    back: PlanzAppBarMenu? = null,
    menu: PlanzAppBarMenu? = null,
    onMenuClick: () -> Unit,
    onBackClick: () -> Unit,
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
                .align(Alignment.Center),
            textAlign = TextAlign.Center,
            style = PlanzTypography.h3,
            color = Gray900,
            maxLines = 1,
        )

        if(back != null) {
            Icon(
                imageVector = ImageVector.vectorResource(id = back.icon),
                tint = Color.Unspecified,
                contentDescription = stringResource(id = back.contentDescription),
                modifier = Modifier
                    .padding(start = back.horizontalPadding)
                    .clip(RoundedCornerShape(30.dp))
                    .clickable { onBackClick() }
                    .align(Alignment.CenterStart),
            )
        }
        if(menu != null) {
            Icon(
                imageVector = ImageVector.vectorResource(id = menu.icon),
                tint = Color.Unspecified,
                contentDescription = stringResource(id = menu.contentDescription),
                modifier = Modifier
                    .padding(end = menu.horizontalPadding)
                    .clip(RoundedCornerShape(30.dp))
                    .clickable { onMenuClick() }
                    .align(Alignment.CenterEnd),
            )
        }
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
    SHARE(
        horizontalPadding = 20.dp,
        icon = R.drawable.ic_share_box_24,
        contentDescription = R.string.icon_share_content_description
    ),

    BACK(
        horizontalPadding = 20.dp,
        icon = R.drawable.ic_arrow_left_20,
        contentDescription = R.string.icon_arrow_left_content_description
    ),
}

@Preview(showBackground = true)
@Composable
fun PlanzCreateAppBarPreview() {
    PlanzCreateAppBar(
        title = "?????? ??????",
        onCreateClick = {},
    )
}

@Preview(showBackground = true)
@Composable
fun PlanzExitAppBarPreview() {
    PlanzExitAppBar(
        title = "?????? ??????",
        onExitClick = {},
    )
}
