package com.github.linkav20.coreui.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getEndGradientColor
import com.github.linkav20.coreui.utils.getPrimaryColor
import com.github.linkav20.coreui.utils.getStartGradientColor

@Composable
fun TamadaGradientDisclaimer(
    modifier: Modifier = Modifier,
    colorScheme: ColorScheme = ColorScheme.MAIN,
    content: @Composable () -> Unit
) = Box(
    modifier = modifier
        .clip(TamadaTheme.shapes.mediumSmall)
        .background(
            brush = Brush.horizontalGradient(
                colors = listOf(
                    getStartGradientColor(scheme = colorScheme),
                    getEndGradientColor(scheme = colorScheme)
                )
            )
        )
        .padding(16.dp)
) {
    content()
}

@Composable
@Preview
private fun Preview() {
    TamadaTheme {
        TamadaGradientDisclaimer {
            Text("Text")
        }
    }
}
