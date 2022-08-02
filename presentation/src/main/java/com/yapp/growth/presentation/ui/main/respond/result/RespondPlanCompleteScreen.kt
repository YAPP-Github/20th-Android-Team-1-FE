package com.yapp.growth.presentation.ui.main.respond.result

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.component.PlanzBasicButton
import com.yapp.growth.presentation.theme.Gray900
import com.yapp.growth.presentation.theme.MainPurple900
import com.yapp.growth.presentation.theme.PlanzTypography

@Composable
fun RespondPlanCompleteScreen(
    navigateToPreviousScreen: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 25.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = stringResource(id = R.string.respond_plan_complete_title_text1),
                style = PlanzTypography.h2,
                color = Gray900
            )

            Row(modifier = Modifier.padding(start = 20.dp)) {
                Text(
                    text = stringResource(id = R.string.respond_plan_complete_title_text2),
                    style = PlanzTypography.h2,
                    color = Gray900
                )

                Text(
                    text = stringResource(id = R.string.respond_plan_complete_title_text3),
                    style = PlanzTypography.h2,
                    color = MainPurple900
                )

                Text(
                    text = "!", style = PlanzTypography.h2, color = Gray900
                )
            }

            Image(
                modifier = Modifier.weight(1f),
                painter = painterResource(id = R.drawable.icon_respond_plan_complete),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 32.dp)
        ) {
            PlanzBasicButton(modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.respond_common_button_text),
                onClick = navigateToPreviousScreen
            )
        }
    }
}

@Preview
@Composable
fun PreviewRespondPlanCompleteScreen() {
    RespondPlanCompleteScreen(
        navigateToPreviousScreen = { }
    )
}
