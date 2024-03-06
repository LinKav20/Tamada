package com.github.linkav20.tamada.notification

import androidx.compose.material.SnackbarData
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import com.github.linkav20.coreui.ui.Type
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class TamadaSnackbarHostState(val snackbarHostState: SnackbarHostState) {
    val currentSnackbarData: SnackbarData?
        get() = snackbarHostState.currentSnackbarData

    suspend fun showSnackbar(
        title: String? = null,
        message: String? = null,
        type: Type,
        actionLabel: String? = null,
        duration: SnackbarDuration = SnackbarDuration.Short,
        isSwipeable: Boolean = true,
    ): SnackbarResult {
        val data =
            Json.encodeToString(
                TamadaSnackbarData(
                    title = title,
                    message = message,
                    type = type,
                    isSwipeable = isSwipeable,
                ),
            )
        return snackbarHostState.showSnackbar(message = data, actionLabel = actionLabel, duration)
    }
}
