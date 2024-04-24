package com.github.linkav20.coreui.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.github.linkav20.coreui.R
import com.github.linkav20.coreui.theme.TamadaTheme

private const val SUBTITLE_MAX_LINES = 4
private const val DISMISS_FRACTION = 0.4F

@Composable
fun TamadaSnackbar(
    modifier: Modifier = Modifier,
    title: String? = null,
    message: String? = null,
    type: Type? = null,
    isSwipeable: Boolean = false,
    onSwipeToDismiss: (() -> Unit)? = null,
) {
    if (isSwipeable) {
        SwipeToDismissSnackbar(onDismiss = onSwipeToDismiss) {
            Content(modifier, title, message, type)
        }
    } else {
        Content(modifier, title, message, type)
    }
}

@Composable
private fun Content(
    modifier: Modifier,
    title: String?,
    message: String?,
    type: Type?,
) = Row(
    modifier = modifier
        .fillMaxWidth()
        .padding(all = 8.dp)
        .background(
            color = Color.Black.copy(alpha = 0.8f),
            shape = TamadaTheme.shapes.medium,
        )
        .padding(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 16.dp),
    verticalAlignment = Alignment.CenterVertically
) {
    if (type != null) {
        when (type) {
            Type.SUCCESS ->
                Icon(
                    modifier =Modifier.size(32.dp),
                    painter = painterResource(id = R.drawable.round_done_24),
                    contentDescription = "success",
                    tint = TamadaTheme.colors.statusPositive,
                )

            Type.ERROR ->
                Icon(
                    modifier =Modifier.size(32.dp),
                    painter = painterResource(id = R.drawable.round_close_24),
                    contentDescription = "error",
                    tint = TamadaTheme.colors.statusNegative,
                )

            Type.WARNING ->
                Icon(
                    modifier =Modifier.size(32.dp),
                    painter = painterResource(id = R.drawable.add_image_icon),
                    contentDescription = "warning",
                    tint = TamadaTheme.colors.statusWarning,
                )

            Type.INFO ->
                Icon(
                    modifier =Modifier.size(32.dp),
                    painter = painterResource(id = R.drawable.add_image_icon),
                    contentDescription = "info",
                    tint = TamadaTheme.colors.textMain,
                )
        }
        Spacer(modifier = Modifier.width(12.dp))
    }
    Column {
        if (title != null) {
            Text(
                title,
                style = TamadaTheme.typography.head,
                color = TamadaTheme.colors.textWhite,
            )
        }
        if (message != null) {
            Text(
                text = message,
                maxLines = SUBTITLE_MAX_LINES,
                overflow = TextOverflow.Ellipsis,
                style = TamadaTheme.typography.body,
                color = TamadaTheme.colors.textWhite,
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SwipeToDismissSnackbar(
    onDismiss: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val dismissState = rememberDismissState(initialValue = DismissValue.Default)
    SwipeToDismiss(
        state = dismissState,
        background = { },
        directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
        dismissThresholds = { FractionalThreshold(DISMISS_FRACTION) },
    ) {
        if (
            dismissState.currentValue == DismissValue.DismissedToStart ||
            dismissState.currentValue == DismissValue.DismissedToEnd
        ) {
            onDismiss?.invoke()
        }
        content()
    }
}

enum class Type {
    SUCCESS,
    ERROR,
    WARNING,
    INFO,
}
