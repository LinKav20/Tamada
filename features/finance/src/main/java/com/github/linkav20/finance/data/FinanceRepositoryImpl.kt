package com.github.linkav20.finance.data

import android.net.Uri
import com.github.linkav20.core.utils.DateTimeUtils
import com.github.linkav20.finance.domain.model.Expense
import com.github.linkav20.finance.domain.model.FinanceState
import com.github.linkav20.finance.domain.model.User
import com.github.linkav20.finance.domain.model.Wallet
import com.github.linkav20.finance.domain.repository.FinanceRepository
import com.github.linkav20.network.data.api.AuthApi
import com.github.linkav20.network.data.api.EventApi
import com.github.linkav20.network.data.models.CommonCalculateExpensesOut
import com.github.linkav20.network.data.models.CommonGetAllUserReceiptIn
import com.github.linkav20.network.data.models.CommonGetPartyExpensesDeadlineIn
import com.github.linkav20.network.data.models.CommonUpdatePartyExpensesDeadlineIn
import com.github.linkav20.network.utils.RetrofitErrorHandler
import com.github.linkav20.network.data.models.CommonGetPartySummaryExpensesIn
import com.github.linkav20.network.data.models.CommonGetPartyWalletIn
import com.github.linkav20.network.data.models.CommonGetPartyWalletOut
import com.github.linkav20.network.data.models.CommonLoadFinanceStateIn
import com.github.linkav20.network.data.models.CommonUpdateFinanceStateIn
import com.github.linkav20.network.data.models.CommonUpdatePartyWalletBankIn
import com.github.linkav20.network.data.models.CommonUpdatePartyWalletCardIn
import com.github.linkav20.network.data.models.CommonUpdatePartyWalletOwnerIn
import com.github.linkav20.network.data.models.CommonUpdatePartyWalletPhoneIn
import com.github.linkav20.network.data.models.CommonUserExpenses
import java.io.InputStream
import java.math.BigDecimal
import java.time.OffsetDateTime
import javax.inject.Inject

