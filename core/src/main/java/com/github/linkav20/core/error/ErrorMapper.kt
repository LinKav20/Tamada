package com.github.linkav20.core.error

import androidx.compose.runtime.Composable
import com.github.linkav20.coreui.utils.ColorScheme

interface ErrorMapper {

    @Composable
    fun OnError(throwable: Throwable, onActionClick: () -> Unit, colorScheme: ColorScheme)
}