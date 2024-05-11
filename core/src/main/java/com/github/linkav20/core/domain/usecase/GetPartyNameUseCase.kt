package com.github.linkav20.core.domain.usecase

import com.github.linkav20.core.domain.repository.PartyIdRepository
import javax.inject.Inject

class GetPartyNameUseCase @Inject constructor(
    private val repository: PartyIdRepository
) {

    fun invoke() = repository.getPartyName()
}