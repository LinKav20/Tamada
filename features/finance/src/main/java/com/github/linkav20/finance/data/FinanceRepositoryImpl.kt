package com.github.linkav20.finance.data

import android.net.Uri
import com.github.linkav20.finance.domain.model.Expense
import com.github.linkav20.finance.domain.model.FinanceState
import com.github.linkav20.finance.domain.model.User
import com.github.linkav20.finance.domain.repository.FinanceRepository
import java.io.InputStream
import javax.inject.Inject

class FinanceRepositoryImpl @Inject constructor(

) : FinanceRepository {

    override suspend fun loadFinanceState(id: Long): FinanceState {
        return FinanceState.STEP_3
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

    override suspend fun getExpenses(id: Long): User {
        return User(
            id = 3,
            "Lina",
            listOf(
                Expense(id = 1, "Firsayth", 12.4),
                Expense(id = 2, "kjdhiusokl", 333.3),
                Expense(id = 3, "56", 546.0),
                Expense(id = 4, "lollilol", 0.0),
                Expense(id = 5, "lollilol", 0.0),
                Expense(id = 6, "lollilol", 0.0),
                Expense(id = 7, "lollilol", 0.0),
                Expense(id = 8, "lollilol", 0.0)
            )
        )
    }

    override suspend fun sendExpense(
        type: Expense.Type,
        name: String,
        sum: Double,
        receipt: Uri
    ) {

    }

    override suspend fun deleteExpense(id: Long) {

    }

    override suspend fun getExpenseReceipt(id: Long): InputStream {
        TODO("Not yet implemented")
    }

    override suspend fun saveWalletData(partyId: Long, cardNumber: String, phoneNumber: String) {

    }
}
