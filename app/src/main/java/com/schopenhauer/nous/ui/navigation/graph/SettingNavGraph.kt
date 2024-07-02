package com.schopenhauer.nous.ui.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.schopenhauer.nous.ui.screen.setting.SETTING_SCREEN
import com.schopenhauer.nous.ui.screen.setting.settingScreen

const val SETTING_GRAPH = "setting_graph"

fun NavController.navigateToSetting(navOptions: NavOptions) = navigate(SETTING_GRAPH, navOptions)

fun NavGraphBuilder.settingGraph(navController: NavHostController) {
	navigation(
		route = SETTING_GRAPH,
		startDestination = SETTING_SCREEN
	) {
		settingScreen()
	}
}