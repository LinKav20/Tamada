package com.github.linkav20.lists.presentation.shared

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.ui.TamadaCard
import com.github.linkav20.coreui.ui.TamadaRadioButton
import com.github.linkav20.coreui.ui.TamadaSwitch
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.lists.R
import com.github.linkav20.lists.domain.entity.ListEntity
import com.github.linkav20.lists.domain.entity.TaskEntity

@Composable
fun getListTitle(type: ListEntity.Type) = when (type) {
    ListEntity.Type.EMPTY -> stringResource(id = R.string.lists_main_empty_list_title)
    ListEntity.Type.TODO -> stringResource(id = R.string.lists_main_todo_list_title)
    ListEntity.Type.BUY -> stringResource(id = R.string.lists_main_buying_list_title)
    ListEntity.Type.WISHLIST -> stringResource(id = R.string.lists_main_wishlist_title)
}

@Composable
fun Filter(
    modifier: Modifier = Modifier,
    title: String,
    isSelected: Boolean,
    onFilterChange: (Boolean) -> Unit
) = TamadaCard(
    modifier = modifier,
    colorScheme = ColorScheme.LISTS
) {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TamadaSwitch(
            colorScheme = ColorScheme.LISTS,
            checked = isSelected,
            onCheckedChange = onFilterChange
        )
        Text(
            text = title,
            style = TamadaTheme.typography.caption,
            color = TamadaTheme.colors.textHeader,
        )
    }
}
