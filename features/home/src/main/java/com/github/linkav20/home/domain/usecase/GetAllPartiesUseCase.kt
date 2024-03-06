package com.github.linkav20.home.domain.usecase

import com.github.linkav20.home.domain.repository.PartyInfoRepository
import javax.inject.Inject

class GetAllPartiesUseCase
    @Inject
    constructor(
        private val repository: PartyInfoRepository,
    ) {
        suspend fun invoke() = repository.getAllParties()
    }
