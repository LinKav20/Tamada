package com.github.linkav20.home.domain.usecase

import com.github.linkav20.core.domain.repository.UserInformationRepository
import com.github.linkav20.home.domain.repository.UserRepository
import javax.inject.Inject

class UpdateWalletCardNumberUseCase @Inject constructor(
    private val repository: UserRepository,
    private val userInformationRepository: UserInformationRepository
) {
    suspend fun invoke(number: String) {
        val id = userInformationRepository.userId
        repository.updateCardNumber(number = number, userId = id)
    }
}
