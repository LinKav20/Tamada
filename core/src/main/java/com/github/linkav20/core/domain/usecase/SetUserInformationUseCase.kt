package com.github.linkav20.core.domain.usecase

import com.github.linkav20.core.domain.entity.User
import com.github.linkav20.core.domain.repository.UserInformationRepository
import javax.inject.Inject

class SetUserInformationUseCase @Inject constructor(
    private val repository: UserInformationRepository,
) {

    fun invoke(user: User) = repository.setUser(user)
}
