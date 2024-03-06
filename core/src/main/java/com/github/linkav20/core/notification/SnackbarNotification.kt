package com.github.linkav20.core.notification

import com.github.linkav20.core.domain.entity.ReactionStyle

class SnackbarNotification(
    val title: String?,
    val subtitle: String?,
    val reactionStyle: ReactionStyle = ReactionStyle.INFO,
    val isSwipeable: Boolean = true,
)
