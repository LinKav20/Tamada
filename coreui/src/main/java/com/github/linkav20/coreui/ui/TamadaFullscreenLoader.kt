package com.github.linkav20.coreui.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getBackgroundColor

@Composable
fun TamadaFullscreenLoader(
    modifier: Modifier = Modifier,
    scheme: ColorScheme = ColorScheme.MAIN,
)  {
    Box(
        modifier =
            modifier
                .fillMaxSize()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = { },
                )
                .background(color = getBackgroundColor(scheme = scheme)),
        contentAlignment = Alignment.Center,
    ) {
        TamadaLoader()
    }
}
