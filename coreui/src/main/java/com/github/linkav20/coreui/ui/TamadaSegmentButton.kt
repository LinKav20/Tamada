package com.github.linkav20.coreui.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getBackgroundColor
import com.github.linkav20.coreui.utils.getPrimaryColor

private const val BORDER_WIDTH = 1

sealed class SegmentElement {
    data class SegmentButton(
        val title: String?,
        val isPrimary: Boolean = false,
        val isEnabled: Boolean = true,
        val onClick: () -> Unit,
    ) : SegmentElement()

    data class Divider(val width: Int = 5) : SegmentElement()
}

@Composable
fun TamadaSegmentButton(
    elements: List<SegmentElement>,
    modifier: Modifier = Modifier,
    colorScheme: ColorScheme = ColorScheme.MAIN,
) {
    Row(
        modifier =
            modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        elements.forEachIndexed { index, item ->
            when (item) {
                is SegmentElement.SegmentButton -> {
                    OutlinedButton(
                        onClick = item.onClick,
                        modifier =
                            Modifier
                                .weight(1f / elements.size)
                                .zIndex(if (item.isPrimary) 1f else 0f),
                        enabled = item.isEnabled,
                        shape =
                            when (index) {
                                0 ->
                                    TamadaTheme.shapes.mediumSmall.copy(
                                        topEnd = CornerSize(0.dp),
                                        bottomEnd = CornerSize(0.dp),
                                    )

                                elements.size - 1 ->
                                    TamadaTheme.shapes.mediumSmall.copy(
                                        bottomStart = CornerSize(0.dp),
                                        topStart = CornerSize(0.dp),
                                    )

                                else -> TamadaTheme.shapes.nullShape
                            },
                        border =
                            BorderStroke(
                                BORDER_WIDTH.dp,
                                TamadaTheme.colors.backgroundPrimary,
                            ),
                        colors =
                            ButtonDefaults.outlinedButtonColors(
                                backgroundColor = TamadaTheme.colors.backgroundPrimary,
                                contentColor = TamadaTheme.colors.backgroundPrimary,
                            ),
                    ) {
                        Text(
                            text = item.title ?: "",
                            style = TamadaTheme.typography.body,
                            color =
                                if (!item.isPrimary) {
                                    TamadaTheme.colors.textMain
                                } else {
                                    getPrimaryColor(scheme = colorScheme)
                                },
                        )
                    }
                }

                is SegmentElement.Divider -> {
                    Box {
                        Divider(
                            color = getBackgroundColor(scheme = colorScheme),
                            modifier =
                                Modifier
                                    .fillMaxHeight(0.5f)
                                    .width(item.width.dp)
                                    .clip(TamadaTheme.shapes.small),
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun Preview() {
    TamadaTheme {
        TamadaSegmentButton(
            elements =
                listOf(
                    SegmentElement.SegmentButton(
                        title = "Hi",
                        isPrimary = true,
                        onClick = {},
                    ),
                    SegmentElement.Divider(),
                    SegmentElement.SegmentButton(
                        title = "Lol",
                        onClick = {},
                    ),
                ),
        )
    }
}
