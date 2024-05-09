package com.github.linkav20.network.data.api

import retrofit2.http.*
import retrofit2.Response

import com.github.linkav20.network.data.models.CommonSendNotificationIn

interface NotificationApi {
    /**
     * SendNotification
     * Отправка уведомления пользователю
     * Responses:
     *  - 204: No Content
     *
     * @param input notification info
     * @return [Unit]
     */
    @POST("push/send_notification")
    suspend fun pushNotification(@Body input: CommonSendNotificationIn): Response<Unit>

}
