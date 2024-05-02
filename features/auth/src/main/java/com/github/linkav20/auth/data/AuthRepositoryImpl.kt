package com.github.linkav20.auth.data

import com.github.linkav20.auth.domain.model.AuthTokenData
import com.github.linkav20.auth.domain.model.UserToTokens
import com.github.linkav20.auth.domain.repository.AuthRepository
import com.github.linkav20.core.domain.entity.DomainException
import com.github.linkav20.core.domain.entity.User
import com.github.linkav20.core.domain.entity.UserRole
import com.github.linkav20.network.data.api.AuthApi
import com.github.linkav20.network.data.models.CommonLoginIn
import com.github.linkav20.network.data.models.CommonLoginOut
import com.github.linkav20.network.data.models.CommonRefreshOut
import com.github.linkav20.network.data.models.CommonRegisterIn
import com.github.linkav20.network.data.models.CommonRegisterOut
import com.github.linkav20.network.utils.RetrofitErrorHandler
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val retrofitErrorHandler: RetrofitErrorHandler
) : AuthRepository {
    override suspend fun login(login: String, password: String) =
        retrofitErrorHandler.apiCall {
            authApi.loginAcc(
                CommonLoginIn(
                    login = login,
                    password = password
                )
            )
        }?.toDomain() ?: throw DomainException.NoDataException

    override suspend fun geUserRole(id: Long): UserRole {
        return UserRole.MANAGER
    }

    override suspend fun refreshToken() = retrofitErrorHandler.apiCall {
        authApi.refreshToken()
    }?.toDomain()


    override suspend fun createUser(
        login: String,
        email: String,
        password: String
    ) = retrofitErrorHandler.apiCall {
        authApi.registerAcc(
            CommonRegisterIn(
                login = login,
                email = email,
                password = password
            )
        )
    }?.toDomain() ?: throw DomainException.NoDataException
}

private fun CommonRegisterOut.toDomain() = UserToTokens(
    user = User(
        login = login,
        id = userID,
        avatarId = profileImageID,
        isWallet = false
    ),
    tokens = AuthTokenData(
        accessToken = accessToken.orEmpty(),
        refreshToken = refreshToken.orEmpty()
    )
)

private fun CommonLoginOut.toDomain() = UserToTokens(
    user = User(
        login = login,
        id = userID,
        avatarId = profileImageID,
        isWallet = hasDebitCard ?: false
    ),
    tokens = AuthTokenData(
        accessToken = accessToken.orEmpty(),
        refreshToken = refreshToken.orEmpty()
    )
)

private fun CommonRefreshOut.toDomain() = AuthTokenData(
    accessToken = accessToken,
    refreshToken = refreshToken
)
