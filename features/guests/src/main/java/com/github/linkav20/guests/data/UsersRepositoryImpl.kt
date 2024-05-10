package com.github.linkav20.guests.data

import com.github.linkav20.guests.domain.model.User
import com.github.linkav20.guests.domain.repository.UsersRepository
import com.github.linkav20.network.data.api.EventApi
import com.github.linkav20.network.data.models.CommonDeleteUserFromPartyIn
import com.github.linkav20.network.data.models.CommonGetPartiesGuestsIn
import com.github.linkav20.network.data.models.CommonGetPartiesGuestsOut
import com.github.linkav20.network.data.models.CommonUpdateUserRoleOnPartyIn
import com.github.linkav20.network.utils.RetrofitErrorHandler
import com.github.linkav20.network.data.models.CommonGetInviteLinkIn
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val retrofitErrorHandler: RetrofitErrorHandler,
    private val eventApi: EventApi
) : UsersRepository {

    override suspend fun updateUserRole(
        partyId: Long,
        actionFrom: Int,
        userId: Int,
        role: User.UserRole
    ) {
        retrofitErrorHandler.apiCall {
            eventApi.updateUserRole(
                CommonUpdateUserRoleOnPartyIn(
                    partyID = partyId.toInt(),
                    actionFrom = actionFrom,
                    userID = userId,
                    newRole = when (role) {
                        User.UserRole.MANAGER -> "manager"
                        User.UserRole.CREATOR -> "creator"
                        else -> "participant"
                    },
                )
            )
        }
    }

    override suspend fun deleteUser(partyId: Long, actionFrom: Int, userId: Int) {
        retrofitErrorHandler.apiCall {
            eventApi.deleteUserFromEvent(
                CommonDeleteUserFromPartyIn(
                    actionFrom = actionFrom,
                    partyID = partyId.toInt(),
                    userToDelete = userId
                )
            )
        }
    }

    override suspend fun getInviteLink(partyId: Long): String? = retrofitErrorHandler.apiCall {
        eventApi.getEventInviteLink(CommonGetInviteLinkIn(partyID = partyId.toInt()))
    }?.link

    override suspend fun getUsers(partyId: Long) = retrofitErrorHandler.apiCall {
        eventApi.getPartiesGuests(CommonGetPartiesGuestsIn(partyID = partyId.toInt()))
    }?.map { it.toDomain() } ?: emptyList()
}

private fun CommonGetPartiesGuestsOut.toDomain() = User(
    id = userID ?: 0,
    name = login ?: "",
    role = when (role) {
        "manager" -> User.UserRole.MANAGER
        "creator" -> User.UserRole.CREATOR
        else -> User.UserRole.GUEST
    },
    status = when (status) {
        "confirmed" -> User.ArriveStatus.EXACTLY
        "uncertain" -> User.ArriveStatus.PROBABLY
        else -> User.ArriveStatus.PROBABLY
    },
    avatar = avatarID ?: 0
)