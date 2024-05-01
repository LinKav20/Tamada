package com.github.linkav20.core.notification

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.github.linkav20.core.domain.entity.ReactionStyle
import com.github.linkav20.core.error.ErrorManager
import javax.inject.Inject

class ReactUseCase @Inject constructor(
    private val snackbarManager: SnackbarManager,
    private val errorManager: ErrorManager
) {
    fun invoke(
        title: String?,
        subtitle: String? = null,
        style: ReactionStyle = ReactionStyle.INFO,
        isSwipeable: Boolean = true,
    ) {
        snackbarManager.show(
            SnackbarNotification(
                title = title,
                subtitle = subtitle,
                reactionStyle = style,
                isSwipeable = isSwipeable,
            ),
        )
    }

    fun invoke(exception: Exception) {
        errorManager.show(exception)
    }
}
