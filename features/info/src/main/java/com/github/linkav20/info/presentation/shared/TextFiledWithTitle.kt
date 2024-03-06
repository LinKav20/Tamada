package com.github.linkav20.info.presentation.shared

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.ui.TamadaTextFiled

@Composable
fun TextFiledWithTitle(
    value: String?,
    title: String,
    hint: String,
    onValueChanged: (String) -> Unit,
) {
    Text(
        text = title,
        style = TamadaTheme.typography.body.copy(fontWeight = FontWeight.Bold),
        color = TamadaTheme.colors.textHeader,
    )
    Spacer(modifier = Modifier.height(4.dp))
    TamadaTextFiled(
        value = value ?: "",
        placeholder = hint,
        onValueChange = onValueChanged,
    )
}
