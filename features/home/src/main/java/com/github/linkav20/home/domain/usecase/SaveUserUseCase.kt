package com.github.linkav20.home.domain.usecase

import com.github.linkav20.home.domain.model.User
import com.github.linkav20.home.domain.repository.UserRepository
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend fun invoke(user: User) = repository.saveUser(user)
}
