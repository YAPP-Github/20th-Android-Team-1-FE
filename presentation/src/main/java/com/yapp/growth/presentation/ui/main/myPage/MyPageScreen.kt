package com.yapp.growth.presentation.ui.main.myPage

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.growth.base.LoadState
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.component.PlanzBackAppBar
import com.yapp.growth.presentation.component.PlanzDialog
import com.yapp.growth.presentation.component.PlanzError
import com.yapp.growth.presentation.component.PlanzLoading
import com.yapp.growth.presentation.theme.*
import com.yapp.growth.presentation.ui.login.LoginActivity
import com.yapp.growth.presentation.ui.main.MainContract
import com.yapp.growth.presentation.ui.main.MainViewModel
import com.yapp.growth.presentation.ui.main.myPage.MyPageContract.*
import com.yapp.growth.presentation.util.composableActivityViewModel

@Composable
fun MyPageScreen(
    mainViewModel: MainViewModel = composableActivityViewModel(),
    viewModel: MyPageViewModel = hiltViewModel(),
    navigateToPolicyScreen: () -> Unit,
    navigateToTermsScreen: () -> Unit,
    navigateToModifyNickNameScreen: () -> Unit,
    exitMyPageScreen: () -> Unit,
) {

    val viewState by viewModel.viewState.collectAsState()
    val context = LocalContext.current as Activity

    LaunchedEffect(key1 = true) {
        viewModel.setEvent(MyPageEvent.InitMyPageScreen)
    }

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MyPageSideEffect.MoveToLogin -> {
                    LoginActivity.startActivity(context, null)
                }
                is MyPageSideEffect.ExitMyPageScreen -> {
                    exitMyPageScreen()
                }
                is MyPageSideEffect.NavigateToPolicy -> {
                    navigateToPolicyScreen()
                }
                is MyPageSideEffect.NavigateToTerms -> {
                    navigateToTermsScreen()
                }
                is MyPageSideEffect.ShowToast -> {
                    Toast.makeText(context, effect.msg, Toast.LENGTH_SHORT).show()
                }
                MyPageSideEffect.ModifyNickName -> {
                    navigateToModifyNickNameScreen()
                }
            }
        }
    }

    LaunchedEffect(key1 = mainViewModel.effect) {
        mainViewModel.effect.collect { effect ->
            when (effect) {
                is MainContract.MainSideEffect.RefreshScreen -> {
                    viewModel.setEvent(MyPageEvent.InitMyPageScreen)
                }
            }
        }
    }

    BackHandler(enabled = viewState.isDialogVisible) {
        viewModel.setEvent(MyPageEvent.OnNegativeButtonClicked)
    }

    when (viewState.loadState) {
        LoadState.SUCCESS -> {
            Scaffold(
                topBar = {
                    PlanzBackAppBar(
                        modifier = Modifier.background(color = BackgroundColor1),
                        title = stringResource(id = R.string.my_page_text),
                        onBackClick = { viewModel.setEvent(MyPageEvent.OnBackButtonClicked) },
                    )
                }
            ) { padding ->
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    when (viewState.loginState) {
                        LoginState.LOGIN -> MyPageUserInfo(
                            userName = viewState.userName,
                            OnClickModifyNickname = {
                                viewModel.setEvent(MyPageEvent.OnClickModifyNickname)
                            },
                        )
                        LoginState.NONE -> MyPageSignUp(
                            onSingUpClick = { viewModel.setEvent(MyPageEvent.OnSignUpClicked) },
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    MyPageCustomerService(
                        context = context,
                        onPolicyClicked = { viewModel.setEvent(MyPageEvent.OnPolicyClicked) },
                        onTermsClicked = { viewModel.setEvent(MyPageEvent.OnTermsClicked) },
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    if (viewState.loginState == LoginState.LOGIN) {
                        MyPageAccountManagement(
                            onLogoutClick = { viewModel.setEvent(MyPageEvent.OnLogoutClicked) },
                            onWithDrawClick = { viewModel.setEvent(MyPageEvent.OnWithDrawClicked) }
                        )
                    }
                }
            }

            if (viewState.isDialogVisible) {
                PlanzDialog(
                    title = stringResource(id = R.string.my_page_dialog_title_text),
                    content = stringResource(id = R.string.my_page_dialog_content_text),
                    positiveButtonText = stringResource(id = R.string.my_page_dialog_positive_button_text),
                    negativeButtonText = stringResource(id = R.string.my_page_dialog_negative_button_text),
                    onCancelButtonClick = { viewModel.setEvent(MyPageEvent.OnPositiveButtonClicked) },
                    onPositiveButtonClick = { viewModel.setEvent(MyPageEvent.OnPositiveButtonClicked) },
                    onNegativeButtonClick = { viewModel.setEvent(MyPageEvent.OnNegativeButtonClicked) }
                )
            }
        }
        LoadState.LOADING -> {
            Surface(modifier = Modifier.fillMaxSize()) {
                PlanzLoading()
            }
        }
        LoadState.ERROR -> {
            Surface(modifier = Modifier.fillMaxSize()) {
                PlanzError()
            }
        }
    }

}

@Composable
fun MyPageSignUp(
    onSingUpClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = BackgroundColor1)
            .padding(top = 16.dp, start = 20.dp, end = 20.dp, bottom = 25.dp)
            .clickable {
                onSingUpClick()
            },
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {

        TextButton(
            modifier = Modifier.wrapContentSize(),
            onClick = onSingUpClick,
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.Transparent,
                contentColor = MainPurple900
            ),
            contentPadding = PaddingValues(0.dp),
        ) {
            Text(
                modifier = Modifier.clickable { onSingUpClick() },
                text = stringResource(id = R.string.my_page_planz_sign_up_text),
                color = MainPurple900,
                style = PlanzTypography.h2,
            )

            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                modifier = Modifier.align(Alignment.CenterVertically),
                tint = Color.Unspecified,
                imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_blue_right),
                contentDescription = null,
            )
        }

        Text(
            text = stringResource(id = R.string.my_page_induce_sign_up_text),
            color = Gray500,
            style = PlanzTypography.body2,
        )
    }
}

