package com.github.linkav20.finance.domain.repository

import android.net.Uri
import com.github.linkav20.finance.domain.model.Expense
import com.github.linkav20.finance.domain.model.FinanceState
import com.github.linkav20.finance.domain.model.User
import com.github.linkav20.finance.domain.model.Wallet
import java.io.InputStream
import java.math.BigDecimal
import java.time.OffsetDateTime

interface FinanceRepository {

    suspend fun loadFinanceState(id: Long): FinanceState

    suspend fun getAllExpanses(id: Long): List<User>

    suspend fun getExpenses(userId: Long, partyId: Long): List<Expense>

    suspend fun sendExpense(
        type: Expense.Type,
        name: String,
        sum: Double,
        receipt: Uri
    )

    suspend fun getTotalSum(partyId: Long): Long?

    suspend fun deleteExpense(id: Long)

    suspend fun updateDeadline(deadline: OffsetDateTime?, partyId: Long)

    suspend fun getDeadline(partyId: Long): OffsetDateTime?

    suspend fun updateFinanceState(state: FinanceState, partyId: Long, userId: Long)

    suspend fun getExpenseReceipt(id: Long): InputStream

    suspend fun saveWalletDataCardNumber(partyId: Long, cardNumber: String)

    suspend fun saveWalletDataPhoneNumber(partyId: Long, phoneNumber: String)

    suspend fun saveWalletDataOwner(partyId: Long, owner: String)

    suspend fun saveWalletDataBank(partyId: Long, bank: String)

    suspend fun getPartyWallet(partyId: Long): Wallet?
}
