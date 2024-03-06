package com.github.linkav20.coreui.theme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.github.linkav20.coreui.R

@Suppress("MemberVisibilityCanBePrivate")
@Stable
open class TamadaColors(
    // Info
    primaryBlue: Color,
    secondaryBlue: Color,
    backgroundBlue: Color,
    startGradientBlue: Color,
    endGradientBlue: Color,
    // Purple
    primaryPurple: Color,
    secondaryPurple: Color,
    backgroundPurple: Color,
    startGradientPurple: Color,
    endGradientPurple: Color,
    // Pink
    primaryPink: Color,
    secondaryPink: Color,
    backgroundPink: Color,
    startGradientPink: Color,
    endGradientPink: Color,
    // Green
    primaryGreen: Color,
    secondaryGreen: Color,
    backgroundGreen: Color,
    startGradientGreen: Color,
    endGradientGreen: Color,
    // Status
    statusPositive: Color,
    statusWarning: Color,
    statusNegative: Color,
    // Text
    textHeader: Color,
    textCaption: Color,
    textMain: Color,
    textHint: Color,
    textWhite: Color,
    backgroundPrimary: Color,
    isDark: Boolean,
) {
    var primaryBlue by mutableStateOf(primaryBlue)
        private set
    var secondaryBlue by mutableStateOf(secondaryBlue)
        private set
    var backgroundBlue by mutableStateOf(backgroundBlue)
        private set
    var startGradientBlue by mutableStateOf(startGradientBlue)
        private set
    var endGradientBlue by mutableStateOf(endGradientBlue)
        private set

    var primaryPurple by mutableStateOf(primaryPurple)
        private set
    var secondaryPurple by mutableStateOf(secondaryPurple)
        private set
    var backgroundPurple by mutableStateOf(backgroundPurple)
        private set
    var startGradientPurple by mutableStateOf(startGradientPurple)
        private set
    var endGradientPurple by mutableStateOf(endGradientPurple)
        private set

    var primaryPink by mutableStateOf(primaryPink)
        private set
    var secondaryPink by mutableStateOf(secondaryPink)
        private set
    var backgroundPink by mutableStateOf(backgroundPink)
        private set
    var startGradientPink by mutableStateOf(startGradientPink)
        private set
    var endGradientPink by mutableStateOf(endGradientPink)
        private set

    var primaryGreen by mutableStateOf(primaryGreen)
        private set
    var secondaryGreen by mutableStateOf(secondaryGreen)
        private set
    var backgroundGreen by mutableStateOf(backgroundGreen)
        private set
    var startGradientGreen by mutableStateOf(startGradientGreen)
        private set
    var endGradientGreen by mutableStateOf(endGradientGreen)
        private set

    var statusPositive by mutableStateOf(statusPositive)
        private set
    var statusWarning by mutableStateOf(statusWarning)
        private set
    var statusNegative by mutableStateOf(statusNegative)
        private set

    var textHeader by mutableStateOf(textHeader)
        private set
    var textCaption by mutableStateOf(textCaption)
        private set
    var textMain by mutableStateOf(textMain)
        private set
    var textWhite by mutableStateOf(textWhite)
        private set
    var textHint by mutableStateOf(textHint)
        private set

    var backgroundPrimary by mutableStateOf(backgroundPrimary)
        private set

    var isDark by mutableStateOf(isDark)
        private set

    fun update(other: TamadaColors) {
        // Info
        primaryBlue = other.primaryBlue
        secondaryBlue = other.secondaryBlue
        backgroundBlue = other.backgroundBlue
        startGradientBlue = other.startGradientBlue
        endGradientBlue = other.endGradientBlue

        // Purple
        primaryPurple = other.primaryPurple
        secondaryPurple = other.secondaryPurple
        backgroundPurple = other.backgroundPurple
        startGradientPurple = other.startGradientPurple
        endGradientPurple = other.endGradientPurple

        // Pink
        primaryPink = other.primaryPink
        secondaryPink = other.secondaryPink
        backgroundPink = other.backgroundPink
        startGradientPink = other.startGradientPink
        endGradientPink = other.endGradientPink

        // Green
        primaryGreen = other.primaryGreen
        secondaryGreen = other.secondaryGreen
        backgroundGreen = other.backgroundGreen
        startGradientGreen = other.startGradientGreen
        endGradientGreen = other.endGradientGreen

        // Status
        statusPositive = other.statusPositive
        statusWarning = other.statusWarning
        statusNegative = other.statusNegative

        // Text
        textHeader = other.textHeader
        textCaption = other.textCaption
        textMain = other.textMain
        textWhite = other.textWhite
        textHint = other.textHint

        backgroundPrimary = other.backgroundPrimary

        isDark = other.isDark
    }

    fun copy(): TamadaColors {
        return TamadaColors(
            // Info
            primaryBlue = this.primaryBlue,
            secondaryBlue = this.secondaryBlue,
            backgroundBlue = this.backgroundBlue,
            startGradientBlue = this.startGradientBlue,
            endGradientBlue = this.endGradientBlue,
            // Purple
            primaryPurple = this.primaryPurple,
            secondaryPurple = this.secondaryPurple,
            backgroundPurple = this.backgroundPurple,
            startGradientPurple = this.startGradientPurple,
            endGradientPurple = this.endGradientPurple,
            // Pink
            primaryPink = this.primaryPink,
            secondaryPink = this.secondaryPink,
            backgroundPink = this.backgroundPink,
            startGradientPink = this.startGradientPink,
            endGradientPink = this.endGradientPink,
            // Green
            primaryGreen = this.primaryGreen,
            secondaryGreen = this.secondaryGreen,
            backgroundGreen = this.backgroundGreen,
            startGradientGreen = this.startGradientGreen,
            endGradientGreen = this.endGradientGreen,
            // Status
            statusPositive = this.statusPositive,
            statusWarning = this.statusWarning,
            statusNegative = this.statusNegative,
            // Text
            textHeader = this.textHeader,
            textCaption = this.textCaption,
            textMain = this.textMain,
            textWhite = this.textWhite,
            textHint = this.textHint,
            backgroundPrimary = this.backgroundPrimary,
            isDark = this.isDark,
        )
    }
}

