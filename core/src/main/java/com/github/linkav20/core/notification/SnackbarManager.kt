package com.github.linkav20.core.notification

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SnackbarManager @Inject constructor() {
    private val _notifications = Channel<SnackbarNotification>()
    val notifications = _notifications.receiveAsFlow()

    internal fun show(notification: SnackbarNotification) = _notifications.trySend(notification)
}
