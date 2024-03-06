package com.github.linkav20.home.domain.model

data class Party(
    val id: Long,
    val name: String,
    val cover: Int,
    val isManager: Boolean,
    val isNew: Boolean,
)
