package com.schopenhauer.nous.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.schopenhauer.nous.ui.navigation.graph.JOURNAL_GRAPH
import com.schopenhauer.nous.ui.navigation.graph.journalGraph
import com.schopenhauer.nous.ui.navigation.graph.navigateToJournalTopLevel
import com.schopenhauer.nous.ui.navigation.graph.navigateToNews
import com.schopenhauer.nous.ui.navigation.graph.navigateToSetting
import com.schopenhauer.nous.ui.navigation.graph.newsGraph
import com.schopenhauer.nous.ui.navigation.graph.settingGraph

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

fun NavController.navigateTopLevelDestination(topLevelDestination: TopLevelDestination) {
	val topLevelNavOptions = navOptions {
		popUpTo(graph.findStartDestination().id) {
			saveState = true
		}
		launchSingleTop = true
		restoreState = true
	}

	when(topLevelDestination) {
		TopLevelDestination.NEWS -> navigateToNews(topLevelNavOptions)
		TopLevelDestination.JOURNAL -> navigateToJournalTopLevel(topLevelNavOptions)
		TopLevelDestination.SETTING -> navigateToSetting(topLevelNavOptions)
	}
}