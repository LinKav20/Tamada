package com.github.linkav20.guests.domain.model

data class User(
    val id: Int,
    val name: String,
    val role: UserRole = UserRole.GUEST,
    val status: ArriveStatus = ArriveStatus.PROBABLY,
    val avatar: Int = 0
) {
    enum class UserRole {
        MANAGER, GUEST, CREATOR
    }

    enum class ArriveStatus {
        EXACTLY, PROBABLY
    }
}
