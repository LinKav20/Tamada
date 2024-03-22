package com.github.linkav20.auth.domain.usecase

import com.github.linkav20.auth.domain.repository.AuthRepository
import com.github.linkav20.core.domain.entity.UserRole
import com.github.linkav20.core.domain.usecase.GetPartyIdUseCase
import com.github.linkav20.core.domain.usecase.GetRoleUseCase
import timber.log.Timber
import javax.inject.Inject

class GetRoleUseCaseImpl @Inject constructor(
    private val repository: AuthRepository,
    private val getPartyIdUseCase: GetPartyIdUseCase
) : GetRoleUseCase {
    override suspend fun invoke(): UserRole {
        var role = UserRole.GUEST
        try {
            val id = getPartyIdUseCase.invoke() ?: return role
            role = repository.geUserRole(id)
            if (id == 1L) {
                role = UserRole.GUEST
            }
        } catch (_: Exception) {
            Timber.tag("").e("Cant load user role")
        }
        return role
    }
}
