package com.github.linkav20.notifications.domain.usecase

import com.github.linkav20.notifications.domain.repository.NotificationsRepository
import javax.inject.Inject

class NotifyUseCase @Inject constructor(
    private val notificationsRepository: NotificationsRepository
) {
    suspend fun invoke(
        toUserId: Long,
        title: String,
        subtitle: String
    ) = notificationsRepository.sendNotification(
        toUserId = toUserId,
        subtitle = subtitle,
        title = title
    )
}
