package com.github.linkav20.home.data

import com.github.linkav20.home.domain.model.User
import com.github.linkav20.home.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(

) : UserRepository {
    override suspend fun getUserInfo(): User {
        return User(
            login = "Lina",
            email = "maltseva-as@mail.ru",
            avatar = 10,
            cardNumber = null,
            cardOwner = null,
            cardBank = null,
            cardPhoneNumber = null
        )
    }

    override suspend fun saveUser(user: User) {

    }

    override suspend fun deleteUser(user: User) {

    }

    override suspend fun updateUserAvatar(avatar: Int) {

    }

    override suspend fun updatePassword(currentPassword: String, newPassword: String) {

    }
}
