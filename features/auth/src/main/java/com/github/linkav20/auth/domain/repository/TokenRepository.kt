package com.github.linkav20.auth.domain.repository

interface TokenRepository {
    var accessToken: String?

    var refreshToken: String?

    suspend fun clear()
}
