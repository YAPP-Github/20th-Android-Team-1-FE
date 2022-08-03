package com.yapp.growth.presentation.component

import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer
import com.yapp.growth.presentation.component.type.PlanzAppBarMenu
import com.yapp.growth.presentation.theme.*

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
fun PlanzColorTextWithExitAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onClickExitIcon: () -> Unit,
    onClickShareIcon: () -> Unit,
    isLoading: Boolean,
) {
    PlanzColorTextAppBar(
        modifier = modifier,
        title = title,
        actionMenus = listOf(PlanzAppBarMenu.SHARE, PlanzAppBarMenu.EXIT),
        onClickActionIcons = listOf(onClickShareIcon, onClickExitIcon),
        isLoading = isLoading
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
            .height(60.dp)
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

        if (back != null) {
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
        if (menu != null) {
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

@Composable
private fun PlanzColorTextAppBar(
    modifier: Modifier = Modifier,
    title: String,
    actionMenus: List<PlanzAppBarMenu>,
    onClickActionIcons: List<() -> Unit>,
    isLoading: Boolean = false,
) {
    if (isLoading) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 20.dp, bottom = 15.dp, start = 20.dp, end = 20.dp)
                .shimmer()
        ) {
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(22.dp)
                    .background(Gray200)
            )
        }
    } else {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 20.dp, bottom = 15.dp, start = 20.dp, end = 20.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding()
                    .align(Alignment.CenterStart),
                text = title,
                style = PlanzTypography.h2,
                color = Gray900,
                maxLines = 1,
            )

            Row(
                modifier = Modifier.align(Alignment.CenterEnd),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                actionMenus.forEachIndexed { index, menu ->
                    Icon(
                        modifier = Modifier
                            .clip(RoundedCornerShape(30.dp))
                            .clickable { onClickActionIcons[index]() },
                        imageVector = ImageVector.vectorResource(menu.icon),
                        tint = Color.Unspecified,
                        contentDescription = stringResource(menu.contentDescription)
                    )
                }
            }
        }
    }
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

@Preview(showBackground = true)
@Composable
fun PreviewPlanzColorTextWithExitAppBar() {
    PlanzColorTextWithExitAppBar(
        title = "식사",
        onClickShareIcon = {},
        onClickExitIcon = {},
        isLoading = false
    )
}
