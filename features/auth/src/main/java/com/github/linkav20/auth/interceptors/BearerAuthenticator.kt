package com.github.linkav20.auth.interceptors

import android.util.Log
import com.github.linkav20.auth.domain.usecase.RefreshTokenUseCase
import dagger.Lazy
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import javax.inject.Inject

private const val AUTHORIZATION_HEADER = "Authorization"
private const val AUTHORIZATION_PREFIX = "Bearer"

internal class BearerAuthenticator @Inject constructor(
    private val refreshTokenUseCase: Lazy<RefreshTokenUseCase>,
) : Authenticator {
    private val mutex = Mutex()

    override fun authenticate(route: Route?, response: Response): Request? =
        with(response.request) {
            if (url.pathSegments.contains("login") ||
                url.pathSegments.contains("refresh_token")
            ) {
                return null
            }

            val auth = runBlocking {
                try {
                    mutex.withLock { refreshTokenUseCase.get().invoke() }
                } catch (e: Exception) {
                    Timber.d(e)
                    null
                }
            } ?: return null

            return newBuilder()
                .header(AUTHORIZATION_HEADER, "$AUTHORIZATION_PREFIX ${auth.accessToken}")
                .build()
        }
}
