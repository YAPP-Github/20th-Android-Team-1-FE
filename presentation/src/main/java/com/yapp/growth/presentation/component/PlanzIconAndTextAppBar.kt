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
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.theme.Gray500
import com.yapp.growth.presentation.theme.Gray900
import com.yapp.growth.presentation.theme.PlanzTypography


@Composable
fun PlanzBackAndClearAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onClickBackIcon: () -> Unit,
    textIconTitle: String,
    onClickClearIcon: () -> Unit,
) {
    PlanzIconAndTextAppBar(
        title = title,
        menu = PlanzAppBarMenu.BACK,
        onClickIcon = onClickBackIcon,
        textIconTitle = textIconTitle,
        onclickTextIcon = onClickClearIcon
    )
}

@Composable
private fun PlanzIconAndTextAppBar(
    modifier: Modifier = Modifier,
    title: String,
    menu: PlanzAppBarMenu,
    onClickIcon: () -> Unit,
    textIconTitle: String,
    onclickTextIcon: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(76.dp)
            .padding(top = 20.dp)
    ) {

        Icon(
            imageVector = ImageVector.vectorResource(id = menu.icon),
            tint = Color.Unspecified,
            contentDescription = stringResource(id = menu.contentDescription),
            modifier = Modifier
                .padding(start = menu.horizontalPadding)
                .clip(RoundedCornerShape(30.dp))
                .clickable { onClickIcon() }
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

        Text(
            text = textIconTitle,
            style = PlanzTypography.caption,
            color = Gray500,
            modifier = Modifier
                .padding(end = 20.dp)
                .align(Alignment.CenterEnd)
                .clickable { onclickTextIcon() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PlanzIconAndTextAppBarPreview() {
    PlanzIconAndTextAppBar(
        title = "약속응답",
        menu = PlanzAppBarMenu.BACK,
        onClickIcon = {},
        textIconTitle = stringResource(id = R.string.respond_plan_clear_select_text),
        onclickTextIcon = {}
    )
}
