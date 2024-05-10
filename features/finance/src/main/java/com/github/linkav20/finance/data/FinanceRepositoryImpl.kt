package com.github.linkav20.finance.data

import android.util.Log
import com.github.linkav20.core.domain.entity.UserRole
import com.github.linkav20.core.utils.DateTimeUtils
import com.github.linkav20.finance.domain.model.Calculate
import com.github.linkav20.finance.domain.model.Expense
import com.github.linkav20.finance.domain.model.FinanceState
import com.github.linkav20.finance.domain.model.User
import com.github.linkav20.finance.domain.model.Wallet
import com.github.linkav20.finance.domain.repository.FinanceRepository
import com.github.linkav20.network.data.api.AuthApi
import com.github.linkav20.network.data.api.EventApi
import com.github.linkav20.network.data.models.CommonCalculateExpensesIn
import com.github.linkav20.network.data.models.CommonCalculateExpensesOut
import com.github.linkav20.network.data.models.CommonGetAllUserReceiptIn
import com.github.linkav20.network.data.models.CommonGetPartiesGuestsIn
import com.github.linkav20.network.data.models.CommonGetPartiesGuestsOut
import com.github.linkav20.network.data.models.CommonGetPartyExpensesDeadlineIn
import com.github.linkav20.network.data.models.CommonUpdatePartyExpensesDeadlineIn
import com.github.linkav20.network.utils.RetrofitErrorHandler
import com.github.linkav20.network.data.models.CommonGetPartySummaryExpensesIn
import com.github.linkav20.network.data.models.CommonGetPartyWalletIn
import com.github.linkav20.network.data.models.CommonGetPartyWalletOut
import com.github.linkav20.network.data.models.CommonGetUserReceiptIn
import com.github.linkav20.network.data.models.CommonLoadFinanceStateIn
import com.github.linkav20.network.data.models.CommonUpdateFinanceStateIn
import com.github.linkav20.network.data.models.CommonUpdatePartyWalletBankIn
import com.github.linkav20.network.data.models.CommonUpdatePartyWalletCardIn
import com.github.linkav20.network.data.models.CommonUpdatePartyWalletOwnerIn
import com.github.linkav20.network.data.models.CommonUpdatePartyWalletPhoneIn
import com.github.linkav20.network.data.models.CommonUploadReceiptInfoNameIn
import com.github.linkav20.network.data.models.CommonUploadReceiptInfoSumIn
import com.github.linkav20.network.data.models.CommonUserExpenses
import okhttp3.MultipartBody
import java.io.InputStream
import java.time.OffsetDateTime
import javax.inject.Inject

private const val LOCAL_DOUBLE_DIVIDER = ','
private const val SERVER_DOUBLE_DIVIDER = '.'

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

    override suspend fun getExpenses(userId: Long, partyId: Long): List<Expense> =
        retrofitErrorHandler.apiCall {
            eventApi.getAllUserExpenses(
                CommonGetAllUserReceiptIn(
                    partyID = partyId.toInt(),
                    userID = userId.toInt()
                )
            )
        }?.map { it.toDomain() } ?: emptyList()

    override suspend fun createExpense(
        type: Expense.Type,
        name: String,
        sum: Double,
        receipt: MultipartBody.Part,
        partyId: Long,
        userId: Long
    ) {
        retrofitErrorHandler.apiCall {
            eventApi.createReceipt(
                file = receipt,
                name = name,
                sum = sum.toString().replace(LOCAL_DOUBLE_DIVIDER, SERVER_DOUBLE_DIVIDER),
                partyId = partyId.toString(),
                userId = userId.toString(),
                type = when (type) {
                    Expense.Type.SUM -> "SUM"
                    else -> "DEPT"
                }
            )
        }
    }

    override suspend fun updateExpenseName(
        expenseId: Long,
        name: String,
        partyId: Long,
        userId: Long
    ) {
        retrofitErrorHandler.apiCall {
            eventApi.updateReceiptName(
                CommonUploadReceiptInfoNameIn(
                    expenseID = expenseId.toInt(),
                    newName = name,
                    partyID = partyId.toInt(),
                    userID = userId.toInt()
                )
            )
        }
    }

    override suspend fun updateExpenseSum(
        expenseId: Long,
        sum: Double,
        partyId: Long,
        userId: Long
    ) {
        retrofitErrorHandler.apiCall {
            eventApi.updateReceiptSum(
                CommonUploadReceiptInfoSumIn(
                    userID = userId.toInt(),
                    partyID = partyId.toInt(),
                    expenseID = expenseId.toInt(),
                    newSum = (sum * 100).toLong()
                )
            )
        }
    }

    override suspend fun updateExpenseReceipt(
        expenseId: Long,
        receipt: MultipartBody.Part,
        partyId: Long,
        userId: Long
    ) {
        TODO("Not yet implemented")
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

    override suspend fun getExpenseReceipt(
        expenseId: Long,
        partyId: Long,
        userId: Long
    ): InputStream? =
        retrofitErrorHandler.apiCall {
            eventApi.getUserReceipt(
                CommonGetUserReceiptIn(
                    expenseID = expenseId.toInt(),
                    partyID = partyId.toInt(),
                    userID = userId.toInt()
                )
            )
        }?.byteStream()


    override suspend fun saveWalletDataCardNumber(partyId: Long, cardNumber: String) {
        retrofitErrorHandler.apiCall {
            eventApi.updatePartyWalletCard(
                CommonUpdatePartyWalletCardIn(
                    cardNumber = cardNumber.toLong(),
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

    override suspend fun getUsers(partyId: Long) = retrofitErrorHandler.apiCall {
        eventApi.getPartiesGuests(CommonGetPartiesGuestsIn(partyID = partyId.toInt()))
    }?.map { it.toDomain() } ?: emptyList()

    override suspend fun calculateExpenses(partyId: Long, userId: Long): Calculate? =
        retrofitErrorHandler.apiCall {
            eventApi.calculateExpenses(
                CommonCalculateExpensesIn(
                    partyID = partyId.toInt(),
                    userID = userId.toInt()
                )
            )
        }?.toDomain()


}

private fun CommonCalculateExpensesOut.toDomain() = Calculate(
    forPerson = expenseForUser,
    personDept = userDebtString,
    dept = userDebt
)


private fun CommonGetPartiesGuestsOut.toDomain() = User(
    id = userID?.toLong() ?: 0,
    name = login ?: "",
    role = when (role) {
        "manager" -> UserRole.MANAGER
        "creator" -> UserRole.CREATOR
        else -> UserRole.GUEST
    },
    avatar = avatarID ?: 0
)

private fun CommonUserExpenses.toDomain() = Expense(
    id = expensesID.toLong(),
    name = name ?: "",
    sum = sum?.toDouble() ?: 0.0
)

private fun CommonGetPartyWalletOut.toDomain() = Wallet(
    cardNumber = if (cardNumber == "0") "" else cardNumber.orEmpty(),
    cardPhone = phoneNumber.orEmpty(),
    owner = cardOwner.orEmpty(),
    bank = bank.orEmpty()
)
