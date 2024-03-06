package com.github.linkav20.home.presentation.lk

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.ui.TamadaTextFiled
import com.github.linkav20.home.R
import com.github.linkav20.coreui.R as CoreR
import com.github.linkav20.coreui.ui.TamadaTopBar
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getBackgroundColor
import com.github.linkav20.coreui.utils.getPrimaryColor


@Composable
fun LkScreen(
    viewModel: LkViewModel,
    navController: NavController
) {
    val state = viewModel.state.collectAsState().value

    Content(
        onBackClick = { navController.navigateUp() }
    )
}

@Composable
private fun Content(
    onBackClick: () -> Unit
) {
    Scaffold(
        backgroundColor = getBackgroundColor(scheme = ColorScheme.MAIN),
        topBar = {
            TamadaTopBar(
                title = stringResource(id = R.string.lk_screen_title),
                onBackClick = onBackClick,
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = CoreR.drawable.avatar),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape),
            )
            TamadaTextFiled(value = "Change login")
            TamadaTextFiled(value = "Change email")
            TamadaTextFiled(value = "Change password")
        }
    }
}