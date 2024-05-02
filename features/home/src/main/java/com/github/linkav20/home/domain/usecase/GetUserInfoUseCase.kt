package com.github.linkav20.home.domain.usecase

import com.github.linkav20.core.domain.repository.UserInformationRepository
import com.github.linkav20.home.domain.model.User
import com.github.linkav20.home.domain.repository.UserRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val repository: UserRepository,
    private val userInformationRepository: UserInformationRepository
) {

    suspend fun invoke(): User {
        val savedData = userInformationRepository.getUser()
        val serverUser = repository.getUserInfo()
        // TODO

        return serverUser.copy(
            login = savedData.login,
            id = savedData.id,
            avatar = savedData.avatarId
        )
    }
}
