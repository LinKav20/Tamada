package com.github.linkav20.auth.data

import com.github.linkav20.auth.api.model.LoginRequest
import com.github.linkav20.auth.api.model.LoginResponse
import com.github.linkav20.auth.api.model.RefreshResponse
import com.github.linkav20.auth.api.model.RegisterRequest
import com.github.linkav20.auth.api.model.RegisterResponse
import com.github.linkav20.auth.domain.model.AuthTokenData
import com.github.linkav20.auth.domain.model.UserToTokens
import com.github.linkav20.auth.domain.repository.AuthRepository
import com.github.linkav20.core.domain.entity.User
import com.github.linkav20.core.domain.entity.UserRole
import com.github.linkav20.network.auth.api.AuthApi
import com.github.linkav20.network.utils.RetrofitErrorHandler
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val retrofitErrorHandler: RetrofitErrorHandler
) : AuthRepository {
    override suspend fun login(login: String, password: String) =
        retrofitErrorHandler.apiCall {
            authApi.login(
                LoginRequest(
                    login = login,
                    password = password
                )
            )
        }.toDomain()

    override suspend fun geUserRole(id: Long): UserRole {
        return UserRole.MANAGER
    }

    override suspend fun refreshToken() = retrofitErrorHandler.apiCall {
        authApi.refreshToken()
    }.toDomain()

    override suspend fun createUser(
        login: String,
        email: String,
        password: String
    ) = retrofitErrorHandler.apiCall {
        authApi.registerUser(
            RegisterRequest(
                login = login,
                email = email,
                password = password
            )
        )
    }.toDomain()
}

private fun RegisterResponse.toDomain() = UserToTokens(
    user = User(
        login = Login,
        id = UserID,
        avatarId = ProfileImageID,
        isWallet = false
    ),
    tokens = AuthTokenData(
        accessToken = AccessToken,
        refreshToken = RefreshToken
    )
)

private fun LoginResponse.toDomain() = UserToTokens(
    user = User(
        login = Login,
        id = UserID,
        avatarId = ProfileImageID,
        isWallet = HasDebitCard
    ),
    tokens = AuthTokenData(
        accessToken = AccessToken,
        refreshToken = RefreshToken
    )
)

private fun RefreshResponse.toDomain() = AuthTokenData(
    accessToken = AccessToken,
    refreshToken = RefreshToken
)
