package com.github.linkav20.notifications.data

import com.github.linkav20.network.data.api.NotificationApi
import com.github.linkav20.network.data.models.CommonSendNotificationIn
import com.github.linkav20.network.utils.RetrofitErrorHandler
import com.github.linkav20.notifications.domain.repository.NotificationsRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val retrofitErrorHandler: RetrofitErrorHandler,
    private val api: NotificationApi
) : NotificationsRepository {
    override suspend fun sendNotification(toUserId: Long, title: String, subtitle: String) {
        retrofitErrorHandler.apiCall {
            api.pushNotification(
                CommonSendNotificationIn(
                    userID = toUserId.toInt(),
                    title = title,
                    body = subtitle
                )
            )
        }
    }
}
