package com.github.linkav20.tamada.notification

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.github.linkav20.core.domain.entity.ReactionStyle
import com.github.linkav20.core.notification.SnackbarManager
import com.github.linkav20.coreui.ui.Type
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
internal fun NotificationHost(
    snackbarHostState: TamadaSnackbarHostState,
    snackbarManager: SnackbarManager,
) {
    LaunchedEffect(snackbarHostState) {
        snackbarManager.notifications
            .onEach {
                snackbarHostState.currentSnackbarData?.dismiss()
                launch {
                    snackbarHostState.showSnackbar(
                        title = it.title,
                        message = it.subtitle,
                        type = convertToType(it.reactionStyle),
                        isSwipeable = it.isSwipeable,
                    )
                }
            }
            .collect()
    }
}

private fun convertToType(style: ReactionStyle) =
    when (style) {
        ReactionStyle.ERROR -> Type.ERROR
        ReactionStyle.SUCCESS -> Type.SUCCESS
        ReactionStyle.WARNING -> Type.WARNING
        ReactionStyle.INFO -> Type.INFO
    }
