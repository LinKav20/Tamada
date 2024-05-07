package com.github.linkav20.home.domain.usecase

import com.github.linkav20.core.domain.repository.UserInformationRepository
import javax.inject.Inject

class UpdateUserInfoIsWalletUseCase @Inject constructor(
    private val userInformationRepository: UserInformationRepository
) {
    fun invoke(isWallet: Boolean) {
        userInformationRepository.isWallet = isWallet
    }
}
