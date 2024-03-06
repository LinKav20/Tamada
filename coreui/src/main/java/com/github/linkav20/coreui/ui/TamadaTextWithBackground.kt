package com.github.linkav20.coreui.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getPrimaryColor
import com.github.linkav20.coreui.utils.getSecondaryColor

enum class TextWithBackgroundType { TEXT, FIELD }

@Composable
fun TamadaTextWithBackground(
    text: String,
    modifier: Modifier = Modifier,
    type: TextWithBackgroundType = TextWithBackgroundType.TEXT,
    textColor: Color? = null,
    colorScheme: ColorScheme = ColorScheme.MAIN,
    maxLines: Int = 1
) = Text(
    modifier = modifier
        .fillMaxHeight()
        .clip(TamadaTheme.shapes.mediumSmall)
        .background(getSecondaryColor(scheme = colorScheme))
        .padding(
            when (type) {
                TextWithBackgroundType.TEXT -> 8.dp
                TextWithBackgroundType.FIELD -> 16.dp
            }
        ),
    text = text,
    style = TamadaTheme.typography.caption,
    color = textColor ?: getPrimaryColor(scheme = colorScheme),
    maxLines = maxLines,
)
