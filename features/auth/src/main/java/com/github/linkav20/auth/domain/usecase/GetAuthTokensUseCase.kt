package com.github.linkav20.auth.domain.usecase

import com.github.linkav20.auth.domain.model.AuthTokenData
import com.github.linkav20.auth.domain.repository.TokenRepository
import javax.inject.Inject

class GetAuthTokensUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {
    operator fun invoke(): AuthTokenData? {
        val accessToken = tokenRepository.accessToken ?: return null
        val refreshToken = tokenRepository.refreshToken ?: return null
        return AuthTokenData(accessToken, refreshToken)
    }
}
