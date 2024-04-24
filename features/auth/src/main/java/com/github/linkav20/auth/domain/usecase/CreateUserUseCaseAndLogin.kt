package com.github.linkav20.auth.domain.usecase

import com.github.linkav20.auth.domain.repository.AuthRepository
import com.github.linkav20.auth.domain.repository.TokenRepository
import com.github.linkav20.core.domain.usecase.SetUserInformationUseCase
import javax.inject.Inject

class CreateUserUseCaseAndLogin @Inject constructor(
    private val repository: AuthRepository,
    private val tokenRepository: TokenRepository,
    private val setUserInformationUseCase: SetUserInformationUseCase
) {

    suspend fun invoke(
        login: String,
        email: String,
        password: String
    ) {
        val data = repository.createUser(
            login = login,
            email = email,
            password = password
        )
        val tokens = data.tokens
        setUserInformationUseCase.invoke(data.user)
        tokenRepository.accessToken = tokens?.accessToken
        tokenRepository.refreshToken = tokens?.refreshToken
    }
}
