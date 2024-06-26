package com.github.linkav20.network.utils

import com.github.linkav20.core.domain.entity.DomainException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.Request
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import timber.log.Timber
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject

class RetrofitErrorHandler @Inject constructor(
    private val retrofit: Retrofit,
    private val json: Json,
) {
    suspend fun <T : Any> apiCall(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        call: suspend () -> Response<T>,
    ): T? = withContext(dispatcher) {
        val response: Response<T>
        try {
            response = call.invoke()
        } catch (t: Throwable) {
            Timber.e(t.message)
            throw mapNetworkThrowableToDomain(t)
        }

        if (!response.isSuccessful) {
            throw mapErrorBodyToDomainError(
                errorBody = response.errorBody(),
                code = response.code(),
                request = (response.raw() as okhttp3.Response).request,
            )
        }

        return@withContext response.body()
    }

    private fun mapErrorBodyToDomainError(
        errorBody: ResponseBody?,
        code: Int,
        request: Request,
    ): Throwable {
        if (code == 403) return DomainException.Forbidden
        if (errorBody != null) {
            try {
                val error = errorBody.string()
                return if (code == 401) {
                    DomainException.Unauthorized(
                        text = error,
                        showButton = !request.isAuthRequest()
                    )
                } else {
                    DomainException.Text(
                        text = error,
                        returnCode = code.toString(),
                    )
                }
            } catch (throwable: SerializationException) {
                Timber.e(throwable)
                if (code == 401) return DomainException.Unauthorized(
                    text = null,
                    showButton = !request.isAuthRequest()
                )
            }
        }
        return when (code) {
            in 400..499 -> {
                Timber.e(getException(code, request.method, request.url))
                DomainException.Client(code)
            }

            in 500..599 -> DomainException.Server(code)
            else -> {
                Timber.e(getException(code, request.method, request.url))
                DomainException.Unknown
            }
        }
    }
}

private fun mapNetworkThrowableToDomain(throwable: Throwable): Throwable =
    when (throwable) {
        is UnknownHostException -> DomainException.Network
        is HttpException -> DomainException.Network
        is ConnectException -> DomainException.Network
        is InterruptedIOException -> DomainException.Timeout
        // CancellationException should be provided as is, so it is possible to catch it later if necessary
        is CancellationException -> throwable
        else -> {
            Timber.e(throwable)
            DomainException.Unknown
        }
    }

private fun getException(
    code: Int,
    method: String,
    url: HttpUrl,
) = Exception("$code error for $method $url")

private fun Request.isAuthRequest() =
    this.url.encodedPath.contains("/register") || this.url.encodedPath.contains("/login")
