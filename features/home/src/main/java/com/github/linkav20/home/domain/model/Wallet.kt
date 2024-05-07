package com.github.linkav20.home.domain.model

data class Wallet(
    val cardNumber: String?,
    val cardPhoneNumber: String?,
    val cardOwner: String?,
    val cardBank: String?
)

fun Wallet.isNotEmpty() = !cardBank.isNullOrEmpty()
        && !cardOwner.isNullOrEmpty()
        && !cardPhoneNumber.isNullOrEmpty()
        && !cardNumber.isNullOrEmpty()
