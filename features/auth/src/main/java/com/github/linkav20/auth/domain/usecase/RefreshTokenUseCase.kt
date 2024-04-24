package com.github.linkav20.auth.domain.usecase

import com.github.linkav20.auth.domain.repository.AuthRepository
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend fun invoke() = repository.refreshToken()
}