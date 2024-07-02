package com.schopenhauer.nous.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import com.schopenhauer.nous.ui.journals.JOURNALS_SCREEN

@Composable
fun NousNavHost(
	modifier: Modifier = Modifier,
	navController: NavHostController,
	startDestination: String = JOURNALS_SCREEN
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