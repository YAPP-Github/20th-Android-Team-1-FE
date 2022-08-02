package com.yapp.growth.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.yapp.growth.presentation.R
import com.yapp.growth.presentation.theme.*

@Composable
fun PlanzTextField(
    modifier: Modifier = Modifier,
    label: String,
    hint: String,
    maxLength: Int,
    text: String,
    onInputChanged: (String) -> Unit,
    onDeleteClicked: () -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
) {
    val textState = getTextState(text = text, maxLength = maxLength)

    Column(
        modifier = modifier.padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            color = Gray900,
            style = PlanzTypography.subtitle2
        )

        PlanzBasicTextField(
            text = text,
            maxLength = maxLength,
            hint = hint,
            onInputChanged = onInputChanged,
            onDeleteClicked = onDeleteClicked,
            textState = textState,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
        )

        ErrorAndLengthCounter(
            text = text,
            maxLength = maxLength,
            textState = textState
        )
    }
}

@Composable
fun PlanzBasicTextField(
    text: String,
    maxLength: Int,
    hint: String,
    onInputChanged: (String) -> Unit,
    onDeleteClicked: () -> Unit,
    textState: TextInputState = getTextState(text = text, maxLength = maxLength),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
) {
    TextField(
        value = text,
        onValueChange = { onInputChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = when (textState) {
                    TextInputState.EMPTY -> Gray300
                    TextInputState.NORMAL -> MainPurple900
                    TextInputState.OVERFLOW -> SubCoral
                },
                shape = RoundedCornerShape(8.dp)
            ),
        textStyle = PlanzTypography.body1.copy(if (text.isNotBlank()) Gray900 else Gray300),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
        trailingIcon = {
            Icon(
                modifier = Modifier.clickable { onDeleteClicked() },
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_exit),
                contentDescription = stringResource(id = R.string.icon_clear_content_description),
                tint = when (textState) {
                    TextInputState.EMPTY -> Gray300
                    else -> Gray900
                }
            )
        },
        placeholder = {
            Text(
                text = hint,
                color = Gray300,
                style = PlanzTypography.body1
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Gray800
        )
    )
}

@Composable
private fun ErrorAndLengthCounter(
    text: String,
    maxLength: Int,
    textState: TextInputState = getTextState(text = text, maxLength = maxLength),
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth()
    ) {
        val (errorMsg, lengthCounter) = createRefs()

        AnimatedVisibility(
            visible = textState == TextInputState.OVERFLOW,
            modifier = Modifier.constrainAs(errorMsg) {
                start.linkTo(parent.absoluteLeft)
            },
            enter = fadeIn(initialAlpha = 0.4f),
            exit = fadeOut(animationSpec = tween(durationMillis = 250))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_error),
                    contentDescription = stringResource(id = R.string.icon_error_content_description),
                    tint = Color.Unspecified
                )

                Text(
                    text = "$maxLength" + stringResource(id = R.string.planz_component_text_field_error_message_text),
                    color = SubCoral,
                    style = PlanzTypography.caption
                )
            }
        }
        Text(
            modifier = Modifier.constrainAs(lengthCounter) {
                end.linkTo(parent.absoluteRight)
            },
            text = "${text.length}/$maxLength",
            color = if (textState == TextInputState.OVERFLOW) SubCoral else Gray300,
            style = PlanzTypography.caption
        )
    }
}

enum class TextInputState {
    EMPTY,
    OVERFLOW,
    NORMAL
}

private fun getTextState(text: String, maxLength: Int) = when {
    text.isBlank() -> TextInputState.EMPTY
    text.length > maxLength -> TextInputState.OVERFLOW
    else -> TextInputState.NORMAL
}

@Preview(showBackground = true)
@Composable
fun PlanzTextFieldPreview() {
    PlanzTextField(
        label = "test label",
        hint = "test hint",
        maxLength = 20,
        text = "test text",
        onInputChanged = {},
        onDeleteClicked = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PlanzTextFieldEmptyPreview() {
    PlanzTextField(
        label = "test label",
        hint = "test hint",
        maxLength = 20,
        text = "",
        onInputChanged = {},
        onDeleteClicked = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PlanzTextFieldOverFlowPreview() {
    PlanzTextField(
        label = "test label",
        hint = "test hint",
        maxLength = 20,
        text = "OVERFLOW OVERFLOW OVERFLOW OVERFLOW OVERFLOW OVERFLOW",
        onInputChanged = {},
        onDeleteClicked = {}
    )
}
