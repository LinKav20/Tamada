package com.github.linkav20.home.domain.model

data class User(
    val id: Int,
    val login: String,
    val email: String,
    val avatar: Int,
    val wallet: Wallet? = null
)
