package com.github.linkav20.finance.domain.usecase

import com.github.linkav20.core.domain.usecase.GetPartyIdUseCase
import com.github.linkav20.finance.domain.repository.FinanceRepository
import javax.inject.Inject

class UpdatePartyWalletBankUseCase @Inject constructor(
    private val repository: FinanceRepository,
    private val getPartyIdUseCase: GetPartyIdUseCase
) {

    suspend fun invoke(bank: String) {
        val id = getPartyIdUseCase.invoke() ?: return
        repository.saveWalletDataBank(
            partyId = id,
            bank = bank
        )
    }
}
