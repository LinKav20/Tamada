package com.github.linkav20.guests.data

import android.util.Log
import com.github.linkav20.guests.domain.model.User
import com.github.linkav20.guests.domain.repository.UsersRepository
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor() : UsersRepository {

    override suspend fun sendUsers(partyId: Long, users: List<User>) {
        Log.d("SERVER_", "Send ${users}")
    }
    override suspend fun getUsers(partyId: Long): List<User> {
        return listOf(
            User(
                name = "Lina",
                role = User.UserRole.MANAGER,
                status = User.ArriveStatus.EXACTLY
            ),
            User(
                name = "Lina2",
                role = User.UserRole.GUEST,
                status = User.ArriveStatus.EXACTLY
            ),
            User(
                name = "Lina3",
                role = User.UserRole.GUEST,
                status = User.ArriveStatus.PROBABLY
            ),
            User(
                name = "Lina4",
                role = User.UserRole.GUEST,
                status = User.ArriveStatus.PROBABLY
            ),
            User(
                name = "Lina5",
                role = User.UserRole.GUEST,
                status = User.ArriveStatus.PROBABLY
            ), User(
                name = "Lina3",
                role = User.UserRole.GUEST,
                status = User.ArriveStatus.PROBABLY
            ),
            User(
                name = "Lina4",
                role = User.UserRole.GUEST,
                status = User.ArriveStatus.PROBABLY
            ),
            User(
                name = "Lina5",
                role = User.UserRole.GUEST,
                status = User.ArriveStatus.PROBABLY
            ), User(
                name = "Lina3",
                role = User.UserRole.GUEST,
                status = User.ArriveStatus.PROBABLY
            ),
            User(
                name = "Lina4",
                role = User.UserRole.GUEST,
                status = User.ArriveStatus.PROBABLY
            ),
            User(
                name = "Lina5",
                role = User.UserRole.GUEST,
                status = User.ArriveStatus.PROBABLY
            )
        )
    }
}