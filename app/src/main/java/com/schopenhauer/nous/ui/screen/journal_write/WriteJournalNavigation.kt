package com.schopenhauer.nous.ui.screen.journal_write

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val WRITE_JOURNAL_SCREEN = "write_journal"

fun NavController.navigateToWriteJournal() = navigate(WRITE_JOURNAL_SCREEN)

fun NavGraphBuilder.writeJournalScreen(onBackClick: () -> Unit) {
	composable(route = WRITE_JOURNAL_SCREEN) {
		WriteJournalScreen(
			onBackClick = onBackClick
		)
	}
}