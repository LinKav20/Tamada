package com.github.linkav20.info.presentation.create

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.github.linkav20.info.presentation.shared.EditableImportantComponent

@Composable
fun ImportantContent(
    state: CreationPartyState,
    onImportantChanged: (String) -> Unit,
) = Column {
    EditableImportantComponent(
        important = state.important,
        onImportantChanged = onImportantChanged
    )
}
