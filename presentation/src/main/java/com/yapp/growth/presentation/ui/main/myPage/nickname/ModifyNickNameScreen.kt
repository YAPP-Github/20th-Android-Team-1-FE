package com.yapp.growth.presentation.ui.main.myPage.nickname

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.component.PlanzExitAppBar
import com.yapp.growth.presentation.component.PlanzTextField
import com.yapp.growth.presentation.ui.createPlan.title.MAX_LENGTH_TITLE
import com.yapp.growth.presentation.ui.createPlan.title.TitleContract

@Composable
fun ModifyNickNameScreen(
    
) {

    Scaffold(
        topBar = {
            PlanzExitAppBar(title = "이름", onExitClick = { })
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column(
                modifier = Modifier.padding(top = 44.dp),
                verticalArrangement = Arrangement.spacedBy(26.dp)
            ) {
//                PlanzTextField(
//                    label = "닉네임",
//                    maxLength = 5,
//                    text = viewState.title,
//                    onInputChanged = { viewModel.setEvent(TitleContract.TitleEvent.FillInTitle(it)) },
//                    onDeleteClicked = { viewModel.setEvent(TitleContract.TitleEvent.FillInTitle("")) }
//                )
            }

        }
    }
}

@Preview
@Composable
fun PreviewModifyNickNameScreen() {
    ModifyNickNameScreen()
}