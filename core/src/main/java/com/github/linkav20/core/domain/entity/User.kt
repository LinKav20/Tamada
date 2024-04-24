package com.github.linkav20.core.domain.entity

data class User(
    val id: Int,
    val login: String,
    val avatarId: Int,
    val isWallet: Boolean
)
