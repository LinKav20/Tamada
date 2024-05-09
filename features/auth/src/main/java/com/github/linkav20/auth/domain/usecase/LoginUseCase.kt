package com.github.linkav20.auth.domain.usecase

import com.github.linkav20.auth.domain.repository.AuthRepository
import com.github.linkav20.auth.domain.repository.TokenRepository
import com.github.linkav20.core.domain.usecase.SetUserInformationUseCase
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val tokenRepository: TokenRepository,
    private val setUserInformationUseCase: SetUserInformationUseCase,
    private val subscribeOnNotificationUseCase: SubscribeOnNotificationUseCase
) {

    suspend fun invoke(login: String, password: String) {
        val data = repository.login(login = login, password = password)
        val tokens = data.tokens
        setUserInformationUseCase.invoke(data.user)
        tokenRepository.accessToken = tokens?.accessToken
        tokenRepository.refreshToken = tokens?.refreshToken
        subscribeOnNotificationUseCase.invoke(data.user.id.toLong())
    }
}
