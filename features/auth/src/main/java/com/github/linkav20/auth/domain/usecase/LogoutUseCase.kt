package com.github.linkav20.auth.domain.usecase

import com.github.linkav20.auth.domain.repository.TokenRepository
import com.github.linkav20.core.data.SecureStorage
import com.github.linkav20.core.domain.entity.User
import com.github.linkav20.core.domain.repository.UserInformationRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val userInformationRepository: UserInformationRepository,
    private val unsubscribeOnNotificationUseCase: UnsubscribeOnNotificationUseCase
) {

    fun invoke() {
        tokenRepository.refreshToken = null
        tokenRepository.accessToken = null
        val userId =userInformationRepository.userId
        unsubscribeOnNotificationUseCase.invoke(userId.toLong())
    }
}
