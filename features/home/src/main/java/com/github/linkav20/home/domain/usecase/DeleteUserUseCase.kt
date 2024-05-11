package com.github.linkav20.home.domain.usecase

import com.github.linkav20.home.domain.model.User
import com.github.linkav20.home.domain.repository.UserRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend fun invoke(user: User, password: String) = repository.deleteUser(
        userId = user.id.toLong(),
        password = password
    )
}
