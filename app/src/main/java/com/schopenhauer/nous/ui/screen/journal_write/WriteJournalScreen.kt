package com.schopenhauer.nous.ui.screen.journal_write

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.schopenhauer.nous.R
import com.schopenhauer.nous.domain.model.Task
import com.schopenhauer.nous.ui.component.JournalDatePickerDialog
import com.schopenhauer.nous.ui.component.NousAppBar
import com.schopenhauer.nous.ui.screen.journal.TaskItemColumn
import com.schopenhauer.nous.ui.theme.NousTheme
import com.schopenhauer.nous.util.getTodayTimeMillis
import com.schopenhauer.nous.util.millisToDate

@Composable
fun WriteJournalScreen(
	modifier: Modifier = Modifier,
	viewModel: WriteJournalViewModel = hiltViewModel(),
	onBackClick: () -> Unit
) {
	val uiState by viewModel.uiState.collectAsState()

	WriteJournalScreen(
		modifier = modifier,
		selectedDateMillis = uiState.timeMillis,
		onDateMillisChanged = viewModel::setTimeMillis,
		tasks = uiState.tasks,
		onWriteTask = viewModel::writeTask,
		onRemoveTask = viewModel::removeTask,
		onBackClick = onBackClick,
		onSaveJournal = viewModel::saveJournal
	)
}

@Composable
fun WriteJournalScreen(
	modifier: Modifier = Modifier,
	selectedDateMillis: Long?,
	tasks: List<Task>,
	onBackClick: () -> Unit,
	onSaveJournal: () -> Unit,
	onRemoveTask: (Long) -> Unit,
	onWriteTask: (String) -> Unit,
	onDateMillisChanged: (Long) -> Unit
) {
	var shouldShowDatePicker by rememberSaveable { mutableStateOf(false) }

	Column(
		modifier = modifier.fillMaxSize()
	) {
		NousAppBar(
			title = stringResource(id = R.string.write_journal),
			onLeftIconClick = onBackClick,
			onRightClickIcon = onSaveJournal,
			rightText = stringResource(id = R.string.save)
		)
		Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
		DateSelectField(
			modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = dimensionResource(id = R.dimen.padding_medium)),
			date = millisToDate(selectedDateMillis ?: getTodayTimeMillis()),
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
		TaskOutlinedTextField(onWriteTask = onWriteTask)

		if (shouldShowDatePicker) {
			JournalDatePickerDialog(
				title = stringResource(id = R.string.dialog_date_picker_title_journal),
				selectedDateMillis = selectedDateMillis,
				onTimeMillisChanged = onDateMillisChanged,
				onDismiss = { shouldShowDatePicker = false }
			)
		}
	}
}

@Preview(showBackground = true)
@Composable
fun WriteJournalScreenPreview() {
	NousTheme {
		Surface {
			WriteJournalScreen(
				selectedDateMillis = System.currentTimeMillis(),
				tasks = listOf(
					Task(
						id = 1,
						content = "컴포즈 마이그레이션",
					)
				),
				onBackClick = {},
				onSaveJournal = {},
				onRemoveTask = {},
				onWriteTask = {},
				onDateMillisChanged = {}
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
				selectedDateMillis = System.currentTimeMillis(),
				tasks = listOf(
					Task(
						id = 1,
						content = "컴포즈 마이그레이션",
					)
				),
				onBackClick = {},
				onSaveJournal = {},
				onRemoveTask = {},
				onWriteTask = {},
				onDateMillisChanged = {}
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


@Composable
fun TaskOutlinedTextField(
	modifier: Modifier = Modifier,
	onWriteTask: (String) -> Unit,
) {
	var taskInputText by rememberSaveable { mutableStateOf("") }

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
				focusedTextColor = Color.Black,
				focusedContainerColor = Color.White,
				unfocusedContainerColor = Color.White
			),
			shape = RoundedCornerShape(dimensionResource(R.dimen.round_extra_large)),
			value = taskInputText,
			onValueChange = {
				taskInputText = it
			},
			trailingIcon = {
				IconButton(onClick = {
					onWriteTask(taskInputText)
					taskInputText = ""
				}) {
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
			onWriteTask = {},
		)
	}
}

@Preview(showBackground = true)
@Composable
fun TaskOutlinedTextFieldDarkPreview() {
	NousTheme(darkTheme = true) {
		TaskOutlinedTextField(
			onWriteTask = {},
		)
	}
}
