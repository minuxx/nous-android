package com.schopenhauer.nous.ui.journal.write

import android.os.Build
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.schopenhauer.nous.R
import com.schopenhauer.nous.domain.model.Task
import com.schopenhauer.nous.ui.component.NousAppBar
import com.schopenhauer.nous.ui.component.TaskItemColumn
import com.schopenhauer.nous.ui.theme.NousTheme
import java.time.DayOfWeek
import java.time.Instant
import java.time.ZoneId
import java.util.Calendar
import java.util.TimeZone

@Composable
fun WriteJournalScreen(
	modifier: Modifier = Modifier,
	date: String,
	tasks: List<Task>,
	onClickBack: () -> Unit,
	onSaveJournal: () -> Unit,
	onRemoveTask: (Long) -> Unit,
	taskContent: String = "",
	onWriteTask: (String) -> Unit,
	onTimeMillisChanged: (Long?) -> Unit
) {
	var shouldShowDatePicker by rememberSaveable { mutableStateOf(false) }

	Column(
		modifier = modifier.fillMaxSize()
	) {
		NousAppBar(
			title = stringResource(id = R.string.write_journal),
			onLeftIconClick = onClickBack,
			onRightClickIcon = onSaveJournal,
			rightText = stringResource(id = R.string.save)
		)
		Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
		DateSelectField(
			modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = dimensionResource(id = R.dimen.padding_medium)),
			date = date,
			onClick = { shouldShowDatePicker = true }
		)
		Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
		TaskItemColumn(
			tasks = tasks,
			onClick = {},
			onClickIcon = { taskId ->
				onRemoveTask(taskId)
			}
		)
		Spacer(modifier = Modifier.weight(1f))
		TaskOutlinedTextField(
			content = taskContent,
			onValueChange = onWriteTask,
		)
	}

	if (shouldShowDatePicker) {
		JournalDatePickerDialog(
			onTimeMillisChanged = onTimeMillisChanged,
			onDismiss = { shouldShowDatePicker = false }
		)
	}
}

@Preview(showBackground = true)
@Composable
fun WriteJournalScreenPreview() {
	NousTheme {
		Surface {
			WriteJournalScreen(
				date = "2024/06/24",
				tasks = listOf(
					Task(
						id = 1,
						content = "컴포즈 마이그레이션",
					)
				),
				onClickBack = {},
				onSaveJournal = {},
				onRemoveTask = {},
				onWriteTask = {},
				onTimeMillisChanged = {}
			)
		}
	}
}

@Preview(showBackground = true)
@Composable
fun WriteJournalScreenDarkPreview() {
	NousTheme(darkTheme = true) {
		Surface {
			WriteJournalScreen(
				date = "2024/06/24",
				tasks = listOf(
					Task(
						id = 1,
						content = "컴포즈 마이그레이션",
					)
				),
				onClickBack = {},
				onSaveJournal = {},
				onRemoveTask = {},
				onWriteTask = {},
				onTimeMillisChanged = {}
			)
		}
	}
}

@Composable
fun DateSelectField(
	modifier: Modifier = Modifier,
	date: String,
	onClick: () -> Unit
) {
	Card(
		modifier = modifier,
		border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.primary),
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.primaryContainer,
		),
		onClick = onClick
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(
					horizontal = dimensionResource(id = R.dimen.padding_medium),
					vertical = dimensionResource(id = R.dimen.padding_small)
				),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween,
		) {
			Text(
				text = date,
				style = MaterialTheme.typography.displaySmall,
				color = MaterialTheme.colorScheme.onPrimaryContainer
			)
			Icon(
				painter = painterResource(id = R.drawable.ic_calendar),
				contentDescription = null,
				tint = MaterialTheme.colorScheme.onPrimaryContainer
			)
		}
	}
}

@Preview(showBackground = true)
@Composable
fun DateSelectFieldLightPreview() {
	NousTheme {
		DateSelectField(
			date = "2024/06/24",
			onClick = {}
		)
	}
}

@Preview(showBackground = true)
@Composable
fun DateSelectFieldDarkPreview() {
	NousTheme(darkTheme = true) {
		DateSelectField(
			date = "2024/06/24",
			onClick = {}
		)
	}
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalDatePickerDialog(
	onTimeMillisChanged: (Long?) -> Unit,
	onDismiss: () -> Unit
) {
	val datePickerState = rememberDatePickerState(
		initialDisplayedMonthMillis = System.currentTimeMillis(),
		yearRange = 1900..2000,
		selectableDates = object : SelectableDates {
			override fun isSelectableDate(utcTimeMillis: Long): Boolean {
				return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
					val dayOfWeek = Instant.ofEpochMilli(utcTimeMillis).atZone(ZoneId.of("UTC"))
						.toLocalDate().dayOfWeek
					dayOfWeek != DayOfWeek.SUNDAY && dayOfWeek != DayOfWeek.SATURDAY
				} else {
					val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
					calendar.timeInMillis = utcTimeMillis
					calendar[Calendar.DAY_OF_WEEK] != Calendar.SUNDAY && calendar[Calendar.DAY_OF_WEEK] != Calendar.SATURDAY
				}
			}

			override fun isSelectableYear(year: Int): Boolean {
				return true
			}
		}
	)

	DatePickerDialog(
		onDismissRequest = {
			onDismiss()
		}, confirmButton = {
			onDismiss()
		}
	) {
		onTimeMillisChanged(datePickerState.selectedDateMillis)
		DatePicker(state = datePickerState)
	}
}

@Composable
fun TaskOutlinedTextField(
	modifier: Modifier = Modifier,
	content: String,
	onValueChange: (String) -> Unit,
) {
	Surface(
		modifier = modifier.fillMaxWidth(),
		color = MaterialTheme.colorScheme.surfaceVariant
	) {
		OutlinedTextField(
			modifier = Modifier
				.padding(
					horizontal = dimensionResource(id = R.dimen.padding_medium),
					vertical = dimensionResource(id = R.dimen.padding_small)
				),
			colors = OutlinedTextFieldDefaults.colors(
				unfocusedContainerColor = Color.White
			),
			shape = RoundedCornerShape(dimensionResource(R.dimen.round_extra_large)),
			value = content,
			onValueChange = onValueChange,
			trailingIcon = {
				IconButton(onClick = { }) {
					Icon(
						painter = painterResource(id = R.drawable.ic_pencil),
						contentDescription = null,
						tint = MaterialTheme.colorScheme.surfaceTint
					)
				}
			}
		)
	}
}

@Preview(showBackground = true)
@Composable
fun TaskOutlinedTextFieldLightPreview() {
	NousTheme {
		TaskOutlinedTextField(
			content = "",
			onValueChange = {},
		)
	}
}

@Preview(showBackground = true)
@Composable
fun TaskOutlinedTextFieldDarkPreview() {
	NousTheme(darkTheme = true) {
		TaskOutlinedTextField(
			content = "",
			onValueChange = {},
		)
	}
}
