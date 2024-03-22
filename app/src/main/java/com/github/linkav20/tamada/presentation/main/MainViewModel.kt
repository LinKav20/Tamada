package com.github.linkav20.tamada.presentation.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.linkav20.auth.navigation.AuthGraphDestination
import com.github.linkav20.core.domain.entity.BottomNavigationItem
import com.github.linkav20.core.domain.usecase.GetUpdatesForBottomItem
import com.github.linkav20.home.navigation.HomeGraphDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUpdatesForBottomItem: GetUpdatesForBottomItem
) : ViewModel() {
    private val _state = MutableStateFlow(MainState(startDestination = getAuthState()))
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    loading = false
                )
            }
        }
    }

    fun isFirstTabLoad(entry: BottomNavigationItem): Boolean {
        return getUpdatesForBottomItem.invoke(entry)
    }

    private fun getAuthState() = HomeGraphDestination.route()
}
