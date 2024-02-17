package com.schopenhauer.nous.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.plusAssign
import androidx.navigation.ui.setupWithNavController
import com.schopenhauer.nous.R
import com.schopenhauer.nous.databinding.ActivityMainBinding
import com.schopenhauer.nous.ui.base.KeepStateNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
	private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
	private lateinit var navController: NavController

	override fun onCreate(savedInstanceState: Bundle?) {
		installSplashScreen()
		super.onCreate(savedInstanceState)
		setContentView(binding.root)
		val navHostFragment =
			supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
		val navigator =
			KeepStateNavigator(this, navHostFragment.childFragmentManager, binding.navHostFragment.id)

		navController = navHostFragment.navController
		navController.navigatorProvider += navigator
		navController.setGraph(R.navigation.nav_graph)
		binding.bottomNav.setupWithNavController(navController)
	}

	fun hideBottomNavigationView() {
		binding.bottomNav.visibility = View.GONE
	}

	fun showBottomNavigationView() {
		binding.bottomNav.visibility = View.VISIBLE
	}

	companion object {
		private const val TAG = "MainActivity"
	}
}