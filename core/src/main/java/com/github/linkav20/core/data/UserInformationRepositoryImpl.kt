package com.github.linkav20.core.data

import com.github.linkav20.core.domain.entity.User
import com.github.linkav20.core.domain.repository.UserInformationRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserInformationRepositoryImpl @Inject constructor(
    private val secureStorage: SecureStorage
) : UserInformationRepository {

    override var userId: Int
        get() = secureStorage.getInt(SecureStorage.USER_ID)
        set(value) = secureStorage.putInt(SecureStorage.USER_ID, value)

    override var login: String?
        get() = secureStorage.getString(SecureStorage.USER_LOGIN)
        set(value) = secureStorage.putString(SecureStorage.USER_LOGIN, value)

    override var avatarId: Int
        get() = secureStorage.getInt(SecureStorage.USER_AVATAR)
        set(value) = secureStorage.putInt(SecureStorage.USER_AVATAR, value)

    override var isWallet: Boolean
        get() = secureStorage.getBoolean(SecureStorage.USER_IS_WALLED)
        set(value) = secureStorage.putBoolean(SecureStorage.USER_IS_WALLED, value)

    override fun getUser() = User(
        id = userId,
        avatarId = avatarId,
        isWallet = isWallet,
        login = login ?: ""
    )


    override fun setUser(user: User) {
        userId = user.id
        avatarId = user.avatarId
        login = user.login
        isWallet = user.isWallet
    }
}
/*
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

 */