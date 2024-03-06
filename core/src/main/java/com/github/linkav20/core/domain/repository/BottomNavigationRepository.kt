package com.github.linkav20.core.domain.repository

import com.github.linkav20.core.domain.entity.BottomNavigationItem

interface BottomNavigationRepository {

    fun updateAll()

    fun needToFirstLoad(entry: BottomNavigationItem): Boolean
}