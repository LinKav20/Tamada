package com.github.linkav20.auth.data

import com.github.linkav20.auth.domain.model.AuthTokenData
import com.github.linkav20.auth.domain.repository.AuthRepository
import com.github.linkav20.core.domain.entity.UserRole
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(

) : AuthRepository {
    override suspend fun login(login: String, password: String): AuthTokenData? {
        //throw Exception()
        return AuthTokenData("access", "refresh")
    }

    override suspend fun geUserRole(id: Long): UserRole {
        return UserRole.MANAGER
    }

    override suspend fun createUser(
        login: String,
        email: String,
        password: String
    ): AuthTokenData? {
        return AuthTokenData("access", "refresh")
    }
}