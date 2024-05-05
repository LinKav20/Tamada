package com.github.linkav20.home.domain.repository

import com.github.linkav20.home.domain.model.User
import com.github.linkav20.home.domain.model.Wallet

interface UserRepository {

    suspend fun getUserWalletInfo(userId: Int): Wallet?

    suspend fun saveUser(user: User)

    suspend fun deleteUser(user: User)

    suspend fun updateUserAvatar(avatar: Int, userId: Int)

    suspend fun updatePassword(
        currentPassword: String,
        newPassword: String
    )
}
