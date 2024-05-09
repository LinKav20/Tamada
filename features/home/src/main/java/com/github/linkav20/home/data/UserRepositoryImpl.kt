package com.github.linkav20.home.data

import com.github.linkav20.home.domain.model.Wallet
import com.github.linkav20.home.domain.model.User
import com.github.linkav20.home.domain.repository.UserRepository
import com.github.linkav20.network.data.api.AuthApi
import com.github.linkav20.network.utils.RetrofitErrorHandler
import com.github.linkav20.network.data.models.CommonGetUserWalletIn
import com.github.linkav20.network.data.models.CommonGetUserWalletOut
import com.github.linkav20.network.data.models.CommonUpdateUserAvatarIn
import com.github.linkav20.network.data.models.CommonUpdateUserWalletBankIn
import com.github.linkav20.network.data.models.CommonUpdateUserWalletCardIn
import com.github.linkav20.network.data.models.CommonUpdateUserWalletOwnerIn
import com.github.linkav20.network.data.models.CommonUpdateUserWalletPhoneIn
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

    override suspend fun updateLogin(login: String, userId: Int) {

    }

    override suspend fun updateCardNumber(number: String, userId: Int) {
        retrofitErrorHandler.apiCall {
            authApi.updateUserWalletCard(
                CommonUpdateUserWalletCardIn(
                    cardNumber = number.toLong(),
                    userID = userId
                )
            )
        }
    }

    override suspend fun updateCardOwner(owner: String, userId: Int) {
        retrofitErrorHandler.apiCall {
            authApi.updateUserWalletOwner(
                CommonUpdateUserWalletOwnerIn(
                    cardOwner = owner,
                    userID = userId
                )
            )
        }
    }

    override suspend fun updateCardBank(bank: String, userId: Int) {
        retrofitErrorHandler.apiCall {
            authApi.updateUserWalletBank(
                CommonUpdateUserWalletBankIn(
                    bank = bank,
                    userID = userId
                )
            )
        }
    }

    override suspend fun updateCardPhoneNumber(phone: String, userId: Int) {
        retrofitErrorHandler.apiCall {
            authApi.updateUserWalletPhone(
                CommonUpdateUserWalletPhoneIn(
                    phoneNumber = phone,
                    userID = userId
                )
            )
        }
    }
}

private fun CommonGetUserWalletOut.toDomain() = Wallet(
    cardNumber = if (cardNumber == "0") "" else cardNumber,
    cardPhoneNumber = phoneNumber,
    cardOwner = cardOwner,
    cardBank = bank
)
