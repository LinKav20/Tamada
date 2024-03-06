package com.github.linkav20.tamada.presentation.error

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.github.linkav20.core.domain.entity.DomainException
import com.github.linkav20.core.error.ErrorMapper
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.ui.TamadaError
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.tamada.R
import javax.inject.Inject

class ErrorMapperImpl @Inject constructor() : ErrorMapper {
    @Composable
    override fun OnError(
        throwable: Throwable,
        onActionClick: () -> Unit,
        colorScheme: ColorScheme
    ) {
        when (throwable) {
            DomainException.Forbidden -> {
                TamadaError(
                    title = "Title",
                    subtitle = "Subtitle",
                    actionLabel = "Update",
                    onActionClick = onActionClick
                )
            }

            DomainException.Network -> {
                TamadaError(
                    title = "Title",
                    subtitle = "Subtitle",
                    actionLabel = "Update",
                    onActionClick = onActionClick
                )
            }

            DomainException.Timeout -> {
                TamadaError(
                    title = "Title",
                    subtitle = "Subtitle",
                    actionLabel = "Update",
                    onActionClick = onActionClick
                )
            }

            DomainException.Unknown -> {
                TamadaError(
                    title = "Title",
                    subtitle = "Subtitle",
                    actionLabel = "Update",
                    onActionClick = onActionClick
                )
            }

            is DomainException.Text -> {
                TamadaError(
                    title = "Title",
                    subtitle = "Subtitle",
                    actionLabel = "Update",
                    onActionClick = onActionClick
                )
            }

            is DomainException.Server -> {
                TamadaError(
                    title = "Title",
                    subtitle = "Subtitle",
                    actionLabel = "Update",
                    onActionClick = onActionClick
                )
            }

            is DomainException.Client -> {
                TamadaError(
                    title = "Title",
                    subtitle = "Subtitle",
                    actionLabel = "Update",
                    onActionClick = onActionClick
                )
            }

            is DomainException.Unauthorized -> {
                TamadaError(
                    title = "Title",
                    subtitle = "Subtitle",
                    actionLabel = "Update",
                    onActionClick = onActionClick
                )
            }
        }
    }

    @Composable
    private fun alertImage(): @Composable () -> Unit =
        {
            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = null,
            )
        }
}
