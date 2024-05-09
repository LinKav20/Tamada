package com.github.linkav20.finance.domain.usecase

import com.github.linkav20.core.domain.usecase.GetPartyIdUseCase
import com.github.linkav20.finance.domain.repository.FinanceRepository
import javax.inject.Inject

class UpdatePartyWalletCardNumberUseCase @Inject constructor(
    private val repository: FinanceRepository,
    private val getPartyIdUseCase: GetPartyIdUseCase
) {

    suspend fun invoke(cardNumber: String) {
        val id = getPartyIdUseCase.invoke() ?: return
        repository.saveWalletDataCardNumber(
            partyId = id,
            cardNumber = cardNumber
        )
    }
}
