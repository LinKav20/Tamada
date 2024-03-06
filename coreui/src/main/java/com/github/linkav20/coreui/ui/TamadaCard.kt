package com.github.linkav20.coreui.ui

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getSecondaryColor

private const val ELEVATION = 12
private const val X_OFFSET = 0
private const val Y_OFFSET = 0

@Composable
fun TamadaCard(
    elevation: Dp = ELEVATION.dp,
    colorScheme: ColorScheme = ColorScheme.MAIN,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) = Box(
    modifier = modifier
        .fillMaxWidth()
        .shadow(
            getSecondaryColor(scheme = colorScheme),
            borderRadius = elevation,
            offsetX = X_OFFSET.dp,
            offsetY = Y_OFFSET.dp,
            spread = elevation / 2,
            blurRadius = elevation,
        )
        .clip(TamadaTheme.shapes.mediumSmall),
    contentAlignment = Alignment.Center,
) {
    Box(
        modifier =
        Modifier
            .fillMaxWidth()
            .clip(TamadaTheme.shapes.mediumSmall)
            .background(TamadaTheme.colors.backgroundPrimary),
    ) {
        content()
    }
}

fun Modifier.shadow(
    color: Color = Color.Black,
    borderRadius: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    spread: Dp = 0f.dp,
    modifier: Modifier = Modifier,
) = this.then(
    modifier.drawBehind {
        this.drawIntoCanvas {
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            val spreadPixel = spread.toPx()
            val leftPixel = (0f - spreadPixel) + offsetX.toPx()
            val topPixel = (0f - spreadPixel) + offsetY.toPx()
            val rightPixel = (this.size.width + spreadPixel)
            val bottomPixel = (this.size.height + spreadPixel)

            if (blurRadius != 0.dp) {
                frameworkPaint.maskFilter =
                    (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
            }

            frameworkPaint.color = color.toArgb()
            it.drawRoundRect(
                left = leftPixel,
                top = topPixel,
                right = rightPixel,
                bottom = bottomPixel,
                radiusX = borderRadius.toPx(),
                radiusY = borderRadius.toPx(),
                paint,
            )
        }
    },
)

fun Modifier.shadow(
    color: Color = Color.Black,
    borderRadius: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    spread: Float = 0f,
    modifier: Modifier = Modifier,
) = this.then(
    modifier.drawBehind {
        this.drawIntoCanvas {
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            val leftPixel = (0f - spread) + offsetX.toPx()
            val topPixel = (0f - spread) + offsetY.toPx()
            val rightPixel = (this.size.width + spread)
            val bottomPixel = (this.size.height + spread)

            if (blurRadius != 0.dp) {
                frameworkPaint.maskFilter =
                    (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
            }

            frameworkPaint.color = color.toArgb()
            it.drawRoundRect(
                left = leftPixel,
                top = topPixel,
                right = rightPixel,
                bottom = bottomPixel,
                radiusX = borderRadius.toPx(),
                radiusY = borderRadius.toPx(),
                paint,
            )
        }
    },
)

@Composable
@Preview(showBackground = true)
private fun Preview() {
    TamadaTheme {
        TamadaCard(
            modifier = Modifier.padding(30.dp),
            content = {
                Text(text = "Hi there")
            },
        )
    }
}
