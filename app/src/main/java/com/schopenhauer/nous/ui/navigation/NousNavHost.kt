package com.schopenhauer.nous.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun NousNavHost(
	modifier: Modifier = Modifier,
	navController: NavHostController,
	startDestination: String = JOURNAL_GRAPH
) {
	NavHost(
		modifier = modifier,
		navController = navController,
		startDestination = startDestination
	) {
		newsGraph(navController)
		journalGraph(navController)
		settingGraph(navController)
	}
}