package com.github.linkav20.auth.domain.usecase

import com.github.linkav20.auth.domain.repository.AuthRepository
import com.github.linkav20.core.domain.entity.UserRole
import com.github.linkav20.core.domain.repository.UserInformationRepository
import com.github.linkav20.core.domain.usecase.GetPartyIdUseCase
import com.github.linkav20.core.domain.usecase.GetRoleUseCase
import timber.log.Timber
import javax.inject.Inject

class GetRoleUseCaseImpl @Inject constructor(
    private val repository: AuthRepository,
    private val getPartyIdUseCase: GetPartyIdUseCase,
    private val userInformationRepository: UserInformationRepository
) : GetRoleUseCase {
    override suspend fun invoke(): UserRole {
        var role = UserRole.GUEST
        try {
            val partyId = getPartyIdUseCase.invoke()?.toInt() ?: return role
            val userId = userInformationRepository.userId
            val parties = repository.getAllParties(userId = userId)
            val currentParty = parties.find { it.id == partyId }
            if (currentParty?.isManager == true) {
                role = UserRole.MANAGER
            }
        } catch (_: Exception) {
            Timber.tag("").e("Cant load user role")
        }
        return role
    }
}
