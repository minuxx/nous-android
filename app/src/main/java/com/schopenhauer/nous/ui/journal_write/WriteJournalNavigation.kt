package com.schopenhauer.nous.ui.journal_write

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val WRITE_JOURNAL_SCREEN = "write_journal"

fun NavGraphBuilder.writeJournalScreen(onClickBack: () -> Unit) {
	composable(route = WRITE_JOURNAL_SCREEN) {
		WriteJournalScreen(
			onClickBack = onClickBack
		)
	}
}