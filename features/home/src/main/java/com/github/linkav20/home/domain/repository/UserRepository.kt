package com.github.linkav20.home.domain.repository

import com.github.linkav20.home.domain.model.User

interface UserRepository {

    suspend fun getUserInfo(): User

    suspend fun saveUser(user: User)

    suspend fun deleteUser(user: User)

    suspend fun updateUserAvatar(avatar: Int)

    suspend fun updatePassword(
        currentPassword: String,
        newPassword: String
    )
}
