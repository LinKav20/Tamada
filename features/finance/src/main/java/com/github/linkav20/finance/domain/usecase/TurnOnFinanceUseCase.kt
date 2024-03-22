package com.github.linkav20.finance.domain.usecase

import com.github.linkav20.finance.domain.repository.FinanceRepository
import javax.inject.Inject

class TurnOnFinanceUseCase @Inject constructor(
    private val repository: FinanceRepository
) {

    fun invoke(id: Long) {

    }
}
