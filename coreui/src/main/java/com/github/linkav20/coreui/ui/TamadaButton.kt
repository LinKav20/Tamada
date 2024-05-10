package com.github.linkav20.coreui.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.linkav20.coreui.R
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getSecondaryColor

private val HorizontalPadding = 8.dp
private val VerticalPadding = 8.dp
private val Elevation = 0.dp
private val BSize = 40.dp
private val ISize = 32.dp
private val CornerRadius = 10.dp
private val PainterPadding = 6.dp
private const val X_OFFSET = 0
private const val Y_OFFSET = 0

enum class ButtonType {
    PRIMARY,
    SECONDARY,
    OUTLINE,
    ERROR
}

enum class ButtonTextAlign {
    CENTER, START, END
}

@Composable
fun TamadaButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    title: String? = null,
    iconPainter: Painter? = null,
    iconColor: Color? = null,
    backgroundColor: Color? = null,
    contentDescription: String? = null,
    type: ButtonType = ButtonType.PRIMARY,
    elevation: Dp = 0.dp,
    colorScheme: ColorScheme = ColorScheme.MAIN,
    textAlign: ButtonTextAlign = ButtonTextAlign.CENTER
) {
    val hasText = title != null

    val height = if (iconPainter == null) BSize else ISize
    val minWidth = if (iconPainter == null) BSize else ISize

    Button(
        modifier = (if (hasText) modifier.fillMaxWidth() else modifier)
            .height(height)
            .defaultMinSize(minWidth = minWidth)
            .then(
                if (elevation != 0.dp) Modifier.shadow(
                    getSecondaryColor(scheme = colorScheme),
                    borderRadius = elevation,
                    offsetX = X_OFFSET.dp,
                    offsetY = Y_OFFSET.dp,
                    spread = 1f,
                    blurRadius = elevation / 2,
                ) else Modifier
            ),
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor ?: getBackgroundColor(type, colorScheme),
            contentColor = iconColor ?: getContentColor(type, colorScheme),
            disabledBackgroundColor = TamadaTheme.colors.textWhite,
            disabledContentColor = TamadaTheme.colors.textCaption,
        ),
        contentPadding = PaddingValues(
            start = HorizontalPadding,
            end = HorizontalPadding,
            top = VerticalPadding,
            bottom = VerticalPadding,
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = Elevation,
            pressedElevation = Elevation,
            disabledElevation = Elevation,
        ),
        shape = RoundedCornerShape(CornerRadius),
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (iconPainter != null) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = iconPainter,
                    contentDescription = contentDescription
                )
            }
            if (iconPainter != null && hasText) {
                Spacer(modifier = Modifier.width(PainterPadding))
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if (title != null) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = title,
                        style = TamadaTheme.typography.caption,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = when (textAlign) {
                            ButtonTextAlign.END -> TextAlign.End
                            ButtonTextAlign.START -> TextAlign.Start
                            else -> TextAlign.Center
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun getContentColor(
    type: ButtonType,
    scheme: ColorScheme,
): Color =
    when (type) {
        ButtonType.PRIMARY, ButtonType.ERROR -> TamadaTheme.colors.textWhite
        ButtonType.SECONDARY, ButtonType.OUTLINE ->
            when (scheme) {
                ColorScheme.MAIN -> TamadaTheme.colors.primaryBlue
                ColorScheme.GUESTS -> TamadaTheme.colors.primaryPurple
                ColorScheme.LISTS -> TamadaTheme.colors.primaryPink
                ColorScheme.FINANCE -> TamadaTheme.colors.primaryGreen
            }
    }

@Composable
private fun getBackgroundColor(
    type: ButtonType,
    scheme: ColorScheme,
): Color =
    when (type) {
        ButtonType.PRIMARY ->
            when (scheme) {
                ColorScheme.MAIN -> TamadaTheme.colors.primaryBlue
                ColorScheme.GUESTS -> TamadaTheme.colors.primaryPurple
                ColorScheme.LISTS -> TamadaTheme.colors.primaryPink
                ColorScheme.FINANCE -> TamadaTheme.colors.primaryGreen
            }

        ButtonType.SECONDARY ->
            when (scheme) {
                ColorScheme.MAIN -> TamadaTheme.colors.secondaryBlue
                ColorScheme.GUESTS -> TamadaTheme.colors.secondaryPurple
                ColorScheme.LISTS -> TamadaTheme.colors.secondaryPink
                ColorScheme.FINANCE -> TamadaTheme.colors.secondaryGreen
            }

        ButtonType.OUTLINE -> Color.Transparent
        ButtonType.ERROR -> TamadaTheme.colors.statusNegative
    }

@Preview(widthDp = 300, heightDp = 600)
@Composable
private fun TamadaButtonGetPreview() {
    TamadaTheme {
        Row(
            modifier = Modifier.background(color = TamadaTheme.colors.textWhite),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier =
                Modifier
                    .padding(16.dp)
                    .fillMaxHeight(),
            ) {
                ButtonType.values().forEach { type ->
                    ColorScheme.values().forEach {
                        TamadaButton(
                            onClick = { },
                            title = "Сохранить",
                            colorScheme = it,
                            type = type,
                        )
                    }
                }
            }
        }
    }
}

@Preview(widthDp = 300, heightDp = 600)
@Composable
private fun TamadaPainterGetPreview() {
    TamadaTheme {
        Row(
            modifier = Modifier.background(color = TamadaTheme.colors.textWhite),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier =
                Modifier
                    .padding(16.dp)
                    .fillMaxHeight(),
            ) {
                ButtonType.values().forEach { type ->
                    ColorScheme.values().forEach {
                        TamadaButton(
                            onClick = { },
                            iconPainter = painterResource(id = R.drawable.edit_icon),
                            colorScheme = it,
                            type = type,
                        )
                    }
                }
            }
        }
    }
}
