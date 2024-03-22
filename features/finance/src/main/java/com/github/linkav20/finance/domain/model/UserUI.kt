package com.github.linkav20.finance.domain.model

import com.github.linkav20.core.domain.entity.UserRole

data class UserUI(
    val id: Long,
    val name: String,
    val expenses: List<Expense>,
    val focusSum: Expense,
    val role: UserRole = UserRole.GUEST,
    val isMe: Boolean = false,
    val isExpanded: Boolean = false,
    val cardNumber: String = "",
    val cardPhoneNumber: String = "",
    val cardBank: String = "",
    val cardUser: String = "",
    val avatar: Int? = 0
)
