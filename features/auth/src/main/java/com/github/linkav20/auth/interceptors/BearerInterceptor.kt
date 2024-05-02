package com.github.linkav20.auth.interceptors

import android.util.Log
import com.github.linkav20.auth.domain.repository.TokenRepository
import okhttp3.Interceptor
import okhttp3.Request
import javax.inject.Inject

private const val AUTHORIZATION_HEADER = "Authorization"
private const val AUTHORIZATION_PREFIX = "Bearer"

class BearerInterceptor @Inject constructor(
    private val tokenRepository: TokenRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain) = chain.proceed(
        chain.request().newBuilder()
            .also { builder ->
                if (!chain.request().isAuthRequest() && !chain.request().isVersioningRequest()) {
                    tokenRepository.accessToken.let {
                        builder.addHeader(AUTHORIZATION_HEADER, "$AUTHORIZATION_PREFIX $it")
                    }
                }
            }
            .build()
    )

    private fun Request.isAuthRequest() = this.url.encodedPath.contains("/auth")
    private fun Request.isVersioningRequest() = this.url.encodedPath.contains("/versioning")
}
