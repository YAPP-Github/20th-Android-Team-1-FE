package com.yapp.growth.presentation.ui.main.myPage.nickname

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.growth.presentation.component.PlanzExitAppBar
import com.yapp.growth.presentation.component.PlanzTextField
import com.yapp.growth.presentation.ui.main.myPage.nickname.ModifyNickNameContract.*
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.component.PlanzBasicButton

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ModifyNickNameScreen(
    viewModel: ModifyNickNameViewModel = hiltViewModel(),
    navigateToPreviousScreen: () -> Unit,
) {
    val uiState by viewModel.viewState.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            PlanzExitAppBar(
                title = "닉네임 변경",
                onExitClick = navigateToPreviousScreen
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(padding)
        ) {
            Column(
                modifier = Modifier.padding(top = 44.dp),
                verticalArrangement = Arrangement.spacedBy(26.dp)
            ) {
                PlanzTextField(
                    label = "닉네임",
                    maxLength = 5,
                    text = uiState.nickName,
                    hint = uiState.nickNameHint.plus(stringResource(id = R.string.my_page_modify_nickname_hint_text)),
                    onInputChanged = { viewModel.setEvent(ModifyNickNameEvent.FillInNickName(it)) },
                    onDeleteClicked = { viewModel.setEvent(ModifyNickNameEvent.ClearNickName) },
                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 16.dp)
            ) {
                PlanzBasicButton(
                    text = stringResource(R.string.my_page_modify_nickname_button_text),
                    enabled = !uiState.isError,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp),
                    onClick = { viewModel.setEvent(ModifyNickNameEvent.OnClickModifyButton) }
                )
            }
        }
    }

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                ModifyNickNameSideEffect.NavigateToPreviousScreen -> navigateToPreviousScreen()
            }
        }
    }
}

@Preview
@Composable
fun PreviewModifyNickNameScreen() {
    ModifyNickNameScreen(
        navigateToPreviousScreen = { },
    )
}
