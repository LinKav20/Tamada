package com.github.linkav20.finance.domain.usecase

import android.content.Context
import android.net.Uri
import com.github.linkav20.core.domain.repository.UserInformationRepository
import com.github.linkav20.core.domain.usecase.GetPartyIdUseCase
import com.github.linkav20.core.utils.multipartBodyPartsFromUri
import com.github.linkav20.finance.domain.model.Expense
import com.github.linkav20.finance.domain.repository.FinanceRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AddExpenseUseCase @Inject constructor(
    private val repository: FinanceRepository,
    private val getPartyIdUseCase: GetPartyIdUseCase,
    private val userInformationRepository: UserInformationRepository,
    @ApplicationContext private val context: Context
) {

    suspend fun invoke(
        type: Expense.Type,
        name: String,
        sum: Double,
        receipt: Uri
    ) {
        val contentResolver = context.contentResolver
        val partyId = getPartyIdUseCase.invoke() ?: return
        val file = multipartBodyPartsFromUri(
            uri = receipt,
            contentResolver = contentResolver,
            context = context,
        )
        if (file == null) return
        repository.createExpense(
            type = type,
            name = name,
            sum = sum,
            receipt = file,
            partyId = partyId,
            userId = userInformationRepository.userId.toLong()
        )
    }
}
