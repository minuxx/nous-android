package com.schopenhauer.nous.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.schopenhauer.nous.ui.journals.journalsScreen

object JournalRoutes {
	const val GRAPH = "journal_graph"
	const val JOURNALS = "journals"
	const val WRITE_JOURNAL = "write_journal"
	const val JOURNAL_DETAIL = "journal_detail"
}

fun NavGraphBuilder.journalGraph(navController: NavHostController) {
	navigation(
		startDestination = JournalRoutes.JOURNALS,
		route = JournalRoutes.GRAPH
	) {
		journalsScreen(navController)
	}
}