@Composable
fun MyPageUserInfo(
    userName: String,
    OnClickModifyNickname: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = BackgroundColor1)
            .padding(top = 16.dp, start = 20.dp, end = 20.dp, bottom = 25.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = userName,
                    color = Gray900,
                    style = PlanzTypography.h2
                )

                Icon(
                    modifier = Modifier.clickable { OnClickModifyNickname() },
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_nickname_modify),
                    contentDescription = null,
                )
            }

            Text(
                text = stringResource(id = R.string.my_page_login_info_text),
                color = Gray500,
                style = PlanzTypography.body2
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Image(
            modifier = Modifier.size(54.dp),
            painter = painterResource(R.drawable.ic_default_user_image_54),
            contentDescription = null,
        )
    }
}

@Composable
fun MyPageCustomerService(
    context: Context,
    onTermsClicked: () -> Unit,
    onPolicyClicked: () -> Unit,
) {
    val versionName = context.packageManager.getPackageInfo(context.packageName, 0).versionName

    Column {
        MyPageItemHeader(content = stringResource(id = R.string.my_page_customer_service_text))
        Spacer(modifier = Modifier.height(12.dp))
        MyPageItem(
            content = stringResource(id = R.string.my_page_terms_text),
            onClick = onTermsClicked
        )
        MyPageItem(
            content = stringResource(id = R.string.my_page_privacy_policy_text),
            onClick = onPolicyClicked
        )
        MyPageItem(
            content = stringResource(id = R.string.my_page_version_info_text) + " $versionName",
            onClick = { /* Nothing */ }
        )
    }
}

@Composable
fun MyPageAccountManagement(
    onLogoutClick: () -> Unit,
    onWithDrawClick: () -> Unit
) {
    Column {
        MyPageItemHeader(content = stringResource(id = R.string.my_page_account_management_text))
        Spacer(modifier = Modifier.height(12.dp))
        MyPageItem(
            content = stringResource(id = R.string.my_page_logout_text),
            onClick = { onLogoutClick() }
        )
        MyPageItem(
            content = stringResource(id = R.string.my_page_withdraw_text),
            onClick = { onWithDrawClick() }
        )
    }
}

@Composable
fun MyPageItemHeader(content: String) {
    Text(
        text = content,
        color = Gray700,
        style = PlanzTypography.subtitle2,
        modifier = Modifier.padding(horizontal = 20.dp)
    )
}

@Composable
fun MyPageItem(
    content: String,
    onClick: () -> Unit,
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(48.dp)
        .then(
            if (content.contains(stringResource(id = R.string.my_page_version_info_text))) Modifier
            else Modifier.clickable { onClick() }
        )
    ) {
        Text(
            text = content,
            color = Gray900,
            style = PlanzTypography.subtitle1,
            modifier = Modifier
                .align(alignment = Alignment.CenterStart)
                .padding(horizontal = 20.dp)
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun PreviewMyPageScreen() {
    PlanzTheme {
        MyPageScreen(
            navigateToPolicyScreen = { },
            navigateToTermsScreen = { },
            navigateToModifyNickNameScreen = { },
            exitMyPageScreen = { },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMyPageUserInfo() {
    PlanzTheme {
        MyPageUserInfo(
            userName = "김정호",
            OnClickModifyNickname = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMyPageInduceLogin() {
    PlanzTheme {
        MyPageSignUp(
            onSingUpClick = { }
        )
    }
}
