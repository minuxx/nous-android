package com.schopenhauer.nous.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController

@Composable
fun NousNavHost(
	modifier: Modifier = Modifier,
	navController: NavHostController,
	startDestination: String = JournalRoutes.JOURNALS
) {
	NavHost(
		modifier = modifier,
		navController = navController,
		startDestination = startDestination
	) {
		journalGraph(navController)
	}
}