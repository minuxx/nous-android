package com.schopenhauer.nous.ui.screen.journals

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val JOURNALS_SCREEN = "journals"

fun NavGraphBuilder.journalsScreen(
	onNavigateToJournal: (journalId: Long) -> Unit,
	onNavigateToWriteJournal: () -> Unit
	) {
	composable(route = JOURNALS_SCREEN) {
		JournalsScreen(
			onNavigateToJournal = onNavigateToJournal,
			onNavigateToWriteJournal = onNavigateToWriteJournal
		)
	}
}