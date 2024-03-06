package com.github.linkav20.info.presentation.create

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.github.linkav20.info.presentation.shared.EditableThemeComponent

@Composable
fun ThemeContent(
    state: CreationPartyState,
    onThemeChanged: (String) -> Unit,
    onDressCodeChanged: (String) -> Unit,
    onMoodboardinkChanged: (String) -> Unit,
) = Column {
    EditableThemeComponent(
        moodboadLink = state.moodboadLink,
        theme = state.theme,
        dressCode = state.dressCode,
        onThemeChanged = onThemeChanged,
        onDressCodeChanged = onDressCodeChanged,
        onMoodboardinkChanged = onMoodboardinkChanged
    )
}
