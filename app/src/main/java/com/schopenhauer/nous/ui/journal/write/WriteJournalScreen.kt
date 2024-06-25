package com.schopenhauer.nous.ui.journal.write

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@Composable
fun WriteJournalScreen(
	modifier: Modifier = Modifier,
	date: String,
	tasks: List<Task>,
	onClickBack: () -> Unit,
	onSaveTask: () -> Unit
) {
	Column(
		modifier = modifier.fillMaxSize()
	) {
		NousAppBar(
			title = stringResource(id = R.string.write_journal),
			onLeftIconClick = onClickBack,
			onRightClickIcon = onSaveTask,
			rightText = stringResource(id = R.string.save)
		)
		Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
		Card(
			modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = dimensionResource(id = R.dimen.padding_medium)),
			border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.primary),
			colors = CardDefaults.cardColors(
				containerColor = MaterialTheme.colorScheme.primaryContainer,
			),
			onClick = {}
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
		Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
		TaskItemColumn(
			tasks = tasks,
			onClick = { },
			onClickIcon = { }
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
			) {
			}
		}
	}
}

@Composable
fun TaskOutlinedTextField(
	modifier: Modifier = Modifier,
	content: String,
	onValueChange: (String) -> Unit
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
			shape = RoundedCornerShape(dimensionResource(R.dimen.round_extra_large)),
			value = content,
			onValueChange = onValueChange,

		)
	}
}

@Preview(showBackground = true)
@Composable
fun TaskOutlinedTextFieldLightPreview() {
	NousTheme {
		TaskOutlinedTextField(
			content = "",
			onValueChange = {}
		)
	}
}

@Preview(showBackground = true)
@Composable
fun TaskOutlinedTextFieldDarkPreview() {
	NousTheme(darkTheme = true) {
		TaskOutlinedTextField(
			content = "",
			onValueChange = {}
		)
	}
}
