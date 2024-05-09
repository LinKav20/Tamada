package com.github.linkav20.network.data.api

import retrofit2.http.*
import retrofit2.Response

import com.github.linkav20.network.data.models.CommonDeleteUserIn
import com.github.linkav20.network.data.models.CommonLoginIn
import com.github.linkav20.network.data.models.CommonLoginOut
import com.github.linkav20.network.data.models.CommonRefreshOut
import com.github.linkav20.network.data.models.CommonRegisterIn
import com.github.linkav20.network.data.models.CommonRegisterOut
import com.github.linkav20.network.data.models.CommonGetPartySummaryExpensesIn
import com.github.linkav20.network.data.models.CommonGetPartySummaryExpensesOut
import com.github.linkav20.network.data.models.CommonGetPartyWalletIn
import com.github.linkav20.network.data.models.CommonGetPartyWalletOut
import com.github.linkav20.network.data.models.CommonGetUserExpenseInfoByIDIn
import com.github.linkav20.network.data.models.CommonGetUserExpenseInfoByIDOut
import com.github.linkav20.network.data.models.CommonGetUserWalletIn
import com.github.linkav20.network.data.models.CommonGetUserWalletOut
import com.github.linkav20.network.data.models.CommonUpdateUserAvatarIn
import com.github.linkav20.network.data.models.CommonUpdateUserPasswordIn
import com.github.linkav20.network.data.models.CommonUpdateUserWalletBankIn
import com.github.linkav20.network.data.models.CommonUpdateUserWalletCardIn
import com.github.linkav20.network.data.models.CommonUpdateUserWalletOwnerIn
import com.github.linkav20.network.data.models.CommonUpdateUserWalletPhoneIn

interface AuthApi {
    /**
     * DeleteUser
     * Удаление пользователя
     * Responses:
     *  - 204: No Content
     *
     * @param input event info
     * @return [Unit]
     */
    @POST("api/delete_user")
    suspend fun deleteUser(@Body input: CommonDeleteUserIn): Response<Unit>

    /**
     * GetPartySummaryExpenses
     * Получение суммарных трат на мероприятие
     * Responses:
     *  - 200: OK
     *
     * @param input event info
     * @return [CommonGetPartySummaryExpensesOut]
     */
    @POST("api/get_party_summary_expenses")
    suspend fun getPartySummaryExpenses(@Body input: CommonGetPartySummaryExpensesIn): Response<CommonGetPartySummaryExpensesOut>

    /**
     * GetPartyWallet
     * Получение информации о кошельке мероприятия
     * Responses:
     *  - 200: OK
     *
     * @param input event info
     * @return [CommonGetPartyWalletOut]
     */
    @POST("api/get_party_wallet")
    suspend fun getPartyWallet(@Body input: CommonGetPartyWalletIn): Response<CommonGetPartyWalletOut>

    /**
     * GetUserExpenseInfoByID
     * Получение трат пользователя на айди расходов
     * Responses:
     *  - 200: OK
     *
     * @param input event info
     * @return [CommonGetUserExpenseInfoByIDOut]
     */
    @POST("api/get_user_expenses_by_id")
    suspend fun getUserExpensesById(@Body input: CommonGetUserExpenseInfoByIDIn): Response<CommonGetUserExpenseInfoByIDOut>

    /**
     * UpdateUserPassword
     * Обновление пароля пользователя
     * Responses:
     *  - 204: No Content
     *
     * @param input event info
     * @return [Unit]
     */
    @POST("api/update_user_password")
    suspend fun getUserPass(@Body input: CommonUpdateUserPasswordIn): Response<Unit>

    /**
     * GetUserWallet
     * Получение информации о кошельке пользователя
     * Responses:
     *  - 200: OK
     *
     * @param input event info
     * @return [CommonGetUserWalletOut]
     */
    @POST("api/get_user_wallet")
    suspend fun getUserWallet(@Body input: CommonGetUserWalletIn): Response<CommonGetUserWalletOut>

    /**
     * Login
     * авторизация в аккаунте
     * Responses:
     *  - 200: OK
     *
     * @param input account info
     * @return [CommonLoginOut]
     */
    @POST("login")
    suspend fun loginAcc(@Body input: CommonLoginIn): Response<CommonLoginOut>

    /**
     * Register
     * регистрация в аккаунте
     * Responses:
     *  - 200: OK
     *
     * @param input account info
     * @return [CommonRegisterOut]
     */
    @POST("register")
    suspend fun registerAcc(@Body input: CommonRegisterIn): Response<CommonRegisterOut>

    @GET("api/refresh_token")
    suspend fun refreshToken(): Response<CommonRefreshOut>

    /**
     * UpdateUserAvatar
     * Обновление аватарки пользователя
     * Responses:
     *  - 204: No Content
     *
     * @param input event info
     * @return [Unit]
     */
    @POST("api/update_user_avatar")
    suspend fun updateUserAvatar(@Body input: CommonUpdateUserAvatarIn): Response<Unit>

    /**
     * UpdateUserWalletBank
     * Обновление банка пользователя
     * Responses:
     *  - 204: No Content
     *
     * @param input event info
     * @return [Unit]
     */
    @POST("api/update_user_wallet_bank")
    suspend fun updateUserWalletBank(@Body input: CommonUpdateUserWalletBankIn): Response<Unit>

    /**
     * UpdateUserWalletCard
     * Обновление карты кошелька пользователя
     * Responses:
     *  - 204: No Content
     *
     * @param input event info
     * @return [Unit]
     */
    @POST("api/update_user_wallet_card")
    suspend fun updateUserWalletCard(@Body input: CommonUpdateUserWalletCardIn): Response<Unit>

    /**
     * UpdateUserWalletOwner
     * Обновление владельца карты кошелька пользователя
     * Responses:
     *  - 204: No Content
     *
     * @param input event info
     * @return [Unit]
     */
    @POST("api/update_user_wallet_owner")
    suspend fun updateUserWalletOwner(@Body input: CommonUpdateUserWalletOwnerIn): Response<Unit>

    /**
     * UpdateUserWalletPhone
     * Обновление телефона кошелька пользователя
     * Responses:
     *  - 204: No Content
     *
     * @param input event info
     * @return [Unit]
     */
    @POST("api/update_user_wallet_phone")
    suspend fun updateUserWalletPhone(@Body input: CommonUpdateUserWalletPhoneIn): Response<Unit>

}
