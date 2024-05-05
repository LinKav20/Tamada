package com.github.linkav20.home.domain.usecase

import com.github.linkav20.core.domain.repository.UserInformationRepository
import com.github.linkav20.home.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserAvatarUseCase @Inject constructor(
    private val repository: UserRepository,
    private val userInformationRepository: UserInformationRepository
) {

    suspend fun invoke(id: Int) {
        val userId = userInformationRepository.userId
        repository.updateUserAvatar(avatar = id, userId = userId)
    }
}
