package com.github.linkav20.coreui.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getBackgroundColor
import com.github.linkav20.coreui.utils.getPrimaryColor
import com.github.linkav20.coreui.utils.getSecondaryColor

@Composable
fun TamadaSwitch(
    checked: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colorScheme: ColorScheme = ColorScheme.MAIN,
    onCheckedChange: (Boolean) -> Unit,
) = Switch(
    enabled = enabled,
    checked = checked,
    onCheckedChange = onCheckedChange,
    colors = SwitchDefaults.colors(
        checkedThumbColor = TamadaTheme.colors.backgroundPrimary,
        checkedTrackColor = getPrimaryColor(scheme = colorScheme),
        checkedTrackAlpha = 1f,
        uncheckedThumbColor = getPrimaryColor(scheme = colorScheme),
        uncheckedTrackColor = getSecondaryColor(scheme = colorScheme),
        uncheckedTrackAlpha = 1f,
        disabledCheckedThumbColor = TamadaTheme.colors.textCaption,
        disabledCheckedTrackColor = TamadaTheme.colors.textCaption,
        disabledUncheckedThumbColor = TamadaTheme.colors.textCaption,
        disabledUncheckedTrackColor = TamadaTheme.colors.textCaption,
    )
)


@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun OzonSwitchColorsPreview() {
    TamadaTheme {
        Column {
            TamadaSwitch(checked = false, onCheckedChange = {})
            TamadaSwitch(checked = true, onCheckedChange = {})
            TamadaSwitch(
                enabled = false,
                checked = false,
                onCheckedChange = {},
            )
            TamadaSwitch(
                enabled = false,
                checked = true,
                onCheckedChange = {},
            )
        }
    }
}
