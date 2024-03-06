package com.github.linkav20.auth.domain.usecase

import com.github.linkav20.core.data.SecureStorage
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val secureStorage: SecureStorage
) {

    suspend fun invoke() = secureStorage.clear()
}
