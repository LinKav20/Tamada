package com.github.linkav20.auth.domain.usecase

import com.github.linkav20.auth.domain.repository.AuthRepository
import com.github.linkav20.auth.domain.repository.TokenRepository
import javax.inject.Inject

class CreateUserUseCaseAndLogin @Inject constructor(
    private val repository: AuthRepository,
    private val tokenRepository: TokenRepository
) {

    suspend fun invoke(
        login: String,
        email: String,
        password: String
    ) {
        val tokens = repository.createUser(
            login = login,
            email = email,
            password = password
        )
        tokenRepository.accessToken = tokens?.accessToken
        tokenRepository.refreshToken = tokens?.refreshToken
    }
}
