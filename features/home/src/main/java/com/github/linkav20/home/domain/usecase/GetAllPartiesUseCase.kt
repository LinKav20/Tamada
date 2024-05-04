package com.github.linkav20.home.domain.usecase

import android.util.Log
import com.github.linkav20.core.domain.repository.UserInformationRepository
import com.github.linkav20.home.domain.model.Party
import com.github.linkav20.home.domain.repository.PartyInfoRepository
import javax.inject.Inject

class GetAllPartiesUseCase @Inject constructor(
    private val repository: PartyInfoRepository,
    private val userInformationRepository: UserInformationRepository
) {
    suspend fun invoke(): List<Party> {
        val userId = userInformationRepository.userId
        return repository.getAllParties(userId)
    }
}
