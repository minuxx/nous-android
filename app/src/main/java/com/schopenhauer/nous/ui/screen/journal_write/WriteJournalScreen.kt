package com.schopenhauer.nous.ui.screen.journal_write

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.schopenhauer.nous.R
import com.schopenhauer.nous.domain.model.Task
import com.schopenhauer.nous.ui.base.SingleEventEffect
import com.schopenhauer.nous.ui.component.JournalDatePickerDialog
import com.schopenhauer.nous.ui.component.NousAppBar
import com.schopenhauer.nous.ui.component.TaskItemColumn
import com.schopenhauer.nous.ui.theme.NousTheme
import com.schopenhauer.nous.util.getTodayTimeMillis
import com.schopenhauer.nous.util.millisToDate

@Composable
fun WriteJournalScreen(
	modifier: Modifier = Modifier,
	viewModel: WriteJournalViewModel = hiltViewModel(),
	onNavigateBack: () -> Unit
) {
	val uiState by viewModel.uiState.collectAsState()
	val context = LocalContext.current

	SingleEventEffect(sideEffectFlow = viewModel.uiEffect) { effect ->
		when (effect) {
			WriteJournalUiEffect.NavigateBack -> {
				onNavigateBack()
			}

			is WriteJournalUiEffect.ShowToast -> {
				Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
			}
		}
	}

	WriteJournalScreen(
		modifier = modifier,
		selectedDateMillis = uiState.timeMillis,
		tasks = uiState.tasks,
		onEvent = viewModel::setUiEvent
	)
}

@Composable
fun WriteJournalScreen(
	modifier: Modifier = Modifier,
	selectedDateMillis: Long?,
	tasks: List<Task>,
	onEvent: (WriteJournalUiEvent) -> Unit
) {
	var shouldShowDatePicker by rememberSaveable { mutableStateOf(false) }

	Column(
		modifier = modifier.fillMaxSize()
	) {
		NousAppBar(
			title = stringResource(id = R.string.write_journal),
			onLeftIconClick = {
				onEvent(WriteJournalUiEvent.OnBackClick)
			},
			onRightClickIcon = {
				onEvent(WriteJournalUiEvent.OnSaveClick)
			},
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
				onEvent(WriteJournalUiEvent.OnRemoveTask(taskId))
			}
		)
		Spacer(modifier = Modifier.weight(1f))
		TaskOutlinedTextField(onWriteTask = { taskContent ->
			onEvent(WriteJournalUiEvent.OnWriteTask(taskContent))
		})

		if (shouldShowDatePicker) {
			JournalDatePickerDialog(
				title = stringResource(id = R.string.dialog_date_picker_title_journal),
				selectedDateMillis = selectedDateMillis,
				onTimeMillisChanged = { timeMillis ->
					onEvent(WriteJournalUiEvent.OnSelectDate(timeMillis))
				},
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
				onEvent = {}
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
				onEvent = {}
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
