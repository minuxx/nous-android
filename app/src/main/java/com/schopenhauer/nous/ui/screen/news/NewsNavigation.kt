package com.schopenhauer.nous.ui.screen.news

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val NEWS_SCREEN = "news"

fun NavGraphBuilder.newsScreen() {
	composable(route = NEWS_SCREEN) {
		NewsScreen()
	}
}