@Composable
internal fun ProvideTamadaColors(
    colors: TamadaColors,
    content: @Composable () -> Unit,
) {
    val colorSemanticColors =
        remember {
            // Explicitly creating a new object here so we don't mutate the initial [colors]
            // provided, and overwrite the values set in it.
            colors.copy()
        }

    colorSemanticColors.update(colors)
    CompositionLocalProvider(
        LocalTamadaColors provides colorSemanticColors,
        content = content,
    )
}

internal val LocalTamadaColors =
    staticCompositionLocalOf<TamadaColors> {
        error("No TamadaColors provided")
    }

/**
 * A Material [Colors] implementation which sets all colors to [debugColor] to discourage usage of
 * [MaterialTheme.colors] in preference to [OzonTheme.colors].
 */
internal fun debugColors(
    darkTheme: Boolean,
    debugColor: Color = Color.Magenta,
) = Colors(
    primary = debugColor,
    primaryVariant = debugColor,
    secondary = debugColor,
    secondaryVariant = debugColor,
    background = debugColor,
    surface = debugColor,
    error = debugColor,
    onPrimary = debugColor,
    onSecondary = debugColor,
    onBackground = debugColor,
    onSurface = debugColor,
    onError = debugColor,
    isLight = !darkTheme,
)

internal val LightTamadaColor
    @Composable get() =
        TamadaColors(
            primaryBlue = colorResource(R.color.primary_blue),
            secondaryBlue = colorResource(R.color.secondary_blue),
            backgroundBlue = colorResource(R.color.background_blue),
            startGradientBlue = colorResource(R.color.start_gradient_blue),
            endGradientBlue = colorResource(R.color.end_gradient_blue),
            primaryPurple = colorResource(R.color.primary_purple),
            secondaryPurple = colorResource(R.color.secondary_purple),
            backgroundPurple = colorResource(R.color.background_purple),
            startGradientPurple = colorResource(R.color.start_gradient_purple),
            endGradientPurple = colorResource(R.color.end_gradient_purple),
            primaryPink = colorResource(R.color.primary_pink),
            secondaryPink = colorResource(R.color.secondary_pink),
            backgroundPink = colorResource(R.color.background_pink),
            startGradientPink = colorResource(R.color.start_gradient_pink),
            endGradientPink = colorResource(R.color.end_gradient_pink),
            primaryGreen = colorResource(R.color.primary_green),
            secondaryGreen = colorResource(R.color.secondary_green),
            backgroundGreen = colorResource(R.color.background_green),
            startGradientGreen = colorResource(R.color.start_gradient_green),
            endGradientGreen = colorResource(R.color.end_gradient_green),
            statusPositive = colorResource(R.color.status_positive),
            statusWarning = colorResource(R.color.status_warning),
            statusNegative = colorResource(R.color.status_negative),
            textHeader = colorResource(R.color.text_header),
            textMain = colorResource(R.color.text_main),
            textCaption = colorResource(R.color.text_caption),
            textHint = colorResource(R.color.text_hint),
            textWhite = colorResource(R.color.text_white),
            backgroundPrimary = colorResource(R.color.background_primary),
            isDark = false,
        )
