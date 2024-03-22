package com.github.linkav20.home.presentation.changeava

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.ui.TamadaFullscreenLoader
import com.github.linkav20.coreui.ui.TamadaTopBar
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getBackgroundColor
import com.github.linkav20.coreui.utils.getPrimaryColor
import com.github.linkav20.coreui.utils.getUserAvatar
import com.github.linkav20.home.R
import com.github.linkav20.coreui.R as CoreR


private const val AVATARS = 12

@Composable
fun ChangeAvatarScreen(
    viewModel: ChangeAvatarViewModel,
    navController: NavController
) {
    val state = viewModel.state.collectAsState().value

    Content(
        state = state,
        onBackClick = { navController.navigateUp() },
        onSelectClick = viewModel::onChangeAvatar
    )
}

@Composable
private fun Content(
    state: ChangeAvatarState,
    onBackClick: () -> Unit,
    onSelectClick: (Int) -> Unit
) = Scaffold(
    backgroundColor = TamadaTheme.colors.backgroundPrimary,
    topBar = {
        TamadaTopBar(
            title = stringResource(id = R.string.change_avatar_title),
            onBackClick = onBackClick,
            transparent = true
        )
    }
) { paddingValues ->
    if (state.loading) {
        TamadaFullscreenLoader(scheme = ColorScheme.LISTS)
    } else {
        LazyVerticalGrid(
            modifier = Modifier.padding(paddingValues),
            columns = GridCells.Fixed(3)
        ) {
            items(AVATARS) {
                ImageItem(
                    id = it,
                    isSelected = it == state.id,
                    onSelectClick = onSelectClick
                )
            }
        }
    }
}

@Composable
private fun ImageItem(
    id: Int,
    isSelected: Boolean,
    onSelectClick: (Int) -> Unit
) = BoxWithConstraints(
    modifier = Modifier
        .padding(16.dp)
) {
    val max = maxWidth
    Image(
        painter = getUserAvatar(id),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(max)
            .clip(CircleShape)
            .clickable { onSelectClick(id) }
    )
    if (isSelected) {
        Box(
            modifier = Modifier
                .background(
                    TamadaTheme.colors.backgroundPrimary.copy(alpha = 0.7f)
                )
                .size(max)
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(64.dp),
                painter = painterResource(id = CoreR.drawable.done_arrow_24),
                contentDescription = "",
                tint = getPrimaryColor(scheme = ColorScheme.LISTS)
            )
        }
    }
}
