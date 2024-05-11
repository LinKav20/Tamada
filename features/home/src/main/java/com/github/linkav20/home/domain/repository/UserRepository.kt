package com.github.linkav20.home.domain.repository

import com.github.linkav20.home.domain.model.User
import com.github.linkav20.home.domain.model.Wallet

interface UserRepository {

    suspend fun getUserWalletInfo(userId: Int): Wallet?

    suspend fun saveUser(user: User)

    suspend fun deleteUser(userId: Long, password: String)

    suspend fun updateUserAvatar(avatar: Int, userId: Int)

    suspend fun updatePassword(
        currentPassword: String,
        newPassword: String
    )

    suspend fun updateLogin(login: String, userId: Int)

    suspend fun updateCardNumber(number: String, userId: Int)

    suspend fun updateCardOwner(owner: String, userId: Int)

    suspend fun updateCardBank(bank: String, userId: Int)

    suspend fun updateCardPhoneNumber(phone: String, userId: Int)
}
