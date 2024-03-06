package com.github.linkav20.coreui.ui

import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getPrimaryColor
import com.github.linkav20.coreui.utils.getSecondaryColor

@Composable
fun TamadaRadioButton(
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)?,
    colorScheme: ColorScheme = ColorScheme.MAIN
) = RadioButton(
    modifier = modifier,
    colors = RadioButtonDefaults.colors(
        selectedColor = getPrimaryColor(scheme = colorScheme),
        unselectedColor = TamadaTheme.colors.textMain,
        disabledColor = getSecondaryColor(scheme = colorScheme)
    ), selected = selected,
    onClick = onClick
) 
