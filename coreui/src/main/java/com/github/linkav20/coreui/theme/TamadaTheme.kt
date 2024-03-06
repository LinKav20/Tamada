package com.github.linkav20.coreui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

@Composable
fun TamadaTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (isDarkTheme) LightTamadaColor else LightTamadaColor
    ProvideTamadaColors(colors) {
        MaterialTheme(
            colors = debugColors(isDarkTheme),
            typography = debugTypography(),
            content = {
                CompositionLocalProvider(
                    LocalContentColor provides TamadaTheme.colors.primaryBlue,
                    LocalContentAlpha provides 1f,
                    LocalRippleTheme provides TamadaRippleTheme,
                    LocalTextSelectionColors provides
                        TextSelectionColors(
                            handleColor = TamadaTheme.colors.primaryBlue,
                            backgroundColor = TamadaTheme.colors.textHeader,
                        ),
                    content = content,
                )
            },
        )
    }
}

object TamadaTheme {
    val colors: TamadaColors
        @Composable
        @ReadOnlyComposable
        get() = LocalTamadaColors.current

    val typography: TamadaTypography
        get() = DefaultTamadaTypography

    val shapes: TamadaShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalTamadaShapes.current
}
