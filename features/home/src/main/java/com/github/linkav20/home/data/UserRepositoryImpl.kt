package com.github.linkav20.home.data

import com.github.linkav20.home.domain.model.Wallet
import com.github.linkav20.home.domain.model.User
import com.github.linkav20.home.domain.repository.UserRepository
import com.github.linkav20.network.data.api.AuthApi
import com.github.linkav20.network.utils.RetrofitErrorHandler
import ru.ozon.ozon_pvz.network.my.models.CommonGetUserWalletIn
import ru.ozon.ozon_pvz.network.my.models.CommonGetUserWalletOut
import ru.ozon.ozon_pvz.network.my.models.CommonUpdateUserAvatarIn
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val retrofitErrorHandler: RetrofitErrorHandler,
    private val authApi: AuthApi
) : UserRepository {
    override suspend fun getUserWalletInfo(userId: Int): Wallet? = retrofitErrorHandler.apiCall {
        authApi.getUserWallet(CommonGetUserWalletIn(userID = userId))
    }?.toDomain()

    override suspend fun saveUser(user: User) {

    }

    override suspend fun deleteUser(user: User) {
//        retrofitErrorHandler.apiCall {
//            authApi.deleteUser(CommonDeleteUserIn())
//        }
    }

    override suspend fun updateUserAvatar(avatar: Int, userId: Int) {
        retrofitErrorHandler.apiCall {
            authApi.updateUserAvatar(
                CommonUpdateUserAvatarIn(
                    newAvatarID = avatar,
                    userID = userId
                )
            )
        }
    }

    override suspend fun updatePassword(currentPassword: String, newPassword: String) {

    }
}

private fun CommonGetUserWalletOut.toDomain() = Wallet(
    cardNumber = cardNumber,
    cardPhoneNumber = phoneNumber,
    cardOwner = cardOwner,
    cardBank = bank
)
