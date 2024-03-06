package com.github.linkav20.coreui.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import com.github.linkav20.coreui.R
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getBackgroundColor
import com.github.linkav20.coreui.utils.getPrimaryColor
import com.github.linkav20.coreui.utils.getSecondaryColor

private const val SmallSize = 8
private const val MediumSize = 12
private const val BigSize = 20

enum class Size {
    SMALL,
    MEDIUM,
    BIG,
}

@Composable
fun TamadaRoadMap(
    currentIndex: Int,
    stepsCount: Int,
    titles: List<Int>,
    modifier: Modifier = Modifier,
    panelSize: Size = Size.SMALL,
    colorScheme: ColorScheme = ColorScheme.MAIN,
    onPointClick: List<() -> Unit>,
) {
    if (!(stepsCount == onPointClick.size && stepsCount == titles.size)) throw Exception()

    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val circleRadius =
            with(LocalDensity.current) {
                when (panelSize) {
                    Size.SMALL -> SmallSize
                    Size.MEDIUM -> MediumSize
                    Size.BIG -> BigSize
                }.dp
            }
        val primaryColor = getPrimaryColor(scheme = colorScheme)
        val secondaryColor = getSecondaryColor(scheme = colorScheme)
        val padding = MediumSize.dp

        val maxWidth = with(LocalDensity.current) { maxWidth.toPx() }
        val gap = (maxWidth / stepsCount)
        var currentGap = gap / 2
        val gaps = mutableListOf<Float>()
        for (i in 1..stepsCount) {
            gaps.add(currentGap)
            currentGap += gap
        }
        val split = if (currentIndex == -1) 0f else gaps[currentIndex]
        Canvas(
            modifier =
            Modifier
                .fillMaxWidth()
                .height(circleRadius * 2),
        ) {
            drawLine(
                color = primaryColor,
                start = Offset(x = 0f, y = (0f + circleRadius.toPx())),
                end = Offset(x = split, y = (0f + circleRadius.toPx())),
                strokeWidth = circleRadius.toPx() / 2,
            )
            drawLine(
                color = secondaryColor,
                start = Offset(x = split, y = (0f + circleRadius.toPx())),
                end = Offset(x = maxWidth, y = (0f + circleRadius.toPx())),
                strokeWidth = circleRadius.toPx() / 2,
            )
        }

        for (i in 0 until stepsCount) {
            val text = stringResource(id = titles[i])
            val textMeasurer = rememberTextMeasurer()
            val widthInPixels =
                textMeasurer.measure(text, TamadaTheme.typography.small).size.width
            val width = with(LocalDensity.current) { widthInPixels.toDp() }
            val color = TamadaTheme.colors.textMain
            Canvas(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .height(circleRadius * 5),
            ) {
                drawText(
                    textMeasurer = textMeasurer,
                    text = text,
                    style = TamadaTheme.typography.small.copy(color = color),
                    topLeft =
                    Offset(
                        x = gaps[i] - width.toPx() / 2,
                        y = (0f + circleRadius.toPx() + padding.toPx()),
                    ),
                )
                drawCircle(
                    color = if (i <= currentIndex) primaryColor else secondaryColor,
                    radius = circleRadius.toPx(),
                    center = Offset(x = gaps[i], y = (0f + circleRadius.toPx())),
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            for (i in 0 until stepsCount) {
                Box(
                    modifier =
                    Modifier
                        .size(circleRadius * 2)
                        .clickable { onPointClick[i]() },
                    contentAlignment = Alignment.Center,
                ) {
                    if (i <= currentIndex) {
                        Icon(
                            painter = painterResource(id = R.drawable.done_arrow_24),
                            contentDescription = "",
                            tint = getBackgroundColor(scheme = ColorScheme.MAIN),
                        )
                    }
                }
            }
        }
    }
}

// @Preview
// @Composable
// private fun Preview() {
//    TamadaTheme {
//        TamadaRoadMap(
//            currentIndex = 2,
//            stepsCount = 5,
//            titles = listOf(
//                R.string.home_main_screen_party_tag,
//                R.string.auth_main_screen_login_title,
//                R.string.home_main_screen_party_tag,
//                R.string.home_main_screen_party_tag,
//                R.string.home_main_screen_party_tag
//            ),
//            onPointClick = listOf({}, {}, {}, {}, {})
//        )
//    }
// }
