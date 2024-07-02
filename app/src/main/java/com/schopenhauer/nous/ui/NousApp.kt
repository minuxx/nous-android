package com.schopenhauer.nous.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.schopenhauer.nous.ui.navigation.JOURNAL_GRAPH
import com.schopenhauer.nous.ui.navigation.NousNavHost
import com.schopenhauer.nous.ui.navigation.TopLevelDestination
import com.schopenhauer.nous.ui.navigation.navigateTopLevelDestination

@Composable
fun NousApp() {
	val navController = rememberNavController()
	val navBackStackEntry by navController.currentBackStackEntryAsState()
	val currentDestination = navBackStackEntry?.destination?.route ?: JOURNAL_GRAPH
	var shouldShowBottomNavBarState by rememberSaveable { mutableStateOf(false) }
	shouldShowBottomNavBarState = TopLevelDestination.entries.any { it.route == currentDestination }

	Scaffold(
		bottomBar = {
			if (shouldShowBottomNavBarState) {
				BottomNavBar(
					currentDestination = currentDestination,
					onNavigateToTopLevelDestination = { navController.navigateTopLevelDestination(it) }
				)
			}
		}
	) { innerPadding ->
		NousNavHost(
			modifier = Modifier
				.fillMaxSize()
				.padding(innerPadding),
			navController = navController,
			startDestination = JOURNAL_GRAPH
		)
	}
}

@Composable
fun BottomNavBar(
	currentDestination: String,
	onNavigateToTopLevelDestination: (TopLevelDestination) -> Unit
) {
	NavigationBar {
		TopLevelDestination.entries.forEach { destination ->
			NavigationBarItem(
				icon = {
					Icon(
						painter = painterResource(id = destination.icon),
						contentDescription = null,
					)
				},
				selected = currentDestination == destination.route,
				onClick = { onNavigateToTopLevelDestination(destination) }
			)
		}
	}
}