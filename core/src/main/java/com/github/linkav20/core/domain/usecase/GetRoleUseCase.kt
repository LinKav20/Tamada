package com.github.linkav20.core.domain.usecase

import com.github.linkav20.core.domain.entity.UserRole

interface GetRoleUseCase {

    suspend fun invoke() : UserRole
}
