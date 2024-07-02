package com.schopenhauer.nous.ui.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.schopenhauer.nous.ui.screen.journal.journalScreen
import com.schopenhauer.nous.ui.screen.journal.navigateToJournal
import com.schopenhauer.nous.ui.screen.journal_write.WRITE_JOURNAL_SCREEN
import com.schopenhauer.nous.ui.screen.journal_write.navigateToWriteJournal
import com.schopenhauer.nous.ui.screen.journal_write.writeJournalScreen
import com.schopenhauer.nous.ui.screen.journals.JOURNALS_SCREEN
import com.schopenhauer.nous.ui.screen.journals.journalsScreen


const val JOURNAL_GRAPH = "journal_graph"

fun NavController.navigateToJournalTopLevel(navOptions: NavOptions) = navigate(JOURNAL_GRAPH, navOptions)

fun NavGraphBuilder.journalGraph(navController: NavHostController) {
	navigation(
		route = JOURNAL_GRAPH,
		startDestination = JOURNALS_SCREEN
	) {
		journalsScreen(
			onNavigateToJournal = { journalId ->
				navController.navigateToJournal(journalId)
			},
			onNavigateToWriteJournal = {
				navController.navigateToWriteJournal()
			}
		)
		writeJournalScreen(
			onNavigateBack = {
				navController.popBackStack()
			}
		)
		journalScreen(
			onNavigateBack = {
				navController.popBackStack()
			}
		)
	}
}