class FinanceRepositoryImpl @Inject constructor(
    private val retrofitErrorHandler: RetrofitErrorHandler,
    private val eventApi: EventApi,
    private val authApi: AuthApi
) : FinanceRepository {

    override suspend fun loadFinanceState(id: Long): FinanceState {
        val state = retrofitErrorHandler.apiCall {
            eventApi.loadFinanceState(CommonLoadFinanceStateIn(partyID = id.toInt()))
        }?.financeState
        return when (state) {
            1 -> FinanceState.STEP_1
            2 -> FinanceState.STEP_2
            3 -> FinanceState.STEP_3
            else -> FinanceState.NONE
        }
    }

    override suspend fun getAllExpanses(id: Long): List<User> {
        return listOf(
            User(
                id = 0,
                "Lina",
                listOf(
                    Expense(id = 1, "1", 11.0),
                    Expense(id = 1, "1", 12.0),
                    Expense(id = 1, "1", 81.0)
                ),
            ),
            User(
                id = 1,
                "Maltseva Angelina Sergeevna",
                listOf(
                    Expense(id = 1, "1", 112.0),
                    Expense(id = 1, "1", 1241.0),
                    Expense(id = 1, "1", 14.0)
                ),
            ),
            User(
                id = 2,
                "Lina",
                listOf(
                    Expense(id = 1, "1", 1.0),
                    Expense(id = 1, "1", 14.0),
                    Expense(id = 1, "1", 41.0)
                ),
            ),
            User(
                id = 3,
                "Lina",
                listOf(
                    Expense(id = 1, "1", 12.0),
                    Expense(id = 1, "1", 1.0),
                    Expense(id = 1, "1", 81.0)
                ),
            ),
            User(
                id = 3,
                "Lina", emptyList()
            ),
            User(
                id = 3,
                "Lina",
                emptyList()
            ),

            )
    }

    override suspend fun getExpenses(userId: Long, partyId: Long): List<Expense> =
        retrofitErrorHandler.apiCall {
            eventApi.getAllUserExpenses(
                CommonGetAllUserReceiptIn(
                    partyID = partyId.toInt(),
                    userID = userId.toInt()
                )
            )
        }?.map { it.toDomain() } ?: emptyList()

    override suspend fun sendExpense(
        type: Expense.Type,
        name: String,
        sum: Double,
        receipt: Uri
    ) {

    }

    override suspend fun getTotalSum(partyId: Long) = retrofitErrorHandler.apiCall {
        authApi.getPartySummaryExpenses(
            CommonGetPartySummaryExpensesIn(
                partyID = partyId.toInt()
            )
        )
    }?.totalSum


    override suspend fun deleteExpense(id: Long) {

    }

    override suspend fun updateDeadline(deadline: OffsetDateTime?, partyId: Long) {
        retrofitErrorHandler.apiCall {
            eventApi.updatePartyExepensesDeadline(
                CommonUpdatePartyExpensesDeadlineIn(
                    expensesDeadline = if (deadline == null) "" else DateTimeUtils.toString(deadline),
                    partyID = partyId.toInt()
                )
            )
        }
    }

    override suspend fun getDeadline(partyId: Long): OffsetDateTime? {
        val deadline = retrofitErrorHandler.apiCall {
            eventApi.getExpensesDeadline(
                CommonGetPartyExpensesDeadlineIn(
                    partyID = partyId.toInt()
                )
            )
        }?.deadlineExpenses
        return if (deadline == null) null else DateTimeUtils.fromString(deadline)
    }

    override suspend fun updateFinanceState(state: FinanceState, partyId: Long, userId: Long) {
        retrofitErrorHandler.apiCall {
            eventApi.updateFinanceState(
                CommonUpdateFinanceStateIn(
                    financeState = when (state) {
                        FinanceState.STEP_1 -> 1
                        FinanceState.STEP_2 -> 2
                        FinanceState.STEP_3 -> 3
                        else -> 0
                    },
                    partyID = partyId.toInt(),
                    userID = userId.toInt()
                )
            )
        }
    }

    override suspend fun getExpenseReceipt(id: Long): InputStream {
        TODO("Not yet implemented")
    }

    override suspend fun saveWalletDataCardNumber(partyId: Long, cardNumber: String) {
        retrofitErrorHandler.apiCall {
            eventApi.updatePartyWalletCard(
                CommonUpdatePartyWalletCardIn(
                    cardNumber = cardNumber.toInt(),
                    partyID = partyId.toInt()
                )
            )
        }
    }

    override suspend fun saveWalletDataPhoneNumber(partyId: Long, phoneNumber: String) {
        retrofitErrorHandler.apiCall {
            eventApi.updatePartyWalletPhone(
                CommonUpdatePartyWalletPhoneIn(
                    partyID = partyId.toInt(),
                    phoneNumber = phoneNumber
                )
            )
        }
    }

    override suspend fun saveWalletDataOwner(partyId: Long, owner: String) {
        retrofitErrorHandler.apiCall {
            eventApi.updatePartyWalletOwner(
                CommonUpdatePartyWalletOwnerIn(
                    owner = owner,
                    partyID = partyId.toInt()
                )
            )
        }
    }

    override suspend fun saveWalletDataBank(partyId: Long, bank: String) {
        retrofitErrorHandler.apiCall {
            eventApi.updatePartyWalletBank(
                CommonUpdatePartyWalletBankIn(
                    bank = bank,
                    partyID = partyId.toInt()
                )
            )
        }
    }

    override suspend fun getPartyWallet(partyId: Long) = retrofitErrorHandler.apiCall {
        authApi.getPartyWallet(
            CommonGetPartyWalletIn(
                partyID = partyId.toInt()
            )
        )
    }?.toDomain()

}

private fun CommonUserExpenses.toDomain() = Expense(
    id = expensesID.toLong(),
    name = name ?: "",
    sum = sum?.toDouble() ?: 0.0
)

private fun CommonGetPartyWalletOut.toDomain() = Wallet(
    cardNumber = cardNumber.orEmpty(),
    cardPhone = phoneNumber.orEmpty(),
    owner = cardOwner.orEmpty(),
    bank = bank.orEmpty()
)
