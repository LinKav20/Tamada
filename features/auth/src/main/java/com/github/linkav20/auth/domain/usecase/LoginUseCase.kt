package com.github.linkav20.auth.domain.usecase

import com.github.linkav20.auth.domain.repository.AuthRepository
import com.github.linkav20.auth.domain.repository.TokenRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val tokenRepository: TokenRepository
) {

    suspend fun invoke(login: String, password: String) {
        val tokens = repository.login(login = login, password = password) ?: return
        tokenRepository.accessToken = tokens.accessToken
        tokenRepository.refreshToken = tokens.refreshToken
    }
}
