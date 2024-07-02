package com.schopenhauer.nous.ui.journals

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

const val JOURNALS_SCREEN = "journals"

fun NavGraphBuilder.journalsScreen(navController: NavHostController) {
	composable(route = JOURNALS_SCREEN) {
		JournalsScreen(
			onJournalClick = {},
			onWriteButtonClick = {}
		)
	}
}