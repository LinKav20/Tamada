package com.github.linkav20.coreui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.github.linkav20.coreui.theme.TamadaTheme

enum class ColorScheme {
    MAIN,
    GUESTS,
    FINANCE,
    LISTS,
}

@Composable
fun getPrimaryColor(scheme: ColorScheme): Color =
    when (scheme) {
        ColorScheme.MAIN -> TamadaTheme.colors.primaryBlue
        ColorScheme.GUESTS -> TamadaTheme.colors.primaryPurple
        ColorScheme.LISTS -> TamadaTheme.colors.primaryPink
        ColorScheme.FINANCE -> TamadaTheme.colors.primaryGreen
    }

@Composable
fun getSecondaryColor(scheme: ColorScheme): Color =
    when (scheme) {
        ColorScheme.MAIN -> TamadaTheme.colors.secondaryBlue
        ColorScheme.GUESTS -> TamadaTheme.colors.secondaryPurple
        ColorScheme.LISTS -> TamadaTheme.colors.secondaryPink
        ColorScheme.FINANCE -> TamadaTheme.colors.secondaryGreen
    }

@Composable
fun getBackgroundColor(scheme: ColorScheme): Color =
    when (scheme) {
        ColorScheme.MAIN -> TamadaTheme.colors.backgroundBlue
        ColorScheme.GUESTS -> TamadaTheme.colors.backgroundPurple
        ColorScheme.LISTS -> TamadaTheme.colors.backgroundPink
        ColorScheme.FINANCE -> TamadaTheme.colors.backgroundGreen
    }

@Composable
fun getStartGradientColor(scheme: ColorScheme): Color =
    when (scheme) {
        ColorScheme.MAIN -> TamadaTheme.colors.startGradientBlue
        ColorScheme.GUESTS -> TamadaTheme.colors.startGradientPurple
        ColorScheme.LISTS -> TamadaTheme.colors.startGradientPink
        ColorScheme.FINANCE -> TamadaTheme.colors.startGradientGreen
    }

@Composable
fun getEndGradientColor(scheme: ColorScheme): Color =
    when (scheme) {
        ColorScheme.MAIN -> TamadaTheme.colors.endGradientBlue
        ColorScheme.GUESTS -> TamadaTheme.colors.endGradientPurple
        ColorScheme.LISTS -> TamadaTheme.colors.endGradientPink
        ColorScheme.FINANCE -> TamadaTheme.colors.endGradientGreen
    }
