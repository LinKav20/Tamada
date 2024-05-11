package com.github.linkav20.notifications.domain.repository

interface NotificationsRepository {

    suspend fun sendNotification(
        toUserId: Long,
        title: String,
        subtitle: String
    )
}
