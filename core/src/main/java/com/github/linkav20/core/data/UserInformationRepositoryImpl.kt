package com.github.linkav20.core.data

import com.github.linkav20.core.domain.entity.User
import com.github.linkav20.core.domain.repository.UserInformationRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserInformationRepositoryImpl @Inject constructor(

) : UserInformationRepository {

    private var _user: User? = null
    override fun getUser(): User? = _user

    override fun setUser(user: User) {
        _user = user
    }
}
