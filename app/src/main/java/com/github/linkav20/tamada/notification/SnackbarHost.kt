package com.github.linkav20.tamada.notification

import androidx.compose.material.SnackbarData
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
    tamadaSnackbarHostState: TamadaSnackbarHostState =
        remember {
            TamadaSnackbarHostState(
                SnackbarHostState(),
            )
        },
    modifier: Modifier,
) {
    ErrorHost(
        snackbarHostState = tamadaSnackbarHostState,
        errorManager = errorManager,
        onAuthorizeClick = {}, // mainViewModel::onAuthorizeClick
    )
    NotificationHost(
        snackbarHostState = tamadaSnackbarHostState,
        snackbarManager = snackbarManager,
    )
    SnackbarHost(
        hostState = tamadaSnackbarHostState.snackbarHostState,
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
                    tamadaSnackbarHostState.currentSnackbarData?.dismiss()
                }
            },
        )
    }
}
