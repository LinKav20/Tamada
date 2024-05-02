package com.github.linkav20.core.domain.repository

import com.github.linkav20.core.domain.entity.User

interface UserInformationRepository {

    var userId: Int
    var login: String?
    var avatarId: Int
    var isWallet: Boolean

    fun setUser(user: User)

    fun getUser(): User
}