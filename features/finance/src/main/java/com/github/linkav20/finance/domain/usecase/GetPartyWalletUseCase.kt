package com.github.linkav20.finance.domain.usecase

import com.github.linkav20.core.domain.usecase.GetPartyIdUseCase
import com.github.linkav20.finance.domain.model.Wallet
import com.github.linkav20.finance.domain.repository.FinanceRepository
import javax.inject.Inject

class GetPartyWalletUseCase @Inject constructor(
    private val repository: FinanceRepository,
    private val getPartyIdUseCase: GetPartyIdUseCase
) {

    suspend fun invoke(): Wallet? {
        val partyId = getPartyIdUseCase.invoke() ?: return null
        return repository.getPartyWallet(partyId = partyId)
    }
}
