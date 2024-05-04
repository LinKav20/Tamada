package com.github.linkav20.auth.data

import com.github.linkav20.auth.domain.model.AuthTokenData
import com.github.linkav20.auth.domain.model.Party
import com.github.linkav20.auth.domain.model.UserToTokens
import com.github.linkav20.auth.domain.repository.AuthRepository
import com.github.linkav20.core.domain.entity.DomainException
import com.github.linkav20.core.domain.entity.User
import com.github.linkav20.core.domain.entity.UserRole
import com.github.linkav20.network.data.api.AuthApi
import com.github.linkav20.network.data.api.EventApi
import com.github.linkav20.network.data.models.CommonGetUserEventsIn
import com.github.linkav20.network.data.models.CommonGetUserPartiesOut
import com.github.linkav20.network.data.models.CommonLoginIn
import com.github.linkav20.network.data.models.CommonLoginOut
import com.github.linkav20.network.data.models.CommonRefreshOut
import com.github.linkav20.network.data.models.CommonRegisterIn
import com.github.linkav20.network.data.models.CommonRegisterOut
import com.github.linkav20.network.utils.RetrofitErrorHandler
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val eventApi: EventApi,
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

    override suspend fun getAllParties(userId: Int): List<Party> = retrofitErrorHandler.apiCall {
        eventApi.getUserEvents(CommonGetUserEventsIn(userID = userId))
    }?.map { it.toDomain() } ?: emptyList()


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

private fun CommonGetUserPartiesOut.toDomain() = Party(
    id = partyID,
    isManager = isManager
)
