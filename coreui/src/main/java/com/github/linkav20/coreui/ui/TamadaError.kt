package com.github.linkav20.coreui.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.utils.ColorScheme

@Composable
fun TamadaError(
    modifier: Modifier = Modifier,
    colorScheme: ColorScheme = ColorScheme.MAIN,
    title: String? = null,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    actionLabel: String? = null,
    onActionClick: (() -> Unit)? = null,
    content: (@Composable () -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))
        if (icon != null) {
            Box(
                modifier = Modifier
                    .weight(1f, fill = false)
                    .fillMaxHeight()
            ) {
                icon()
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
        if (title != null) {
            Text(
                title,
                textAlign = TextAlign.Center,
                style = TamadaTheme.typography.head,
                color = TamadaTheme.colors.textHeader
            )
        }
        if (subtitle != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                subtitle,
                textAlign = TextAlign.Center,
                style = TamadaTheme.typography.body,
                color = TamadaTheme.colors.textMain,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
        }
        if (content != null) {
            Spacer(modifier = Modifier.height(8.dp))
            content.invoke()
        }
        Spacer(modifier = Modifier.weight(1f))
        if (actionLabel != null && onActionClick != null) {
            Spacer(modifier = Modifier.height(16.dp))
            TamadaButton(
                colorScheme = colorScheme,
                title = actionLabel,
                onClick = onActionClick
            )
        }
    }
}
