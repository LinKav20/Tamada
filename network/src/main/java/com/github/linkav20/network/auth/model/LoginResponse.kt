package com.github.linkav20.auth.api.model


data class LoginResponse(
    val AccessToken: String,
    val RefreshToken: String,
    val Login: String,
    val UserID: Int,
    val ProfileImageID: Int,
    val HasDebitCard: Boolean
)
