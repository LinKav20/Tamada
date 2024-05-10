package com.github.linkav20.finance.domain.usecase

import android.content.Context
import com.github.linkav20.core.domain.repository.UserInformationRepository
import com.github.linkav20.core.domain.usecase.GetPartyIdUseCase
import com.github.linkav20.finance.R
import com.github.linkav20.finance.domain.model.Expense
import com.github.linkav20.finance.domain.model.UserUI
import com.github.linkav20.finance.domain.repository.FinanceRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.math.abs

class GetAllUsersWithExpenses @Inject constructor(
    private val repository: FinanceRepository,
    private val userInformationRepository: UserInformationRepository,
    private val getPartyIdUseCase: GetPartyIdUseCase,
    private val getCalculateResultUseCase: GetCalculateResultUseCase,
    @ApplicationContext private val context: Context
) {

    suspend fun invoke(step: Int): List<UserUI> {
        val partyId = getPartyIdUseCase.invoke() ?: return emptyList()
        val users = repository.getUsers(partyId = partyId)
        val usersUI = mutableListOf<UserUI>()
        for (user in users) {
            val expenses = repository.getExpenses(userId = user.id, partyId = partyId)
            val calculation = getCalculateResultUseCase.invoke(user.id)
            val userUI = UserUI(
                id = user.id,
                name = user.name,
                expenses = expenses,
                focusSum = Expense(
                    id = 0,
                    name = context.getString(R.string.progress_user_total_sum),
                    sum = when (step) {
                        1 -> if (calculation != null && calculation.dept > 0) calculation.dept else 0.0
                        2 -> if (calculation != null && calculation.dept <= 0) abs(calculation.dept) else 0.0
                        else -> expenses.sumOf { it.sum }
                    }
                ),
                isMe = user.id == userInformationRepository.userId.toLong()
            )
            usersUI.add(userUI)
        }
        return usersUI.sortedByDescending { it.isMe }
    }
}
