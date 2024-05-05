package com.github.linkav20.guests.domain.repository

import com.github.linkav20.guests.domain.model.User

interface UsersRepository {
    suspend fun getUsers(partyId: Long): List<User>

    suspend fun updateUserRole(
        partyId: Long,
        actionFrom: Int,
        userId: Int,
        role: User.UserRole
    )

    suspend fun deleteUser(
        partyId: Long,
        actionFrom: Int,
        userId: Int
    )

    suspend fun getInviteLink(
        partyId: Long,
    ): String?
}
