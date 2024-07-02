package com.schopenhauer.nous.ui.screen.journals

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.schopenhauer.nous.R
import com.schopenhauer.nous.domain.model.Journal
import com.schopenhauer.nous.ui.base.SingleEventEffect
import com.schopenhauer.nous.ui.theme.NousTheme
import com.schopenhauer.nous.util.getTodayTimeMillis
import com.schopenhauer.nous.util.millisToDate


@Composable
fun JournalsScreen(
	modifier: Modifier = Modifier,
	viewModel: JournalsViewModel = hiltViewModel(),
	onNavigateToJournal: (journalId: Long) -> Unit,
	onNavigateToWriteJournal: () -> Unit,
) {
	val uiState by viewModel.uiState.collectAsState()
	val context = LocalContext.current

	SingleEventEffect(sideEffectFlow = viewModel.uiEffect) { effect ->
		when (effect) {
			is JournalsUiEffect.ShowToast -> {
				Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
			}
			is JournalsUiEffect.NavigateToJournal -> {
				onNavigateToJournal(effect.journalId)
			}
			is JournalsUiEffect.NavigateToWriteJournal -> {
				onNavigateToWriteJournal()
			}
		}
	}

	when (val state = uiState) {
		is JournalsUiState.Idle -> {}
		is JournalsUiState.Loaded -> {
			JournalsScreen(
				modifier = modifier,
				journals = state.journals,
				onEvent = viewModel::setUiEvent
			)
		}

		is JournalsUiState.Loading -> {}
	}
}

@Composable
fun JournalsScreen(
	modifier: Modifier = Modifier,
	journals: List<Journal>,
	onEvent: (JournalsUiEvent) -> Unit,
) {
	Box(
		modifier = modifier
	) {
		Column {
			Text(
				modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
				text = stringResource(id = R.string.journals),
				style = MaterialTheme.typography.titleLarge
			)
			JournalItemColumn(
				modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium)),
				journals = journals,
				onJournalClick = { journalId ->
					onEvent(JournalsUiEvent.OnJournalClicked(journalId))
				}
			)
		}
		FloatingActionButton(
			onClick = {
				onEvent(JournalsUiEvent.OnWriteButtonClicked)
			},
			modifier = Modifier
				.align(Alignment.BottomEnd)
				.padding(dimensionResource(id = R.dimen.padding_medium))
		) {
			Icon(
				painter = painterResource(id = R.drawable.ic_pencil),
				contentDescription = null
			)
		}
	}
}

@Preview(showBackground = true)
@Composable
fun JournalsScreenLightPreview() {
	NousTheme {
		JournalsScreen(
			journals = listOf(
				Journal(1, getTodayTimeMillis(), listOf()),
				Journal(2, getTodayTimeMillis(), listOf()),
				Journal(3, getTodayTimeMillis(), listOf()),
				Journal(4, getTodayTimeMillis(), listOf()),
				Journal(5, getTodayTimeMillis(), listOf()),
				Journal(6, getTodayTimeMillis(), listOf()),
				Journal(7, getTodayTimeMillis(), listOf()),
				Journal(8, getTodayTimeMillis(), listOf()),
				Journal(9, getTodayTimeMillis(), listOf()),
			),
			onEvent = {},
		)
	}
}

@Preview(showBackground = true)
@Composable
fun JournalsScreenDarkPreview() {
	NousTheme(darkTheme = true) {
		JournalsScreen(
			journals = listOf(
				Journal(1, getTodayTimeMillis(), listOf()),
				Journal(2, getTodayTimeMillis(), listOf()),
				Journal(3, getTodayTimeMillis(), listOf()),
				Journal(4, getTodayTimeMillis(), listOf()),
				Journal(5, getTodayTimeMillis(), listOf()),
				Journal(6, getTodayTimeMillis(), listOf()),
				Journal(7, getTodayTimeMillis(), listOf()),
				Journal(8, getTodayTimeMillis(), listOf()),
				Journal(9, getTodayTimeMillis(), listOf()),
			),
			onEvent = {},
		)
	}
}

@Composable
fun JournalItemColumn(
	modifier: Modifier = Modifier,
	journals: List<Journal>,
	onJournalClick: (journalId: Long) -> Unit
) {
	LazyColumn(
		modifier = modifier.fillMaxWidth(),
		contentPadding = PaddingValues(vertical = dimensionResource(id = R.dimen.padding_small)),
		verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
	)
	{
		items(
			items = journals,
			key = { item ->
				item.id
			}
		) { item ->
			JournalItem(
				date = millisToDate(item.timeMillis),
				onClick = { onJournalClick(item.id) }
			)
		}
	}
}

@Preview(showBackground = true)
@Composable
fun JournalItemColumnLightPreview() {
	NousTheme {
		JournalItemColumn(
			journals = listOf(
				Journal(1, getTodayTimeMillis(), listOf()),
				Journal(2, getTodayTimeMillis(), listOf()),
				Journal(3, getTodayTimeMillis(), listOf()),
				Journal(4, getTodayTimeMillis(), listOf()),
				Journal(5, getTodayTimeMillis(), listOf()),
				Journal(6, getTodayTimeMillis(), listOf()),
				Journal(7, getTodayTimeMillis(), listOf()),
				Journal(8, getTodayTimeMillis(), listOf()),
				Journal(9, getTodayTimeMillis(), listOf()),
			),
			onJournalClick = {}
		)
	}
}

@Preview(showBackground = true)
@Composable
fun JournalItemColumnDarkPreview() {
	NousTheme(darkTheme = true) {
		JournalItemColumn(
			journals = listOf(
				Journal(1, getTodayTimeMillis(), listOf()),
				Journal(2, getTodayTimeMillis(), listOf()),
				Journal(3, getTodayTimeMillis(), listOf()),
				Journal(4, getTodayTimeMillis(), listOf()),
				Journal(5, getTodayTimeMillis(), listOf()),
				Journal(6, getTodayTimeMillis(), listOf()),
				Journal(7, getTodayTimeMillis(), listOf()),
				Journal(8, getTodayTimeMillis(), listOf()),
				Journal(9, getTodayTimeMillis(), listOf()),
			),
			onJournalClick = {}
		)
	}
}

@Composable
fun JournalItem(
	modifier: Modifier = Modifier,
	date: String,
	onClick: () -> Unit
) {
	Card(
		modifier = modifier.fillMaxWidth(),
		shape = RoundedCornerShape(dimensionResource(id = R.dimen.round_small)),
		border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.onPrimaryContainer),
		onClick = onClick
	) {
		Text(
			modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
			text = date,
			style = MaterialTheme.typography.bodyLarge
		)
	}
}

@Preview(showBackground = true)
@Composable
fun JournalItemLightPreview() {
	NousTheme {
		JournalItem(
			date = "2024/07/01",
			onClick = {}
		)
	}
}

@Preview(showBackground = true)
@Composable
fun JournalItemDarkPreview() {
	NousTheme(darkTheme = true) {
		JournalItem(
			date = "2024/07/01",
			onClick = {}
		)
	}
}