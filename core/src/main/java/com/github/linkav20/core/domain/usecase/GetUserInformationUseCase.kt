package com.github.linkav20.core.domain.usecase

import com.github.linkav20.core.domain.repository.UserInformationRepository
import javax.inject.Inject

class GetUserInformationUseCase@Inject constructor(
    private val repository: UserInformationRepository
) {

    fun invoke() = repository.getUser()
}
