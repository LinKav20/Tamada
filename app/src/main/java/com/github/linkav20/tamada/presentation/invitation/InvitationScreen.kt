package com.github.linkav20.tamada.presentation.invitation

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.github.linkav20.coreui.ui.TamadaButton
import com.github.linkav20.coreui.ui.TamadaTopBar
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getBackgroundColor

@Composable
fun InvitationScreen(id: Int) {
    val activity = (LocalContext.current as? Activity)

    Content(
        id = id,
        onBackClick = { activity?.finish() }
    )
}

@Composable
private fun Content(
    id: Int,
    onBackClick: () -> Unit
) = Scaffold(
    backgroundColor = getBackgroundColor(scheme = ColorScheme.LISTS),
    topBar = {
        TamadaTopBar(
            onBackClick = onBackClick
        )
    },
) { paddingValues ->
    Box(modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize()) {
        TamadaButton(onClick = onBackClick, title = id.toString())
    }
}
