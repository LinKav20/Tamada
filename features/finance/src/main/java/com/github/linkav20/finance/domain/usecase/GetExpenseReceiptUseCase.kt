package com.github.linkav20.finance.domain.usecase

import com.github.linkav20.core.domain.repository.UserInformationRepository
import com.github.linkav20.core.domain.usecase.GetPartyIdUseCase
import com.github.linkav20.finance.domain.repository.FinanceRepository
import java.io.InputStream
import javax.inject.Inject

class GetExpenseReceiptUseCase @Inject constructor(
    private val repository: FinanceRepository,
    private val getPartyIdUseCase: GetPartyIdUseCase,
    private val userInformationRepository: UserInformationRepository
) {

    suspend fun invoke(id: Long): InputStream? {
        val partyId = getPartyIdUseCase.invoke() ?: return null
        val userId = userInformationRepository.userId
        return repository.getExpenseReceipt(
            expenseId = id,
            partyId = partyId,
            userId = userId.toLong()
        )
    }
}
