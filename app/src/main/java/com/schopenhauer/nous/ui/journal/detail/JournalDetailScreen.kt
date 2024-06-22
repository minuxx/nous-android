package com.schopenhauer.nous.ui.journal.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.schopenhauer.nous.R
import com.schopenhauer.nous.domain.model.Task
import com.schopenhauer.nous.ui.theme.NousTheme

@Composable
fun JournalDetailScreen(
	modifier: Modifier = Modifier,
	date: String,
	tasks: List<Task>,
	onClickBack: () -> Unit,
	onRemoveJournal: () -> Unit
) {
	Column(
		modifier = modifier
			.fillMaxSize()
	) {
		Row(
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier.padding(
				end = dimensionResource(id = R.dimen.padding_medium)
			)
		) {
			IconButton(
				onClick = onClickBack,
			) {
				Icon(
					painter = painterResource(id = R.drawable.ic_angle_left),
					contentDescription = null,
				)
			}
			Text(
				text = stringResource(id = R.string.journal_detail),
				style = MaterialTheme.typography.titleSmall
			)
			Spacer(modifier = Modifier.weight(1f))
			TextButton(onClick = onRemoveJournal) {
				Text(
					text = stringResource(id = R.string.delete),
					style = MaterialTheme.typography.titleSmall,
				)
			}
		}
		Row(
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
			modifier = Modifier.padding(
				vertical = dimensionResource(id = R.dimen.padding_small),
				horizontal = dimensionResource(id = R.dimen.padding_medium)
			)
		) {
			Icon(
				painter = painterResource(id = R.drawable.ic_calendar),
				contentDescription = null,
			)
			Text(
				text = date,
				style = MaterialTheme.typography.displaySmall
			)
		}
		TaskItemColumn(
			tasks = tasks,
			onClick = { }
		)
	}
}

@Preview(showBackground = true)
@Composable
fun JournalDetailScreenPreview() {
	NousTheme {
		JournalDetailScreen(
			date = "2024/06/06",
			tasks = listOf(
				Task(1, "컴포즈 마이그레이션"),
				Task(2, "컴포즈 마이그레이션"),
				Task(3, "컴포즈 마이그레이션"),
				Task(4, "컴포즈 마이그레이션")),
			onClickBack = {},
			onRemoveJournal = {}
		)
	}
}