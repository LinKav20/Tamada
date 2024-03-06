package com.github.linkav20.auth.domain.model

data class AuthTokenData(
    val accessToken: String,
    val refreshToken: String
)
