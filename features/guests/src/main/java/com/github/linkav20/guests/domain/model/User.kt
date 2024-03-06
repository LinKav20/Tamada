package com.github.linkav20.guests.domain.model

data class User(
    val name: String,
    val role: UserRole = UserRole.GUEST,
    val status: ArriveStatus = ArriveStatus.PROBABLY
) {
    enum class UserRole {
        MANAGER, GUEST
    }

    enum class ArriveStatus {
        EXACTLY, PROBABLY
    }
}
