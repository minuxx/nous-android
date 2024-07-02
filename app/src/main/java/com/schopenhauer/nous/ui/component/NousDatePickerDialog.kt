package com.schopenhauer.nous.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.schopenhauer.nous.R
import com.schopenhauer.nous.util.getCurrentYear
import com.schopenhauer.nous.util.getTodayTimeMillis
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalDatePickerDialog(
	title: String,
	selectedDateMillis: Long?,
	onTimeMillisChanged: (Long) -> Unit,
	onDismiss: () -> Unit
) {
	val datePickerState = rememberDatePickerState(
		initialDisplayMode = DisplayMode.Picker,
		initialDisplayedMonthMillis = getTodayTimeMillis(),
		initialSelectedDateMillis = selectedDateMillis ?: getTodayTimeMillis(),
		yearRange = 1900..getCurrentYear(),
		selectableDates = object : SelectableDates {
			override fun isSelectableDate(utcTimeMillis: Long): Boolean {
				return utcTimeMillis <= getTodayTimeMillis()
			}

			override fun isSelectableYear(year: Int): Boolean {
				return true
			}
		}
	)

	DatePickerDialog(
		onDismissRequest = {
			onDismiss()
		},
		dismissButton = {
			TextButton(
				onClick = {
					onDismiss()
				}) {
				Text(
					text = stringResource(id = R.string.dialog_date_picker_dismiss)
				)
			}
		},
		confirmButton = {
			TextButton(
				onClick = {
					val dateMillis = datePickerState.selectedDateMillis ?: getTodayTimeMillis()
					onTimeMillisChanged(dateMillis)
					onDismiss()
				}) {
				Text(
					text = stringResource(id = R.string.dialog_date_picker_confirm)
				)
			}
		}
	) {
		DatePicker(
			title = {
				Text(
					modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
					text = title,
					style = MaterialTheme.typography.titleMedium
				)
			},
			state = datePickerState
		)
	}
}