package com.github.linkav20.auth.api.model


data class RegisterResponse(
    val AccessToken: String,
    val RefreshToken: String,
    val Login: String,
    val UserID: Int,
    val ProfileImageID: Int,
)