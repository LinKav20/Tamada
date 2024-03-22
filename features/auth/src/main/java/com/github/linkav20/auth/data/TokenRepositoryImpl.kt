package com.github.linkav20.auth.data

import com.github.linkav20.auth.domain.repository.TokenRepository
import com.github.linkav20.core.data.SecureStorage
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val secureStorage: SecureStorage
) : TokenRepository {

    override var accessToken: String?
        get() = secureStorage.getString(SecureStorage.TOKEN_KEY)
        set(value) = secureStorage.putString(SecureStorage.TOKEN_KEY, value)

    override var refreshToken: String?
        get() = secureStorage.getString(SecureStorage.REFRESH_TOKEN_KEY)
        set(value) = secureStorage.putString(SecureStorage.REFRESH_TOKEN_KEY, value)

    override suspend fun clear() = secureStorage.clear()
}
