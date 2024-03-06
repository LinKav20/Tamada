package com.github.linkav20.auth.data

import com.github.linkav20.auth.domain.model.AuthTokenData
import com.github.linkav20.auth.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(

) : AuthRepository {
    override suspend fun login(login: String, password: String): AuthTokenData? {
        //throw Exception()
        return AuthTokenData("access", "refresh")
    }
}