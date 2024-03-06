package com.github.linkav20.tamada.notification

import com.github.linkav20.coreui.ui.Type
import kotlinx.serialization.Serializable

@Serializable
internal data class TamadaSnackbarData(
    val title: String? = null,
    val message: String?,
    val type: Type,
    val isSwipeable: Boolean,
)
