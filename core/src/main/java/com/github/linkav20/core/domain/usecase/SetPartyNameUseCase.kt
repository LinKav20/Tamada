package com.github.linkav20.core.domain.usecase

import com.github.linkav20.core.domain.repository.BottomNavigationRepository
import com.github.linkav20.core.domain.repository.PartyIdRepository
import javax.inject.Inject

class SetPartyNameUseCase @Inject constructor(
    private val repository: PartyIdRepository,
    private val navigationRepository: BottomNavigationRepository
) {

    fun invoke(name: String) {
        val previousId = repository.getPartyName()
        if (previousId != name) {
            navigationRepository.updateAll()
        }
        repository.setPartyName(name)
    }
}
