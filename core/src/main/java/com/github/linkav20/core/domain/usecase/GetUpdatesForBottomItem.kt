package com.github.linkav20.core.domain.usecase

import com.github.linkav20.core.domain.entity.BottomNavigationItem
import com.github.linkav20.core.domain.repository.BottomNavigationRepository
import javax.inject.Inject

class GetUpdatesForBottomItem @Inject constructor(
    private val repository: BottomNavigationRepository
) {

    fun invoke(entry: BottomNavigationItem) = repository.needToFirstLoad(entry)
}