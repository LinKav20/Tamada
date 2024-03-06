package com.github.linkav20.core.domain.usecase

import com.github.linkav20.core.domain.repository.BottomNavigationRepository
import com.github.linkav20.core.domain.repository.PartyIdRepository
import javax.inject.Inject

class SetPartyIdUseCase @Inject constructor(
    private val repository: PartyIdRepository,
    private val navigationRepository: BottomNavigationRepository
) {

    fun invoke(id: Long) {
        val previousId = repository.getPartyId()
        if (previousId != id) {
            navigationRepository.updateAll()
        }
        repository.setPartyId(id)
    }
}