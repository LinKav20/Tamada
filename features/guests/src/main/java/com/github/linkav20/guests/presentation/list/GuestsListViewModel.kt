package com.github.linkav20.guests.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.linkav20.core.domain.entity.UserRole
import com.github.linkav20.core.domain.usecase.GetPartyIdUseCase
import com.github.linkav20.core.domain.usecase.GetRoleUseCase
import com.github.linkav20.core.notification.ReactUseCase
import com.github.linkav20.guests.domain.model.User
import com.github.linkav20.guests.domain.usecase.DeleteUserUseCase
import com.github.linkav20.guests.domain.usecase.GetInviteLinkUseCase
import com.github.linkav20.guests.domain.usecase.GetUsersForPartyUseCase
import com.github.linkav20.guests.domain.usecase.UpdateUserRoleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GuestsListViewModel @Inject constructor(
    private val getUsersForPartyUseCase: GetUsersForPartyUseCase,
    private val updateUserRoleUseCase: UpdateUserRoleUseCase,
    private val getRoleUseCase: GetRoleUseCase,
    private val getPartyIdUseCase: GetPartyIdUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val getInviteLinkUseCase: GetInviteLinkUseCase,
    private val reactUseCase: ReactUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(GuestsListState())
    val state = _state.asStateFlow()

    fun onStart() = loadData()

    fun onRetry() {
        _state.update { it.copy(error = null) }
        loadData()
    }

    fun onAddToAdminClick(user: User) {
        changeUserRole(user, User.UserRole.MANAGER)
    }

    fun onDeleteFromAdminClick(user: User) {
        changeUserRole(user, User.UserRole.GUEST)
    }

    fun onEditClick() = _state.update { it.copy(isEditable = true) }

    fun onUpdateLink() = loadInvitationLink()

    fun onDeleteClick(user: User) {
        val newUsers = state.value.users.minus(user)
        _state.update { it.copy(users = newUsers) }
        deleteUserFromParty(user)
    }

    fun onSaveClick() = viewModelScope.launch {
        _state.update { it.copy(isEditable = false) }
    }

    private fun changeUserRole(
        user: User,
        role: User.UserRole
    ) {
        val newUser = state.value.users.find { it == user }?.copy(role = role)
        if (newUser != null) {
            val index = state.value.users.indexOf(user)
            val newUsers = state.value.users.minus(user).toMutableList()
            newUsers.add(index, newUser)
            _state.update { it.copy(users = newUsers) }
            loadNewUserRoleToServer(newUser)
        }
    }

    private fun loadNewUserRoleToServer(user: User) = viewModelScope.launch {
        val partyId = getPartyIdUseCase.invoke() ?: return@launch
        try {
            updateUserRoleUseCase.invoke(partyId = partyId, user = user)
        } catch (e: Exception) {
            reactUseCase.invoke(e)
        }
    }

    private fun deleteUserFromParty(user: User) = viewModelScope.launch {
        val partyId = getPartyIdUseCase.invoke() ?: return@launch
        try {
            deleteUserUseCase.invoke(partyId = partyId, userId = user.id)
        } catch (e: Exception) {
            reactUseCase.invoke(e)
        }
    }

    private fun loadData() = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        onUpdateLink()
        val partyId = getPartyIdUseCase.invoke() ?: return@launch
        val role = getRoleUseCase.invoke()
        loadInvitationLink()
        try {
            val users = getUsersForPartyUseCase.invoke(partyId)
            _state.update {
                it.copy(
                    users = users,
                    isUserEdit = role == UserRole.MANAGER,
                    infoShownCount = 6
                )
            }
        } catch (e: Exception) {
            reactUseCase.invoke(e)
        } finally {
            _state.update { it.copy(loading = false) }
        }
    }

    private fun loadInvitationLink() = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        val partyId = getPartyIdUseCase.invoke() ?: return@launch
        try {
            val inviteLink = getInviteLinkUseCase.invoke(partyId)
            _state.update { it.copy(link = inviteLink) }
        } catch (e: Exception) {
            _state.update { it.copy(error = e) }
        } finally {
            _state.update { it.copy(loading = false) }
        }
    }
}
