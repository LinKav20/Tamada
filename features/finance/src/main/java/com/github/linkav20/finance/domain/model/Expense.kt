package com.github.linkav20.finance.domain.model

import android.net.Uri

data class Expense(
    val id: Long = 0L,
    val name: String,
    val sum: Double,
    val uri: Uri? = null,
    val type: Type = Type.SUM
) {
    enum class Type {
        DEBT, SUM
    }
}
