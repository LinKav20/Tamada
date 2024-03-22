package com.github.linkav20.home.domain.usecase

import com.github.linkav20.home.domain.repository.UserRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend fun invoke() = repository.getUserInfo()
}
