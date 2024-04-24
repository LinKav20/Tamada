package com.github.linkav20.auth.domain.model

import com.github.linkav20.core.domain.entity.User

data class UserToTokens(
    val user: User,
    val tokens: AuthTokenData?
)
