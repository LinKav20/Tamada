package com.github.linkav20.guests.domain.repository

import com.github.linkav20.guests.domain.model.User

interface UsersRepository {
    suspend fun getUsers(partyId: Long): List<User>

    suspend fun sendUsers(partyId: Long, users: List<User>)
}
