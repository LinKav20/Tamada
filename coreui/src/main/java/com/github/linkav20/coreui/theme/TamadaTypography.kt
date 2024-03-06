package com.github.linkav20.coreui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.unit.sp
import com.github.linkav20.coreui.R

val DefaultTamadaTypography
    get() =
        TamadaTypography(
            head = TextStyle(
                fontFamily = Font(R.font.golos_text_regular).toFontFamily(),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                lineHeight = 28.sp,
                letterSpacing = 0.4.sp,
            ),
            body = TextStyle(
                fontFamily = Font(R.font.golos_text_regular).toFontFamily(),
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                lineHeight = 18.sp,
            ),
            caption = TextStyle(
                fontFamily = Font(R.font.golos_text_regular).toFontFamily(),
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.2.sp,
            ),
            small = TextStyle(
                fontFamily = Font(R.font.golos_text_regular).toFontFamily(),
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                lineHeight = 14.sp,
                letterSpacing = 0.2.sp,
            ),
        )

@Immutable
open class TamadaTypography(
    // Head
    val head: TextStyle,
    // Body
    val body: TextStyle,
    // Caption
    val caption: TextStyle,
    // Small
    val small: TextStyle
)

internal fun debugTypography(debugColor: Color = Color.Magenta): Typography {
    return Typography().run {
        copy(
            h1 = this.h1.copy(color = debugColor),
            h2 = this.h2.copy(color = debugColor),
            h3 = this.h3.copy(color = debugColor),
            h4 = this.h4.copy(color = debugColor),
            h5 = this.h5.copy(color = debugColor),
            h6 = this.h6.copy(color = debugColor),
            subtitle1 = this.subtitle1.copy(color = debugColor),
            subtitle2 = this.subtitle2.copy(color = debugColor),
            body1 = this.body1.copy(color = debugColor),
            body2 = this.body2.copy(color = debugColor),
            button = this.button.copy(color = debugColor),
            caption = this.caption.copy(color = debugColor),
            overline = this.overline.copy(color = debugColor),
        )
    }
}
