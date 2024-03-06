package com.github.linkav20.coreui.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

@Immutable
data class TamadaShapes(
    val nullShape: CornerBasedShape = RoundedCornerShape(0.dp),
    val small: CornerBasedShape = RoundedCornerShape(6.dp),
    val mediumSmall: CornerBasedShape = RoundedCornerShape(8.dp),
    val medium: CornerBasedShape = RoundedCornerShape(12.dp),
    val mediumLarge: CornerBasedShape = RoundedCornerShape(16.dp),
    val large: CornerBasedShape = RoundedCornerShape(20.dp),
)

internal val LocalTamadaShapes = staticCompositionLocalOf { TamadaShapes() }
