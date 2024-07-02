package com.schopenhauer.nous.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.schopenhauer.nous.ui.setting.SETTING_SCREEN
import com.schopenhauer.nous.ui.setting.settingScreen

const val SETTING_GRAPH = "setting_graph"

fun NavGraphBuilder.settingGraph(navController: NavHostController) {
	navigation(
		route = SETTING_GRAPH,
		startDestination = SETTING_SCREEN
	) {
		settingScreen()
	}
}