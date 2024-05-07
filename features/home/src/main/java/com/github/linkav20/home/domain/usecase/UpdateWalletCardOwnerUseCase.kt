package com.github.linkav20.home.domain.usecase

import com.github.linkav20.core.domain.repository.UserInformationRepository
import com.github.linkav20.home.domain.repository.UserRepository
import javax.inject.Inject

class UpdateWalletCardOwnerUseCase @Inject constructor(
    private val repository: UserRepository,
    private val userInformationRepository: UserInformationRepository
) {
    suspend fun invoke(owner: String) {
        val id = userInformationRepository.userId
        repository.updateCardOwner(owner = owner, userId = id)
    }
}
