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
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.component.type.PlanzAppBarMenu
import com.yapp.growth.presentation.theme.Gray900
import com.yapp.growth.presentation.theme.MainPurple900
import com.yapp.growth.presentation.theme.PlanzTypography


@Composable
fun PlanzBackAndClearAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onClickBackIcon: () -> Unit,
    onClickShareIcon: () -> Unit,
    textIconTitle: String,
    textIconColor: Color,
    onClickClearText: () -> Unit,
    clickable: Boolean,
) {
    PlanzIconAndTextAppBar(
        title = title,
        menu = PlanzAppBarMenu.BACK,
        onClickNavigationIcon = onClickBackIcon,
        actionMenu = PlanzAppBarMenu.SHARE,
        onClickActionIcon = onClickShareIcon,
        textIconTitle = textIconTitle,
        textIconColor = if (clickable) MainPurple900 else textIconColor,
        onClickText = onClickClearText
    )
}

@Composable
private fun PlanzIconAndTextAppBar(
    modifier: Modifier = Modifier,
    title: String,
    menu: PlanzAppBarMenu,
    onClickNavigationIcon: () -> Unit,
    actionMenu: PlanzAppBarMenu,
    onClickActionIcon: () -> Unit,
    textIconTitle: String,
    textIconColor: Color,
    onClickText: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 20.dp, bottom = 16.dp)
    ) {

        Icon(
            imageVector = ImageVector.vectorResource(id = menu.icon),
            tint = Color.Unspecified,
            contentDescription = stringResource(id = menu.contentDescription),
            modifier = Modifier
                .padding(start = menu.horizontalPadding)
                .clip(RoundedCornerShape(30.dp))
                .clickable { onClickNavigationIcon() }
                .align(Alignment.CenterStart),
        )

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

        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Icon(
                modifier = Modifier
                    .clip(RoundedCornerShape(30.dp))
                    .clickable { onClickActionIcon() },
                imageVector = ImageVector.vectorResource(actionMenu.icon),
                tint = Color.Unspecified,
                contentDescription = stringResource(menu.contentDescription),
            )

            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickable { onClickText() },
                text = textIconTitle,
                style = PlanzTypography.caption,
                color = textIconColor,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlanzIconAndTextAppBarPreview() {
    PlanzIconAndTextAppBar(
        title = "약속응답",
        menu = PlanzAppBarMenu.BACK,
        onClickNavigationIcon = {},
        onClickActionIcon = {},
        actionMenu = PlanzAppBarMenu.USER,
        textIconTitle = stringResource(id = R.string.respond_plan_clear_select_text),
        textIconColor = MainPurple900,
        onClickText = {}
    )
}
