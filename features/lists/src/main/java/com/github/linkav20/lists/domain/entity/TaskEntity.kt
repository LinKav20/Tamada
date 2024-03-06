package com.github.linkav20.lists.domain.entity

data class TaskEntity(
    val id: Long,
    val name: String,
    val done: Boolean = false
)
