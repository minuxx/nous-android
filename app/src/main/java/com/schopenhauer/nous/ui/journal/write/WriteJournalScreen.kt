package com.schopenhauer.nous.ui.journal.write

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.schopenhauer.nous.R
import com.schopenhauer.nous.domain.model.Task
import com.schopenhauer.nous.ui.component.NousAppBar

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
			onRightClickIcon = onSaveTask
		)

	}
}

@Preview(showBackground = true)
@Composable
fun WriteJournalScreenPreview() {

}