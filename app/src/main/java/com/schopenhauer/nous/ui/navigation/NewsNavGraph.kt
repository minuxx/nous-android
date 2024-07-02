package com.schopenhauer.nous.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.schopenhauer.nous.ui.news.NEWS_SCREEN
import com.schopenhauer.nous.ui.news.newsScreen


const val NEWS_GRAPH = "news_graph"

fun NavGraphBuilder.newsGraph(navController: NavHostController) {
	navigation(
		route = NEWS_GRAPH,
		startDestination = NEWS_SCREEN
	) {
		newsScreen()
	}
}