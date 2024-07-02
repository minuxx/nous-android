package com.schopenhauer.nous

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.schopenhauer.nous.ui.NousApp
import com.schopenhauer.nous.ui.theme.NousTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		installSplashScreen()
		super.onCreate(savedInstanceState)
		setContent {
			NousTheme {
				NousApp()
			}
		}
	}

	companion object {
		private const val TAG = "MainActivity"
	}
}