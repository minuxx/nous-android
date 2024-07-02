package com.schopenhauer.nous.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.schopenhauer.nous.ui.navigation.JOURNAL_GRAPH
import com.schopenhauer.nous.ui.navigation.NousNavHost

@Composable
fun NousApp() {
	val navController = rememberNavController()

	Scaffold(
		bottomBar = {
			BottomNavBar()
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
fun BottomNavBar() {
	NavigationBar {

	}
}