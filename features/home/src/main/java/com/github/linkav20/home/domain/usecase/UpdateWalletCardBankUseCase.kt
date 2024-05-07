package com.github.linkav20.home.domain.usecase

import com.github.linkav20.core.domain.repository.UserInformationRepository
import com.github.linkav20.home.domain.repository.UserRepository
import javax.inject.Inject

class UpdateWalletCardBankUseCase @Inject constructor(
    private val repository: UserRepository,
    private val userInformationRepository: UserInformationRepository
) {
    suspend fun invoke(bank: String) {
        val id = userInformationRepository.userId
        repository.updateCardBank(bank = bank, userId = id)
    }
}
