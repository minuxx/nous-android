package com.schopenhauer.nous.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.schopenhauer.nous.ui.journals.JOURNALS_SCREEN
import com.schopenhauer.nous.ui.journals.journalsScreen


const val JOURNAL_GRAPH = "journal_graph"

fun NavGraphBuilder.journalGraph(navController: NavHostController) {
	navigation(
		route = JOURNAL_GRAPH,
		startDestination = JOURNALS_SCREEN
	) {
		journalsScreen()
	}
}