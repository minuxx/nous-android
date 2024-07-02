package com.schopenhauer.nous.ui.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.schopenhauer.nous.ui.journal_write.writeJournalScreen
import com.schopenhauer.nous.ui.journals.JOURNALS_SCREEN
import com.schopenhauer.nous.ui.journals.journalsScreen


const val JOURNAL_GRAPH = "journal_graph"

fun NavController.navigateToJournal(navOptions: NavOptions) = navigate(JOURNAL_GRAPH, navOptions)

fun NavGraphBuilder.journalGraph(navController: NavHostController) {
	navigation(
		route = JOURNAL_GRAPH,
		startDestination = JOURNALS_SCREEN
	) {
		journalsScreen()
		writeJournalScreen(onClickBack = { navController.popBackStack() })
	}
}