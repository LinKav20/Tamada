package com.github.linkav20.coreui.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getPrimaryColor

@Composable
fun TamadaLoader(
    modifier: Modifier = Modifier,
    scheme: ColorScheme = ColorScheme.MAIN,
    contentDescription: String? = null,
) {
    CircularProgressIndicator(
        modifier =
            modifier
                .size(48.dp)
                .semantics {
                    if (contentDescription != null) this.contentDescription = contentDescription
                },
        color = getPrimaryColor(scheme),
    )
}
