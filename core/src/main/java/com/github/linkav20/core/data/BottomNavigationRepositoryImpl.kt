package com.github.linkav20.core.data

import com.github.linkav20.core.domain.entity.BottomNavigationItem
import com.github.linkav20.core.domain.entity.ExpensesBottomNavigationItem
import com.github.linkav20.core.domain.entity.GuestsBottomNavigationItem
import com.github.linkav20.core.domain.entity.InfoBottomNavigationItem
import com.github.linkav20.core.domain.entity.ListsBottomNavigationItem
import com.github.linkav20.core.domain.repository.BottomNavigationRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BottomNavigationRepositoryImpl @Inject constructor(

) : BottomNavigationRepository {

    private val updates = mutableMapOf(
        InfoBottomNavigationItem to true,
        GuestsBottomNavigationItem to true,
        ListsBottomNavigationItem to true,
        ExpensesBottomNavigationItem to true
    )

    override fun updateAll() {
        for (entry in updates.keys) {
            updates[entry] = true
        }
        updates[InfoBottomNavigationItem] = false
    }

    override fun needToFirstLoad(entry: BottomNavigationItem): Boolean {
        val update = updates[entry]
        updates[entry] = false
        return update ?: true
    }
}