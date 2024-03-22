package com.github.linkav20.home.domain.usecase

import com.github.linkav20.home.domain.repository.UserRepository
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend fun invoke(
        currentPassword: String,
        newPassword: String
    ) = repository.updatePassword(
        currentPassword = currentPassword,
        newPassword = newPassword
    )
}
