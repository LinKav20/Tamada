package com.github.linkav20.guests.presentation.list

import com.github.linkav20.guests.domain.model.User
import java.lang.Error

data class GuestsListState(
    val loading: Boolean = false,
    val infoShownCount: Int? = null,
    val link: String? = null,
    val isEditable: Boolean = false,
    val isUserEdit: Boolean = true,
    val users: List<User> = emptyList(),
    val error: Exception? = null
) {

    val isUsersEmpty: Boolean
        get() = users.isEmpty()

    fun managers() =
        users.filter { it.role == User.UserRole.MANAGER || it.role == User.UserRole.CREATOR }

    fun guestsProbablyCome() = users.filter {
        it.role == User.UserRole.GUEST &&
                it.status == User.ArriveStatus.PROBABLY
    }

    fun guestsExactlyCome() = users.filter {
        it.role == User.UserRole.GUEST &&
                it.status == User.ArriveStatus.EXACTLY
    }
}
