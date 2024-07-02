package com.schopenhauer.nous.ui.screen.journal

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.schopenhauer.nous.R
import com.schopenhauer.nous.domain.model.Task
import com.schopenhauer.nous.ui.base.SingleEventEffect
import com.schopenhauer.nous.ui.component.LoadingBar
import com.schopenhauer.nous.ui.component.NousAppBar
import com.schopenhauer.nous.ui.component.TaskItemColumn
import com.schopenhauer.nous.ui.theme.NousTheme
import com.schopenhauer.nous.util.millisToDate

@Composable
fun JournalScreen(
	modifier: Modifier = Modifier,
	viewModel: JournalViewModel = hiltViewModel(),
	onNavigateBack: () -> Unit
) {
	val uiState by viewModel.uiState.collectAsState()
	val context = LocalContext.current

	SingleEventEffect(sideEffectFlow = viewModel.uiEffect) { effect ->
		when(effect) {
			JournalUiEffect.NavigateBack -> {
				onNavigateBack()
			}
			is JournalUiEffect.ShowToast -> {
				Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
			}
		}
	}

	when(val state = uiState) {
		JournalUiState.Idle -> {}
		is JournalUiState.Loaded -> {
			JournalScreen(
				modifier = modifier,
				date = millisToDate(state.timeMillis) ,
				tasks = state.tasks,
				onEvent = viewModel::setUiEvent
			)
		}
		is JournalUiState.Loading -> {
			LoadingBar()
		}
	}
}

@Composable
fun JournalScreen(
	modifier: Modifier = Modifier,
	date: String,
	tasks: List<Task>,
	onEvent: (JournalUiEvent) -> Unit,
) {
	Column(
		modifier = modifier.fillMaxSize()
	) {
		NousAppBar(
			title = stringResource(id = R.string.journal_detail),
			onLeftIconClick = { onEvent(JournalUiEvent.OnBackClick) },
			onRightClickIcon = { onEvent(JournalUiEvent.OnRemoveClick) },
			rightText = stringResource(id = R.string.delete)
		)
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
			onClick = {}
		)
	}
}

@Preview(showBackground = true)
@Composable
fun JournalDetailScreenPreview() {
	NousTheme {
		JournalScreen(
			date = "2024/06/06",
			tasks = listOf(
				Task(1, "컴포즈 마이그레이션"),
				Task(2, "컴포즈 마이그레이션"),
				Task(3, "컴포즈 마이그레이션"),
				Task(4, "컴포즈 마이그레이션")),
			onEvent = {}
		)
	}
}