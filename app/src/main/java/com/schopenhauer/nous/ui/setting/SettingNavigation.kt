package com.schopenhauer.nous.ui.setting

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val SETTING_SCREEN = "setting_screen"

fun NavGraphBuilder.settingScreen() {
	composable(route = SETTING_SCREEN) {
		SettingScreen()
	}
}