package com.schopenhauer.nous.ui.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
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
	private var doubleBackToExitPressedOnce = false
	var isNotDefaultNavHost = false

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

		savedInstanceState?.let {
			isNotDefaultNavHost = it.getBoolean(DEFAULT_NAV_HOST_FLAG)
		}
	}

	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		outState.putBoolean(DEFAULT_NAV_HOST_FLAG, isNotDefaultNavHost)
	}

	fun hideBottomNavigationView() {
		binding.bottomNav.visibility = View.GONE
	}

	fun showBottomNavigationView() {
		binding.bottomNav.visibility = View.VISIBLE
	}

	fun hideSoftKeyboard() {
		val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
		this.currentFocus?.let {
			imm.hideSoftInputFromWindow(it.windowToken, 0)
		}
	}

	@Deprecated("Deprecated in Java")
	override fun onBackPressed() {
		if (isNotDefaultNavHost || doubleBackToExitPressedOnce) {
			super.onBackPressed()
			return
		}

		doubleBackToExitPressedOnce = true
		Handler(Looper.getMainLooper()).postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
	}

	companion object {
		private const val TAG = "MainActivity"
		private const val DEFAULT_NAV_HOST_FLAG = "default-nav-host"
	}
}