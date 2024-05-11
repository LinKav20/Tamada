package com.github.linkav20.finance.domain.repository

import com.github.linkav20.finance.domain.model.Calculate
import com.github.linkav20.finance.domain.model.Expense
import com.github.linkav20.finance.domain.model.FinanceState
import com.github.linkav20.finance.domain.model.User
import com.github.linkav20.finance.domain.model.Wallet
import okhttp3.MultipartBody
import java.io.InputStream
import java.time.OffsetDateTime

interface FinanceRepository {

    suspend fun loadFinanceState(id: Long): FinanceState

    suspend fun getExpenses(userId: Long, partyId: Long): List<Expense>

    suspend fun createExpense(
        type: Expense.Type,
        name: String,
        sum: Double,
        receipt: MultipartBody.Part,
        partyId: Long,
        userId: Long
    )

    suspend fun updateExpenseName(
        expenseId: Long,
        name: String,
        partyId: Long,
        userId: Long
    )

    suspend fun updateExpenseSum(
        expenseId: Long,
        sum: Double,
        partyId: Long,
        userId: Long
    )

    suspend fun updateExpenseReceipt(
        expenseId: Long,
        receipt: MultipartBody.Part,
        partyId: Long,
        userId: Long
    )

    suspend fun getTotalSum(partyId: Long): Double?

    suspend fun deleteExpense(id: Long)

    suspend fun updateDeadline(deadline: OffsetDateTime?, partyId: Long)

    suspend fun getDeadline(partyId: Long): OffsetDateTime?

    suspend fun updateFinanceState(state: FinanceState, partyId: Long, userId: Long)

    suspend fun getExpenseReceipt(
        expenseId: Long,
        partyId: Long,
        userId: Long
    ): InputStream?

    suspend fun saveWalletDataCardNumber(partyId: Long, cardNumber: String)

    suspend fun saveWalletDataPhoneNumber(partyId: Long, phoneNumber: String)

    suspend fun saveWalletDataOwner(partyId: Long, owner: String)

    suspend fun saveWalletDataBank(partyId: Long, bank: String)

    suspend fun getPartyWallet(partyId: Long): Wallet?

    suspend fun getUsers(partyId: Long): List<User>

    suspend fun calculateExpenses(partyId: Long, userId: Long): Calculate?

    suspend fun getUserWalletInfo(userId: Long): Wallet?

    suspend fun getUserReceipts(userId: Long, partyId: Long): InputStream?
}
