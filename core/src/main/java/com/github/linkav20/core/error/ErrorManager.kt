package com.github.linkav20.core.error

import android.util.Log
import com.github.linkav20.core.domain.entity.DomainException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorManager @Inject constructor() {
    private val _exceptions = Channel<DomainException>()
    val exceptions = _exceptions.receiveAsFlow()

    internal fun show(exception: Exception) {
        when (exception) {
            is DomainException -> {
                _exceptions.trySend(exception)
            }

            is CancellationException -> {
                return
            }

            else -> {
                Log.e("", exception.message.toString())
                _exceptions.trySend(DomainException.Unknown)
            }
        }
    }
}
