package com.github.linkav20.home.presentation.party

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.github.linkav20.coreui.theme.TamadaTheme

@Composable
fun PartyScreen(navController: NavController) =
    Scaffold(
        backgroundColor = TamadaTheme.colors.backgroundPrimary,
    ) {
        Box(modifier = Modifier.padding(it).clickable { navController.navigateUp() }) {
            Text(text = "Here")
        }
    }
