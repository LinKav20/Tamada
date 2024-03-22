package com.github.linkav20.auth.domain.usecase

import com.github.linkav20.auth.domain.repository.TokenRepository
import com.github.linkav20.core.data.SecureStorage
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {

    fun invoke() {
        tokenRepository.refreshToken = null
        tokenRepository.accessToken = null
    }
}
