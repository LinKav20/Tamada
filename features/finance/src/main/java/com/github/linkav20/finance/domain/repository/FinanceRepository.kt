package com.github.linkav20.finance.domain.repository

import android.net.Uri
import com.github.linkav20.finance.domain.model.Expense
import com.github.linkav20.finance.domain.model.FinanceState
import com.github.linkav20.finance.domain.model.User
import java.io.InputStream

interface FinanceRepository {

    suspend fun loadFinanceState(id: Long): FinanceState

    suspend fun getAllExpanses(id: Long): List<User>

    suspend fun getExpenses(id: Long): User

    suspend fun sendExpense(
        type: Expense.Type,
        name: String,
        sum: Double,
        receipt: Uri
    )

    suspend fun deleteExpense(id: Long)

    suspend fun getExpenseReceipt(id: Long): InputStream

    suspend fun saveWalletData(partyId: Long, cardNumber: String, phoneNumber: String)
}
