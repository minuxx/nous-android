package com.schopenhauer.nous.ui.screen.journal

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val JOURNAL_SCREEN = "journal"
const val JOURNAL_ID_ARG = "journalId"

fun NavController.navigateToJournal(journalId: Long, navOptions: NavOptions? = null) =
	navigate("$JOURNAL_SCREEN/$journalId", navOptions)

fun NavGraphBuilder.journalScreen(onBackClick: () -> Unit) {
	composable(
		route = "$JOURNAL_SCREEN/{$JOURNAL_ID_ARG}",
		arguments = listOf(
			navArgument(name = JOURNAL_ID_ARG) {
				type = NavType.LongType
			}
		)
	) {
		JournalScreen(
			onBackClick = onBackClick
		)
	}
}