package com.github.linkav20.home.domain.model

data class User(
    val login: String,
    val email: String,
    val avatar: Int,
    val cardNumber: String?,
    val cardPhoneNumber: String?,
    val cardOwner: String?,
    val cardBank: String?
)
