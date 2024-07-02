package com.schopenhauer.nous.ui.journals

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val JOURNALS_SCREEN = "journals"

fun NavGraphBuilder.journalsScreen() {
	composable(route = JOURNALS_SCREEN) {
		JournalsScreen(
			onJournalClick = {},
			onWriteButtonClick = {}
		)
	}
}