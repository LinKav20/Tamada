package com.github.linkav20.core.domain.repository

import com.github.linkav20.core.domain.entity.User

interface UserInformationRepository {

    fun setUser(user: User)

    fun getUser(): User?
}