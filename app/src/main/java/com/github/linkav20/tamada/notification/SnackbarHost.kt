package com.github.linkav20.tamada.notification

import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.github.linkav20.core.error.ErrorManager
import com.github.linkav20.core.notification.SnackbarManager
import com.github.linkav20.coreui.ui.TamadaSnackbar
import com.github.linkav20.tamada.presentation.main.MainViewModel
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Composable
internal fun SnackbarHost(
    snackbarManager: SnackbarManager,
    errorManager: ErrorManager,
    mainViewModel: MainViewModel,
    ozonSnackbarHostState: TamadaSnackbarHostState =
        remember {
            TamadaSnackbarHostState(
                SnackbarHostState(),
            )
        },
    modifier: Modifier,
) {
    ErrorHost(
        snackbarHostState = ozonSnackbarHostState,
        errorManager = errorManager,
        onAuthorizeClick = {}, // mainViewModel::onAuthorizeClick
    )
    NotificationHost(
        snackbarHostState = ozonSnackbarHostState,
        snackbarManager = snackbarManager,
    )
    SnackbarHost(
        hostState = ozonSnackbarHostState.snackbarHostState,
        modifier = modifier,
    ) { snackbarData ->
        val data: TamadaSnackbarData =
            try {
                Json.decodeFromString(snackbarData.message)
            } catch (e: SerializationException) {
                throw IllegalArgumentException()
            }
        TamadaSnackbar(
            title = data.title,
            message = data.message,
            type = data.type,
            isSwipeable = data.isSwipeable,
            onSwipeToDismiss = {
                if (data.isSwipeable) {
                    ozonSnackbarHostState.currentSnackbarData?.dismiss()
                }
            },
        )
    }
}
