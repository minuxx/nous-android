package com.schopenhauer.nous.ui.navigation

import androidx.annotation.DrawableRes
import com.schopenhauer.nous.R
import com.schopenhauer.nous.ui.journals.JOURNALS_SCREEN
import com.schopenhauer.nous.ui.news.NEWS_SCREEN
import com.schopenhauer.nous.ui.setting.SETTING_SCREEN

enum class TopLevelDestination(
	val route: String,
	@DrawableRes val icon: Int
) {
	NEWS(
		route = NEWS_SCREEN,
		icon = R.drawable.ic_news
	),
	JOURNAL(
		route = JOURNALS_SCREEN,
		icon = R.drawable.ic_briefcase_active
	),
	SETTING(
		route = SETTING_SCREEN,
		icon = R.drawable.ic_setting
	)
}