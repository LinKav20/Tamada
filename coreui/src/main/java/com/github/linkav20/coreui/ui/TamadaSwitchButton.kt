package com.github.linkav20.coreui.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getPrimaryColor
import com.github.linkav20.coreui.utils.getSecondaryColor

@Composable
fun TamadaSwitchButton(
    checkVariant: String,
    uncheckVariant: String,
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    colorScheme: ColorScheme = ColorScheme.MAIN,
    onClick: () -> Unit,
) {
    Row(modifier = modifier) {
        Button(
            onClick = onClick,
            modifier = Modifier.weight(0.5f),
            shape =
                TamadaTheme.shapes.mediumSmall.copy(
                    topEnd = CornerSize(0.dp),
                    bottomEnd = CornerSize(0.dp),
                ),
            colors =
                ButtonDefaults.outlinedButtonColors(
                    backgroundColor =
                        if (isChecked) {
                            getPrimaryColor(scheme = colorScheme)
                        } else {
                            getSecondaryColor(scheme = colorScheme)
                        },
                ),
        ) {
            Text(
                text = checkVariant,
                style = TamadaTheme.typography.body,
                color =
                    if (isChecked) {
                        TamadaTheme.colors.textWhite
                    } else {
                        getPrimaryColor(scheme = colorScheme)
                    },
            )
        }
        Button(
            onClick = onClick,
            modifier = Modifier.weight(0.5f),
            shape =
                TamadaTheme.shapes.mediumSmall.copy(
                    bottomStart = CornerSize(0.dp),
                    topStart = CornerSize(0.dp),
                ),
            colors =
                ButtonDefaults.outlinedButtonColors(
                    backgroundColor =
                        if (!isChecked) {
                            getPrimaryColor(scheme = colorScheme)
                        } else {
                            getSecondaryColor(scheme = colorScheme)
                        },
                ),
        ) {
            Text(
                text = uncheckVariant,
                style = TamadaTheme.typography.body,
                color =
                    if (!isChecked) {
                        TamadaTheme.colors.textWhite
                    } else {
                        getPrimaryColor(scheme = colorScheme)
                    },
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    TamadaTheme {
        TamadaSwitchButton(
            checkVariant = "Hehe",
            uncheckVariant = "Not hehe",
            isChecked = false,
            onClick = {},
        )
    }
}
