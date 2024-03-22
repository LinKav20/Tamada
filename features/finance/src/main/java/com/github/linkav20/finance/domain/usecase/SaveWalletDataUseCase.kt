package com.github.linkav20.finance.domain.usecase

import com.github.linkav20.finance.domain.repository.FinanceRepository
import javax.inject.Inject

class SaveWalletDataUseCase @Inject constructor(
    private val repository: FinanceRepository
) {

    suspend fun invoke(
        partyId: Long,
        cardNumber: String,
        phoneNumber: String
    ) = repository.saveWalletData(
        partyId = partyId,
        cardNumber = cardNumber,
        phoneNumber = phoneNumber
    )
}