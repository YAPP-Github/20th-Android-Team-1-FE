package com.yapp.growth.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yapp.growth.domain.entity.User
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.theme.Gray700
import com.yapp.growth.presentation.theme.Gray800
import com.yapp.growth.presentation.theme.PlanzTypography

@Composable
fun PlanzRespondentBottomSheetContent(
    respondents: List<User>,
) {
    val respondentText = StringBuilder()
    respondents.forEachIndexed { index, user ->
        when (index) {
            0, 3, 7 -> respondentText.append(user.userName)
            2, 6 -> respondentText.append(", ${user.userName.plus("\n")}")
            else -> respondentText.append(", ${user.userName}")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 20.dp)
            .padding(top = 4.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(id = R.string.planz_component_respondent_bottom_sheet_content_title),
            style = PlanzTypography.subtitle2,
            color = Gray700
        )
        Text(
            text = respondentText.toString(),
            style = PlanzTypography.caption,
            color = Gray800
        )
    }
}
