package com.schopenhauer.nous.ui.screen.journals

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val JOURNALS_SCREEN = "journals"

fun NavGraphBuilder.journalsScreen(
	onJournalClick: (Long) -> Unit,
	onWriteButtonClick: () -> Unit
	) {
	composable(route = JOURNALS_SCREEN) {
		JournalsScreen(
			onJournalClick = onJournalClick,
			onWriteButtonClick = onWriteButtonClick
		)
	}
}