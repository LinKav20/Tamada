package com.github.linkav20.finance.domain.usecase

import com.github.linkav20.core.domain.usecase.GetPartyIdUseCase
import com.github.linkav20.finance.domain.repository.FinanceRepository
import java.io.InputStream
import javax.inject.Inject

class GetAllUserReceipts @Inject constructor(
    private val repository: FinanceRepository,
    private val getPartyIdUseCase: GetPartyIdUseCase
) {

    suspend fun invoke(userId: Long): InputStream? {
        val partyId = getPartyIdUseCase.invoke() ?: return null
        return repository.getUserReceipts(
            userId = userId,
            partyId = partyId
        )
    }
}
