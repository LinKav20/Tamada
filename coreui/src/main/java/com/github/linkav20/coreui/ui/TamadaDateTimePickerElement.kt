package com.github.linkav20.coreui.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.github.linkav20.coreui.R
import com.maxkeppeker.sheets.core.models.base.SelectionButton
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.time.OffsetDateTime

@Composable
fun TamadaDateTimePickerElement(
    currentDate: OffsetDateTime?,
    onValueChanged: (OffsetDateTime) -> Unit,
    content: @Composable () -> Unit
) {
    val calendarDialog = rememberSheetState()
    val clockDialog = rememberSheetState()
    val date = remember { mutableStateOf(currentDate ?: OffsetDateTime.now()) }
    CalendarDialog(
        state = calendarDialog,
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true
        ),
        selection = CalendarSelection.Date(
            negativeButton = SelectionButton(stringResource(id = R.string.dialog_button_cancel)),
            positiveButton = SelectionButton(stringResource(id = R.string.dialog_button_next)),
            selectedDate = date.value.toLocalDate()
        ) {
            clockDialog.show()
            date.value = date.value.withYear(it.year)
                .withMonth(it.month.value)
                .withDayOfMonth(it.dayOfMonth)
        })
    ClockDialog(
        state = clockDialog,
        config = ClockConfig(
            is24HourFormat = true,
            defaultTime = date.value.toLocalTime()
        ),
        selection = ClockSelection.HoursMinutes(
            negativeButton = SelectionButton(stringResource(id = R.string.dialog_button_back)),
            positiveButton = SelectionButton(stringResource(id = R.string.dialog_button_ok)),
            onNegativeClick = { calendarDialog.show() }
        ) { h, m ->
            date.value = date.value.withHour(h).withMinute(m)
            onValueChanged(date.value)
        })
    Box(modifier = Modifier.clickable { calendarDialog.show() }) {
        content()
    }
}