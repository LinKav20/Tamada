package com.github.linkav20.tamada.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.github.linkav20.tamada.R
import com.github.linkav20.tamada.presentation.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber

const val CHANNEL_ID = "NOTIFICATIONS"
const val CHANNEL_NAME = "com.github.linkav20.tamada.notifications"

class NotificationService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = 1
        val requestCode = 1

        val channelId = CHANNEL_ID
        val channelName = CHANNEL_NAME
        notificationManager.createNotificationChannel(
            NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
        )

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntentFlag = PendingIntent.FLAG_IMMUTABLE
        val pendingIntent = PendingIntent.getActivity(this, requestCode, intent, pendingIntentFlag)

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(message.notification?.title)
            .setContentText(message.notification?.body)
            .setSmallIcon(R.drawable.splash)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        Timber.tag(CHANNEL_ID).d(message.notification.toString())

        notificationManager.notify(notificationId, notification)
    }

}
