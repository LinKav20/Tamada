package com.github.linkav20.guests.domain.usecase

import java.util.UUID
import javax.inject.Inject

class GetLinkUseCase @Inject constructor() {
    suspend fun invoke() = "https://${UUID.randomUUID()}"
}