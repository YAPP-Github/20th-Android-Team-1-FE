package com.yapp.growth.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.yapp.growth.presentation.component.type.PlanzAppBarMenu
import com.yapp.growth.presentation.theme.Gray900
import com.yapp.growth.presentation.theme.PlanzTypography

@Composable
fun PlanzUserAndShareAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onClickBackIcon: () -> Unit,
    onClickUserIcon: () -> Unit,
    onClickShareIcon: () -> Unit,
) {
    PlanzMenuAppBar(
        modifier = modifier,
        title = title,
        navigationMenu = PlanzAppBarMenu.BACK,
        actionMenus = listOf(PlanzAppBarMenu.USER, PlanzAppBarMenu.SHARE),
        onClickNavigationIcon = onClickBackIcon,
        onClickActionIcons = listOf(onClickUserIcon, onClickShareIcon),
    )
}

@Composable
fun PlanzMenuAppBar(
    modifier: Modifier = Modifier,
    title: String,
    navigationMenu: PlanzAppBarMenu? = null,
    actionMenus: List<PlanzAppBarMenu>,
    onClickNavigationIcon: (() -> Unit)? = null,
    onClickActionIcons: List<() -> Unit>,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 20.dp, bottom = 16.dp)
            .padding(horizontal = 20.dp)
    ) {

        if (navigationMenu != null && onClickNavigationIcon != null) {
            Icon(
                imageVector = ImageVector.vectorResource(id = navigationMenu.icon),
                tint = Color.Unspecified,
                contentDescription = stringResource(id = navigationMenu.contentDescription),
                modifier = Modifier
                    .clip(RoundedCornerShape(30.dp))
                    .clickable { onClickNavigationIcon() }
                    .align(Alignment.CenterStart),
            )
        }

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

        Row(
            modifier = Modifier.align(Alignment.CenterEnd),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            actionMenus.forEachIndexed { index, menu ->
                Icon(
                    imageVector = ImageVector.vectorResource(id = menu.icon),
                    tint = Color.Unspecified,
                    contentDescription = stringResource(id = menu.contentDescription),
                    modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .clickable { onClickActionIcons[index]() },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlanzBackAppBarPreview() {
    PlanzMenuAppBar(
        title = "약속확정",
        navigationMenu = PlanzAppBarMenu.BACK,
        actionMenus = listOf(PlanzAppBarMenu.SHARE, PlanzAppBarMenu.USER),
        onClickNavigationIcon = {},
        onClickActionIcons = listOf({}, {})
    )
}
