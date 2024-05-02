package com.github.linkav20.network.data.api

import retrofit2.http.*
import retrofit2.Response

import com.github.linkav20.network.data.models.CommonDeleteUserIn
import com.github.linkav20.network.data.models.CommonLoginIn
import com.github.linkav20.network.data.models.CommonLoginOut
import com.github.linkav20.network.data.models.CommonRefreshOut
import com.github.linkav20.network.data.models.CommonRegisterIn
import com.github.linkav20.network.data.models.CommonRegisterOut

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

}
