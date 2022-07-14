package com.yapp.growth.presentation.ui.main.myPage

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
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
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.component.PlanzBackAppBar
import com.yapp.growth.presentation.theme.BackgroundColor1
import com.yapp.growth.presentation.theme.Gray500
import com.yapp.growth.presentation.theme.Gray700
import com.yapp.growth.presentation.theme.Gray900
import com.yapp.growth.presentation.theme.MainPurple900
import com.yapp.growth.presentation.theme.PlanzTheme
import com.yapp.growth.presentation.theme.PlanzTypography
import com.yapp.growth.presentation.ui.login.LoginActivity
import com.yapp.growth.presentation.ui.main.myPage.MyPageContract.LoginState
import com.yapp.growth.presentation.ui.main.myPage.MyPageContract.MyPageEvent
import com.yapp.growth.presentation.ui.main.myPage.MyPageContract.MyPageSideEffect

@Composable
fun MyPageScreen(
    viewModel: MyPageViewModel = hiltViewModel(),
    exitMyPageScreen: () -> Unit,
) {

    val viewState by viewModel.viewState.collectAsState()
    val context = LocalContext.current as Activity

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MyPageSideEffect.MoveToLogin -> {
                    LoginActivity.startActivity(context)
                    context.finish()
                }
                is MyPageSideEffect.ExitMyPageScreen -> {
                    exitMyPageScreen()
                }
            }
        }
    }

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
                // TODO : UserName 을 SharedPreferences 로 관리할 것인지 . . .?
                LoginState.LOGIN -> MyPageUserInfo(viewState.userName)
                LoginState.NONE -> MyPageInduceLogin(
                    onSingUpClick = { viewModel.setEvent(MyPageEvent.OnSignUpClicked) },
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            MyPageCustomerService(context = context)
            Spacer(modifier = Modifier.height(24.dp))
            if (viewState.loginState == LoginState.LOGIN) {
                MyPageAccountManagement(
                    onLogoutClick = { viewModel.setEvent(MyPageEvent.OnLogoutClicked) },
                )
            }
        }
    }
}

@Composable
fun MyPageInduceLogin(
    onSingUpClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = BackgroundColor1)
            .padding(start = 20.dp, end = 20.dp, bottom = 25.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(id = R.string.my_page_planz_sign_up_text),
                color = MainPurple900,
                style = PlanzTypography.h2,
            )
            Spacer(modifier = Modifier.width(2.dp))
            Box(modifier = Modifier
                .size(24.dp)
                .clip(RoundedCornerShape(30.dp))
                .clickable { onSingUpClick() }) {
                Icon(
                    modifier = Modifier.align(Alignment.Center),
                    tint = Color.Unspecified,
                    imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_blue_right),
                    contentDescription = null,
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(id = R.string.my_page_induce_sign_up_text),
            color = Gray500,
            style = PlanzTypography.body2,
        )
    }
}

@Composable
fun MyPageUserInfo(
    userName: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = BackgroundColor1)
            .padding(start = 20.dp, end = 20.dp, bottom = 25.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Text(
                text = userName,
                color = Gray900,
                style = PlanzTypography.h2
            )
            Spacer(modifier = Modifier.height(4.dp))
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
    context: Context
) {
    val versionName = context.packageManager.getPackageInfo(context.packageName, 0).versionName

    Column {
        MyPageItemHeader(content = stringResource(id = R.string.my_page_customer_service_text))
        Spacer(modifier = Modifier.height(12.dp))
        MyPageItem(
            content = stringResource(id = R.string.my_page_terms_text),
            onClick = { }
        )
        MyPageItem(
            content = stringResource(id = R.string.my_page_privacy_policy_text),
            onClick = { }
        )
        MyPageItem(
            content = stringResource(id = R.string.my_page_version_info_text) + " $versionName",
            onClick = { /* Nothing */ }
        )
    }
}

@Composable
fun MyPageAccountManagement(
    onLogoutClick : () -> Unit
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
            onClick = { }
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
        MyPageScreen(exitMyPageScreen = { })
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMyPageUserInfo() {
    PlanzTheme {
        MyPageUserInfo("김정호")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMyPageInduceLogin() {
    PlanzTheme {
        MyPageInduceLogin(
            onSingUpClick = { }
        )
    }
}