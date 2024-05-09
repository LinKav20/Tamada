package com.github.linkav20.finance.domain.usecase

import com.github.linkav20.core.domain.usecase.GetPartyIdUseCase
import com.github.linkav20.finance.domain.repository.FinanceRepository
import javax.inject.Inject

class UpdatePartyWalletOwnerUseCase @Inject constructor(
    private val repository: FinanceRepository,
    private val getPartyIdUseCase: GetPartyIdUseCase
) {

    suspend fun invoke(owner: String) {
        val id = getPartyIdUseCase.invoke() ?: return
        repository.saveWalletDataOwner(
            partyId = id,
            owner = owner
        )
    }
}
