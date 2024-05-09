package com.github.linkav20.auth.domain.usecase

import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import javax.inject.Inject

class SubscribeOnNotificationUseCase @Inject constructor( ){
    fun invoke(userId: Long) = Firebase.messaging.subscribeToTopic(userId.toString())
}
