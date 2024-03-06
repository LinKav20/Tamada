package com.github.linkav20.coreui.ui

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.linkav20.coreui.R
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getPrimaryColor
import com.github.linkav20.coreui.utils.getSecondaryColor

enum class TextFiledType {
    PRIMARY, SECONDARY
}

@Composable
fun TamadaTextFiled(
    value: String,
    modifier: Modifier = Modifier,
    textFieldModifier: Modifier = Modifier,
    colorScheme: ColorScheme = ColorScheme.MAIN,
    type: TextFiledType = TextFiledType.SECONDARY,
    onValueChange: (String) -> Unit = {},
    enabled: Boolean = true,
    readOnly: Boolean = false,
    label: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    supportingText: (@Composable () -> Unit)? = null,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    textStyle: TextStyle = TamadaTheme.typography.caption,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    showEraseButton: Boolean = true,
    placeholder: String? = null,
) {
    var textFieldValueState by remember {
        mutableStateOf(TextFieldValue(text = value, selection = TextRange(value.length)))
    }
    val textFieldValue = textFieldValueState.copy(text = value)
    var lastTextValue by remember(value) { mutableStateOf(value) }

    val labelColor = if (enabled) {
        TamadaTheme.colors.textHint
    } else {
        TamadaTheme.colors.textHint
    }
    Column(modifier = modifier) {
        TextField(
            value = textFieldValue,
            onValueChange = { newTextFieldValueState ->
                textFieldValueState = newTextFieldValueState

                val stringChangedSinceLastInvocation = lastTextValue != newTextFieldValueState.text
                lastTextValue = newTextFieldValueState.text

                if (stringChangedSinceLastInvocation) {
                    onValueChange(newTextFieldValueState.text)
                }
            },
            modifier = textFieldModifier.fillMaxWidth(),
            enabled = enabled,
            readOnly = readOnly,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            maxLines = maxLines,
            textStyle = textStyle,
            label = label(label, labelColor, textStyle),
            leadingIcon = leadingIcon,
            trailingIcon = {
                Row {
                    if (trailingIcon == null) {
                        if (showEraseButton && textFieldValueState.text.isNotEmpty()) {
                            DebounceIconButton(onClick = {
                                textFieldValueState = TextFieldValue(text = "", TextRange(0))
                                onValueChange.invoke("")
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_clear_24),
                                    contentDescription = null,
                                    tint = getPrimaryColor(scheme = colorScheme),
                                )
                            }
                        }
                    } else {
                        trailingIcon.invoke()
                    }
                }
            },
            isError = isError,
            colors =
            TextFieldDefaults.textFieldColors(
                textColor = when (type) {
                    TextFiledType.PRIMARY -> TamadaTheme.colors.textHeader
                    TextFiledType.SECONDARY -> getPrimaryColor(scheme = colorScheme)
                },
                disabledTextColor = TamadaTheme.colors.textCaption,
                backgroundColor = when (type) {
                    TextFiledType.PRIMARY -> TamadaTheme.colors.backgroundPrimary
                    TextFiledType.SECONDARY -> getSecondaryColor(scheme = colorScheme)
                },
                cursorColor = getPrimaryColor(scheme = colorScheme),
                errorCursorColor = TamadaTheme.colors.statusNegative,
                placeholderColor = TamadaTheme.colors.textCaption,
                disabledPlaceholderColor = TamadaTheme.colors.textCaption,
                errorIndicatorColor = TamadaTheme.colors.statusNegative,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            shape = TamadaTheme.shapes.mediumSmall,
            visualTransformation = visualTransformation,
            interactionSource = interactionSource,
            placeholder = label(placeholder, labelColor, textStyle),
        )
        if (supportingText != null) {
            Box(Modifier.padding(start = 12.dp, top = 4.dp, end = 12.dp)) {
                supportingText()
            }
        }
    }
}


@Composable
private fun label(
    label: String? = null,
    labelColor: Color,
    textStyle: TextStyle,
): @Composable (() -> Unit)? {
    if (label == null) return null

    return {
        Text(
            label,
            color = labelColor,
            style = textStyle,
        )
    }
}

@Composable
fun DebounceIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    delayMs: Long = 1000,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit,
) {
    val controller = remember { DebounceEventController(delayMs) }
    IconButton({ controller.processEvent(onClick) }, modifier, enabled, interactionSource, content)
}

class DebounceEventController(private val delayMs: Long = 1000) {
    private val nowMs get() = System.currentTimeMillis()
    private var lastEventMs = 0L

    fun processEvent(event: () -> Unit) {
        if (nowMs - lastEventMs > delayMs) {
            lastEventMs = nowMs
            event()
        }
    }
}

@Composable
@Preview
private fun Preview() {
    TamadaTheme {
        TamadaTextFiled(
            value = "Hello",
        )
    }
}
