package com.yapp.growth.presentation.ui.createPlan.share

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.component.PlanzSnackBar
import com.yapp.growth.presentation.theme.*
import kotlinx.coroutines.launch
import timber.log.Timber


@Composable
fun ShareScreen(
    viewModel: ShareViewModel = hiltViewModel(),
    finishCreatePlan: () -> Unit,
    startShareActivity: (Intent) -> Unit,
) {
    val viewState by viewModel.viewState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { snackbarHostState ->
            SnackbarHost(hostState = snackbarHostState) { snackbarData ->
                PlanzSnackBar(message = snackbarData.message)
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(BackgroundColor1)) {
            Icon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 26.dp)
                    .padding(end = 20.dp)
                    .clickable { viewModel.setEvent(ShareContract.ShareEvent.OnClickExit) },
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_exit),
                contentDescription = stringResource(id = R.string.icon_exit_content_description),
                tint = Color.Unspecified,
            )

            Column(
                modifier = Modifier
                    .padding(top = 26.dp)
                    .padding(start = 20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.share_plan_now_share_your_plan_text),
                    style = PlanzTypography.h2,
                    color = Gray900
                )

                Text(
                    text = stringResource(id = R.string.share_plan_max_member_count_text),
                    style = PlanzTypography.body1,
                    color = Gray900
                )
            }

            // TODO: 이미지 교체 및 위치 조정
            Image(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(width = 280.dp, 230.dp),
                painter = painterResource(id = R.drawable.image_invitation),
                contentDescription = stringResource(id = R.string.image_invitation_content_description)
            )

            ShareButtonColumn(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 32.dp),
                shareUrl = viewState.shareUrl,
                onCopyClick = {
                    clipboardManager.setText(AnnotatedString(viewState.shareUrl))
                    viewModel.setEvent(ShareContract.ShareEvent.OnClickCopy)
                },
                onShareButtonClick = {
                    kakaoSocialShare(
                        context = context,
                        startShareActivity = { shareIntent -> startShareActivity(shareIntent) }
                    )
                }
            )
        }
    }

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ShareContract.ShareSideEffect.FinishCreatePlan -> {
                    finishCreatePlan()
                }
                is ShareContract.ShareSideEffect.ShowSnackBar -> {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            context.getString(R.string.share_plan_copy_success_message)
                        )
                    }
                }
            }
        }
    }
}

// TODO: 요구사항에 맞게 공유 기능 구현 필요(현재 임시 구현 상태)
fun kakaoSocialShare(
    context: Context,
    startShareActivity: (Intent) -> Unit,
) {
    // TODO: 서버로부터 약속 공유 이미지 받아오기
    // TODO: URL 설정
    // TODO: 이미지 크기 설정
    val sharePlanFeedTemplate = FeedTemplate(
        content = Content(
            title = "약속 초대 링크",
            description = "가능한 약속 날짜와 시간을 응답해 주세요!",
            imageUrl = "https://picsum.photos/300/200",
            link = Link(
                webUrl = "https://developers.kakao.com",
                mobileWebUrl = "https://developers.kakao.com"
            )
        )
    )

    ShareClient.instance.shareDefault(context, sharePlanFeedTemplate) { sharingResult, error ->
        if (error != null) {
            Timber.w("공유 실패", error)
        } else if (sharingResult != null) {
            Timber.w("카카오링크 보내기 성공 ${sharingResult.intent}")
            startShareActivity(sharingResult.intent)

            Timber.w("Warning Msg: ${sharingResult.warningMsg}")
            Timber.w("Argument Msg: ${sharingResult.argumentMsg}")
        }
    }
}

@Composable
fun ShareButtonColumn(
    modifier: Modifier = Modifier,
    shareUrl: String,
    onCopyClick: () -> Unit,
    onShareButtonClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ShareUrl(shareUrl = shareUrl, onCopyClick = onCopyClick)
        ShareKaKaoButton(onShareButtonClick = onShareButtonClick)
    }
}

@Composable
fun ShareUrl(
    shareUrl: String,
    onCopyClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color.White,
        border = BorderStroke(1.dp, Gray200),
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = shareUrl,
                color = Gray800,
                style = PlanzTypography.body2
            )
            Text(
                modifier = Modifier.clickable { onCopyClick() },
                text = stringResource(id = R.string.share_plan_copy_text),
                color = MainPurple900,
                style = PlanzTypography.subtitle2
            )
        }
    }
}

@Composable
fun ShareKaKaoButton(
    onShareButtonClick: () -> Unit,
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        onClick = onShareButtonClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = SubYellow),
        shape = RoundedCornerShape(10.dp),
        elevation = null
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(R.drawable.icon_kakao),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = R.string.share_plan_kakao_button_text),
                color = Gray900, //Color(0x001E1715),
                style = PlanzTypography.button
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShareKaKaoButtonPreview() {
    ShareKaKaoButton {}
}
