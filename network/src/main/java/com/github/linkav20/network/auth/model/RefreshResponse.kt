package com.github.linkav20.auth.api.model


data class RefreshResponse(
    val AccessToken: String,
    val RefreshToken: String
)