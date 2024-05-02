package com.github.linkav20.tamada.notification

import android.util.Log
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.github.linkav20.core.domain.entity.DomainException
import com.github.linkav20.core.error.ErrorManager
import com.github.linkav20.coreui.ui.Type
import com.github.linkav20.tamada.R
import kotlinx.coroutines.launch

@Composable
internal fun ErrorHost(
    snackbarHostState: TamadaSnackbarHostState,
    errorManager: ErrorManager,
    onAuthorizeClick: suspend () -> Unit,
) {
    val context = LocalContext.current

    LaunchedEffect(snackbarHostState) {
        errorManager.exceptions.collect { exception ->
            snackbarHostState.currentSnackbarData?.dismiss()

            launch {
                val result =
                    when (exception) {
                        DomainException.Network ->
                            snackbarHostState.showSnackbar(
                                message = context.resources.getString(R.string.error_network),
                                type = Type.ERROR,
                            )

                        is DomainException.Server ->
                            snackbarHostState.showSnackbar(
                                message = context.resources.getString(R.string.error_server),
                                type = Type.ERROR,
                            )

                        is DomainException.Client ->
                            snackbarHostState.showSnackbar(
                                message = context.resources.getString(R.string.error_client),
                                type = Type.ERROR,
                            )

                        DomainException.Unknown ->
                            snackbarHostState.showSnackbar(
                                message = context.resources.getString(R.string.error_unknown),
                                type = Type.ERROR,
                            )

                        is DomainException.Unauthorized ->
                            snackbarHostState.showSnackbar(
                                message = exception.text
                                    ?: context.resources.getString(R.string.error_unauthorized),
                                type = Type.ERROR,
                                actionLabel = if (exception.showButton) {
                                    context.resources.getString(R.string.error_unauthorized_action)
                                } else {
                                    null
                                },
                            )

                        is DomainException.Text ->
                            snackbarHostState.showSnackbar(
                                message = exception.text,
                                type = Type.ERROR,
                            )

                        DomainException.Timeout ->
                            snackbarHostState.showSnackbar(
                                message = context.getString(R.string.error_timeout),
                                type = Type.ERROR,
                            )

                        is DomainException.Forbidden ->
                            snackbarHostState.showSnackbar(
                                message = context.getString(R.string.error_forbidden),
                                type = Type.ERROR,
                            )

                        is DomainException.NoDataException -> {

                        }
                    }
                when (result) {
                    SnackbarResult.Dismissed -> Log.d("", "Notification dismissed")
                    SnackbarResult.ActionPerformed -> {
                        if (exception is DomainException.Unauthorized) {
                            onAuthorizeClick()
                        }
                    }
                }
            }
        }
    }
}
