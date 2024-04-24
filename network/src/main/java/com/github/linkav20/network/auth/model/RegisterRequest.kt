package com.github.linkav20.auth.api.model

data class RegisterRequest(
    val login: String,
    val email: String,
    val password: String
)