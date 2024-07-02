package com.schopenhauer.nous.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.schopenhauer.nous.ui.journals.JOURNALS_SCREEN
import com.schopenhauer.nous.ui.journals.journalsScreen

const val SETTING_GRAPH = "setting_graph"

fun NavGraphBuilder.settingGraph(navController: NavHostController) {
	navigation(
		startDestination = JOURNALS_SCREEN,
		route = SETTING_GRAPH
	) {
		journalsScreen(navController)
	}
}