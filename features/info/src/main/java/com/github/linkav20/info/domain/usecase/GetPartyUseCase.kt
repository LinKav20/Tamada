package com.github.linkav20.info.domain.usecase

import com.github.linkav20.info.domain.repository.PartyRepository
import javax.inject.Inject

class GetPartyUseCase @Inject constructor(
    private val repository: PartyRepository,
) {

    suspend fun invoke(id: Long) = repository.getParty(id)
}