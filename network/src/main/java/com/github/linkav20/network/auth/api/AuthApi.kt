package com.github.linkav20.network.auth.api

import com.github.linkav20.auth.api.model.LoginRequest
import com.github.linkav20.auth.api.model.LoginResponse
import com.github.linkav20.auth.api.model.RefreshResponse
import com.github.linkav20.auth.api.model.RegisterRequest
import com.github.linkav20.auth.api.model.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("api/refresh_token")
    suspend fun refreshToken(): Response<RefreshResponse>
}