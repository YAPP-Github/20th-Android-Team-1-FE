package com.yapp.growth.presentation.component.type

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yapp.growth.presentation.R

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
    USER(
        horizontalPadding = 20.dp,
        icon = R.drawable.ic_face,
        contentDescription = R.string.icon_user_content_description
    ),
    BACK(
        horizontalPadding = 20.dp,
        icon = R.drawable.ic_arrow_left_20,
        contentDescription = R.string.icon_arrow_left_content_description
    ),
}
