package com.github.linkav20.finance.domain.model

import com.github.linkav20.core.domain.entity.UserRole

data class User(
    val id: Long,
    val name: String,
    val expenses: List<Expense>,
    val role: UserRole = UserRole.GUEST,
    val avatar: Int? = 0
)