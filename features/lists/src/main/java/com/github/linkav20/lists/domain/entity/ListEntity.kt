package com.github.linkav20.lists.domain.entity

data class ListEntity(
    val id: Long,
    val tasks: List<TaskEntity>,
    val type: Type = Type.EMPTY,
    val managersOnly: Boolean = false
) {
    enum class Type { EMPTY, TODO, BUY, WISHLIST }
}
