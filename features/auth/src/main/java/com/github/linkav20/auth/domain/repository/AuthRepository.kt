package com.github.linkav20.auth.domain.repository

import com.github.linkav20.auth.domain.model.AuthTokenData

interface AuthRepository {

    suspend fun login(login: String, password: String) : AuthTokenData?
}