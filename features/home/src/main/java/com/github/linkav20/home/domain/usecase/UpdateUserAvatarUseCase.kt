package com.github.linkav20.home.domain.usecase

import com.github.linkav20.home.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserAvatarUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend fun invoke(id: Int) = repository.updateUserAvatar(id)
}
