package com.github.linkav20.coreui.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.github.linkav20.coreui.R
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getBackgroundColor
import kotlin.math.max

private val AppBarHeight = 48.dp

@Composable
fun TamadaTopBar(
    modifier: Modifier = Modifier,
    title: String = "",
    subtitle: String? = null,
    actions: @Composable RowScope.() -> Unit = {},
    onBackClick: (() -> Unit)? = null,
    transparent: Boolean = false,
    colorScheme: ColorScheme = ColorScheme.MAIN,
) {
    val backgroundColor =
        if (transparent) Color.Transparent else getBackgroundColor(scheme = colorScheme)
    val contentColor =
        if (transparent) TamadaTheme.colors.textWhite else TamadaTheme.colors.textHeader
    val subtitleTextColor =
        if (transparent) TamadaTheme.colors.textWhite else TamadaTheme.colors.textHeader
    TopAppBar(
        modifier =
            modifier
                .height(AppBarHeight),
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        elevation = 0.dp,
    ) {
        CenteredLayout(
            left = {
                BackButton(
                    onBackClick = onBackClick,
                )
            },
            center = {
                Content(
                    title = title,
                    subtitle = subtitle,
                    textColor = contentColor,
                    subtitleTextColor = subtitleTextColor,
                )
            },
            right = {
                Row(
                    Modifier.fillMaxHeight(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    content = actions,
                )
            },
        )
    }
}

/**
 * Places [center] right between [left] and [right] sections
 */
@Composable
private fun CenteredLayout(
    modifier: Modifier = Modifier,
    left: @Composable () -> Unit,
    right: @Composable () -> Unit,
    center: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier,
        content = {
            Box { left() }
            Box { center() }
            Box { right() }
        },
    ) { measurables, constraints ->

        check(measurables.size == 3)

        val leftPlaceable = measurables[0].measure(constraints.copy(minWidth = 0, minHeight = 0))
        val rightPlaceable = measurables[2].measure(constraints.copy(minWidth = 0, minHeight = 0))

        val offset = max(leftPlaceable.width, rightPlaceable.width)
        val childConstraints =
            constraints.copy(
                maxWidth = max(constraints.maxWidth - 2 * offset, 0),
                minHeight = 0,
            )

        val centerPlaceable = measurables[1].measure(childConstraints)

        layout(constraints.maxWidth, constraints.maxHeight) {
            leftPlaceable.placeRelative(x = 0, y = 0)
            rightPlaceable.placeRelative(x = constraints.maxWidth - rightPlaceable.width, y = 0)
            centerPlaceable.apply {
                placeRelative(x = offset, y = 0)
            }
        }
    }
}

@Composable
private fun Content(
    title: String,
    subtitle: String? = null,
    textColor: Color,
    subtitleTextColor: Color,
) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
        ) {
            Text(
                title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = TamadaTheme.typography.head,
                color = textColor,
            )
        }

        if (subtitle != null) {
            Box(
                contentAlignment = Alignment.Center,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
            ) {
                Text(
                    subtitle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = TamadaTheme.typography.body,
                )
            }
        }
    }
}

@Composable
private fun BackButton(
    onBackClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
) {
    if (onBackClick != null) {
        DebounceIconButton(onBackClick, modifier = modifier) {
            Icon(
                painterResource(R.drawable.baseline_arrow_back_24),
                contentDescription = null,
                tint = TamadaTheme.colors.textMain,
            )
        }
    }
}
