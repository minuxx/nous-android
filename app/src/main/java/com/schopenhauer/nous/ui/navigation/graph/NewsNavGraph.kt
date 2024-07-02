package com.schopenhauer.nous.ui.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.schopenhauer.nous.ui.screen.news.NEWS_SCREEN
import com.schopenhauer.nous.ui.screen.news.newsScreen


const val NEWS_GRAPH = "news_graph"

fun NavController.navigateToNews(navOptions: NavOptions) = navigate(NEWS_GRAPH, navOptions)

fun NavGraphBuilder.newsGraph(navController: NavHostController) {
	navigation(
		route = NEWS_GRAPH,
		startDestination = NEWS_SCREEN
	) {
		newsScreen()
	}
}