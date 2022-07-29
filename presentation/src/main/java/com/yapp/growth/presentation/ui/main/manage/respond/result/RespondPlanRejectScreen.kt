package com.yapp.growth.presentation.ui.main.manage.respond.result

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.component.PlanzBasicButton
import com.yapp.growth.presentation.theme.Gray900
import com.yapp.growth.presentation.theme.PlanzTypography

@Composable
fun RespondPlanRejectScreen(
    userName: String,
    navigateToPreviousScreen: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 70.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(start = 20.dp)
        ) {
            Text(
                text = userName, style = PlanzTypography.h2, color = Gray900
            )

            Text(
                text = stringResource(id = R.string.respond_plan_reject_title_text2),
                style = PlanzTypography.h2,
                color = Gray900
            )
        }

        Column(
            Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier.wrapContentWidth(),
                painter = painterResource(id = R.drawable.ic_respond_plan_reject),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )

            Spacer(modifier = Modifier.height(21.dp))

            Text(
                text = stringResource(id = R.string.respond_plan_reject_info_text),
                style = PlanzTypography.body1,
                color = Gray900
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 32.dp)
        ) {
            PlanzBasicButton(modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.respond_plan_reject_button_text),
                onClick = navigateToPreviousScreen
            )
        }
    }
}

@Preview
@Composable
fun PreviewRespondPlanRejectScreen() {
    RespondPlanRejectScreen(
        userName = "대원",
        navigateToPreviousScreen = { }
    )
}
