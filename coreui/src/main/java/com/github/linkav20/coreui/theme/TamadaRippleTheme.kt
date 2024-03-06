package com.github.linkav20.coreui.theme

import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable

internal object TamadaRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() =
        RippleTheme.defaultRippleColor(
            contentColor = TamadaTheme.colors.textCaption,
            lightTheme = TamadaTheme.colors.isDark.not(),
        )

    @Composable
    override fun rippleAlpha(): RippleAlpha {
        return RippleTheme.defaultRippleAlpha(
            contentColor = TamadaTheme.colors.textCaption,
            lightTheme = TamadaTheme.colors.isDark.not(),
        )
    }
}
