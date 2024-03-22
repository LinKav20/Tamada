package com.github.linkav20.auth.domain.repository

import com.github.linkav20.auth.domain.model.AuthTokenData
import com.github.linkav20.core.domain.entity.UserRole

interface AuthRepository {

    suspend fun login(login: String, password: String): AuthTokenData?

    suspend fun geUserRole(id: Long): UserRole

    suspend fun createUser(
        login: String,
        email: String,
        password: String
    ):  AuthTokenData?
}