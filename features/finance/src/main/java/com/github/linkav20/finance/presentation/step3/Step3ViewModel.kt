package com.github.linkav20.finance.presentation.step3

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.linkav20.core.domain.entity.ReactionStyle
import com.github.linkav20.core.domain.entity.User
import com.github.linkav20.core.domain.entity.UserRole
import com.github.linkav20.core.domain.repository.UserInformationRepository
import com.github.linkav20.core.domain.usecase.GetRoleUseCase
import com.github.linkav20.core.notification.ReactUseCase
import com.github.linkav20.finance.R
import com.github.linkav20.finance.domain.usecase.GetCalculateResultUseCase
import com.github.linkav20.finance.domain.usecase.GetMyExpenseUseCase
import com.github.linkav20.finance.domain.usecase.GetTotalPartySumUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class Step3ViewModel @Inject constructor(
    private val reactUseCase: ReactUseCase,
    private val getCalculateResultUseCase: GetCalculateResultUseCase,
    private val getMyExpenseUseCase: GetMyExpenseUseCase,
    private val userInformationRepository: UserInformationRepository,
    private val getTotalPartySumUseCase: GetTotalPartySumUseCase,
    private val getUserRoleUseCase: GetRoleUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(Step3State())
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    fun onDeptDoneClick() {
        _state.update { it.copy(isDept = !state.value.isDept) }
        reactUseCase.invoke(
            title = context.getString(R.string.step3_dept_done_success_popup),
            style = ReactionStyle.SUCCESS
        )
    }

    private fun loadData() = viewModelScope.launch {
        try {
            _state.update { it.copy(loading = true) }
            val myTotal = getMyExpenseUseCase.invoke().sumOf { it.sum }
            val total = getTotalPartySumUseCase.invoke()
            val calculation =
                getCalculateResultUseCase.invoke(userInformationRepository.userId.toLong())
            val role = getUserRoleUseCase.invoke()
            _state.update {
                it.copy(
                    myTotal = myTotal,
                    sum = total,
                    dept = calculation?.dept?.let { it1 -> abs(it1) },
                    calculation = calculation,
                    isDept = if (calculation != null) calculation.dept <= 0 else false,
                    isManager = role == UserRole.MANAGER || role == UserRole.CREATOR
                )
            }
        } catch (e: Exception) {
            _state.update { it.copy(error = e) }
        } finally {
            _state.update { it.copy(loading = false) }
        }
    }
}