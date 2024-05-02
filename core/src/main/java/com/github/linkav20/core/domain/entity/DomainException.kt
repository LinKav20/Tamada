package com.github.linkav20.core.domain.entity

sealed class DomainException : Exception() {
    object Network : DomainException()

    data class Unauthorized(val text: String? = null, val showButton: Boolean = true) :
        DomainException()

    object Unknown : DomainException()

    object Forbidden : DomainException()

    data class Text(val text: String, val returnCode: String? = null) : DomainException() {
        override val message get() = text
    }

    data class Server(val code: Int) : DomainException()

    data class Client(val code: Int) : DomainException()

    object Timeout : DomainException()

    object NoDataException: DomainException()
}
