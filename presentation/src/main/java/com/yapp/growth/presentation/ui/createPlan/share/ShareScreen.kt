package com.yapp.growth.presentation.ui.createPlan.share

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.share.WebSharerClient
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link
import com.yapp.growth.presentation.BuildConfig
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.component.PlanzErrorSnackBar
import com.yapp.growth.presentation.component.PlanzSnackBar
import com.yapp.growth.presentation.firebase.PLAN_ID_KEY_NAME
import com.yapp.growth.presentation.theme.BackgroundColor1
import com.yapp.growth.presentation.theme.CoolGray500
import com.yapp.growth.presentation.theme.Gray200
import com.yapp.growth.presentation.theme.Gray800
import com.yapp.growth.presentation.theme.Gray900
import com.yapp.growth.presentation.theme.MainPurple900
import com.yapp.growth.presentation.theme.PlanzTypography
import com.yapp.growth.presentation.theme.SubYellow
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
                when (viewState.snackBarType) {
                    ShareContract.ShareViewState.SnackBarType.SUCCESS ->
                        PlanzSnackBar(
                            message = snackbarData.message,
                            bottomPadding = 156
                        )
                    else -> PlanzErrorSnackBar(
                        message = snackbarData.message,
                        bottomPadding = 156
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(BackgroundColor1)
        ) {
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
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                ShareTitle(modifier = Modifier.padding(top = 26.dp))

                Image(
                    modifier = Modifier
                        .fillMaxWidth(),
                    painter = painterResource(id = R.drawable.image_invitation),
                    contentDescription = stringResource(id = R.string.image_invitation_content_description)
                )

                ShareButtonColumn(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 32.dp),
                    shareUrl = viewState.shareUrl,
                    onCopyClick = {
                        viewModel.setEvent(ShareContract.ShareEvent.OnClickCopy)
                    },
                    onShareButtonClick = {
                        viewModel.setEvent(ShareContract.ShareEvent.OnClickShare)
                    },
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getDynamicLink(
            context = context,
            thumbNailTitle = context.getString(R.string.share_thumbnail_title),
            thumbNailDescription = context.getString(R.string.share_thumbnail_description),
            thumbNailImageUrl = BuildConfig.BASE_URL + context.getString(R.string.share_plan_share_feed_template_image_url)
        )
    }

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ShareContract.ShareSideEffect.FinishCreatePlan -> {
                    finishCreatePlan()
                }
                is ShareContract.ShareSideEffect.CopyShareUrl -> {
                    clipboardManager.setText(AnnotatedString(viewState.shareUrl))
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            context.getString(R.string.share_plan_copy_success_message)
                        )
                    }
                }
                is ShareContract.ShareSideEffect.SendKakaoShareMessage -> {
                    kakaoSocialShare(
                        context = context,
                        planId = viewState.planId.toString(),
                        shareUrl = viewState.shareUrl,
                        startShareActivity = { shareIntent -> startShareActivity(shareIntent) },
                        failToShareWithKakaoTalk = {
                            viewModel.setEvent(ShareContract.ShareEvent.FailToShare)
                        }
                    )
                }
                is ShareContract.ShareSideEffect.ShowFailToShareSnackBar -> {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                        scaffoldState.snackbarHostState.showSnackbar(
                            context.getString(R.string.share_plan_share_fail_message)
                        )
                    }
                }
                is ShareContract.ShareSideEffect.ShowSuccessSnackBar -> {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                        scaffoldState.snackbarHostState.showSnackbar(
                            context.getString(R.string.share_plan_copy_success_message)
                        )
                    }
                }
            }
        }
    }
}

fun kakaoSocialShare(
    context: Context,
    planId: String,
    shareUrl: String,
    startShareActivity: (Intent) -> Unit,
    failToShareWithKakaoTalk: () -> Unit,
) {
    val shareFeedImageUrl =
        BuildConfig.BASE_URL + context.getString(R.string.share_plan_share_feed_template_image_url)

    val sharePlanFeedTemplate = FeedTemplate(
        content = Content(
            title = context.getString(R.string.share_plan_share_feed_template_title),
            description = context.getString(R.string.share_plan_share_feed_template_description),
            imageUrl = shareFeedImageUrl,
            link = Link(
                webUrl = shareUrl,
                mobileWebUrl = shareUrl,
                androidExecutionParams = mapOf(PLAN_ID_KEY_NAME to planId)
            ),
            imageWidth = 800,
            imageHeight = 400
        )
    )

    if (ShareClient.instance.isKakaoTalkSharingAvailable(context)) {
        ShareClient.instance.shareDefault(context, sharePlanFeedTemplate) { sharingResult, error ->
            if (error != null) {
                failToShareWithKakaoTalk()
            } else if (sharingResult != null) {
                startShareActivity(sharingResult.intent)

                Timber.w("Warning Msg: ${sharingResult.warningMsg}")
                Timber.w("Argument Msg: ${sharingResult.argumentMsg}")
            }
        }
    } else {
        val sharerUrl = WebSharerClient.instance.makeDefaultUrl(sharePlanFeedTemplate)

        runCatching {
            KakaoCustomTabsClient.openWithDefault(context, sharerUrl)
        }.onFailure {
            failToShareWithKakaoTalk()
        }
    }
}

@Composable
fun ShareTitle(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(start = 20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = buildAnnotatedString {
                append(stringResource(id = R.string.share_plan_now_share_your_plan_now_text))
                withStyle(SpanStyle(color = MainPurple900)) {
                    append(stringResource(id = R.string.share_plan_now_share_your_plan_share_text))
                }
                append(stringResource(id = R.string.share_plan_now_share_your_plan_do_text))
            },
            style = PlanzTypography.h2,
            color = Gray900
        )

        Text(
            text = stringResource(id = R.string.share_plan_max_member_count_text),
            style = PlanzTypography.body1,
            color = Gray900
        )

        Text(
            text = stringResource(id = R.string.share_plan_max_member_including_yourself_text),
            style = PlanzTypography.caption,
            color = CoolGray500
        )
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
        ShareKakaoButton(onShareButtonClick = onShareButtonClick)
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = shareUrl,
                color = Gray800,
                style = PlanzTypography.body2,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
            Text(
                modifier = Modifier
                    .wrapContentWidth()
                    .clickable { onCopyClick() },
                text = stringResource(id = R.string.share_plan_copy_text),
                color = MainPurple900,
                style = PlanzTypography.subtitle2
            )
        }
    }
}

@Composable
fun ShareKakaoButton(
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
    ShareKakaoButton {}
}
