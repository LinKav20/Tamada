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
        val wallet = repository.getUserWalletInfo(savedData.id)

        return User(
            id = savedData.id,
            avatar = savedData.avatarId,
            email = savedData.login,
            login = savedData.login,
            wallet = wallet
        )
    }
}
