package com.schopenhauer.nous.ui.main

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.schopenhauer.nous.R
import com.schopenhauer.nous.databinding.ActivityMainBinding
import com.schopenhauer.nous.ui.base.BaseActivity
import com.schopenhauer.nous.ui.detail.TDetailFragment
import com.schopenhauer.nous.ui.journals.JournalsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
	override val bindingInflater: (LayoutInflater) -> ActivityMainBinding =
		{ layoutInflater ->
			ActivityMainBinding.inflate(layoutInflater)
		}

	private val journalsFragment: JournalsFragment by lazy {
		val fr = supportFragmentManager.findFragmentByTag(JournalsFragment.TAG)
		if (fr != null) fr as JournalsFragment
		else JournalsFragment()
	}

	private val detailFragment: TDetailFragment by lazy {
		val fr = supportFragmentManager.findFragmentByTag(TDetailFragment.TAG)
		if (fr != null) fr as TDetailFragment
		else TDetailFragment()
	}

	private var currentFragmentId = R.id.journals
	private var currentFragment: Fragment? = null

	override fun onBeforeCreate() {
		installSplashScreen()
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		savedInstanceState?.let {
			currentFragmentId = it.getInt(CURRENT_FRAGMENT_ID, R.id.journals)
		}

		currentFragment = when (currentFragmentId) {
			R.id.journals -> journalsFragment
			R.id.detail -> detailFragment
			else -> journalsFragment
		}

		if (savedInstanceState == null)
			supportFragmentManager.beginTransaction()
				.add(R.id.nav_host_fragment, journalsFragment, JournalsFragment.TAG)
				.add(R.id.nav_host_fragment, detailFragment, TDetailFragment.TAG)
				.hide(detailFragment)
				.commit()

		binding.bottomNav.setOnItemSelectedListener { switchFragment(it.itemId) }
	}

	private fun switchFragment(itemId: Int): Boolean {
		val targetFragment = when (itemId) {
			R.id.journals -> journalsFragment
			R.id.detail -> detailFragment
			else -> return false
		}
		if (currentFragment == targetFragment) return false

		supportFragmentManager.beginTransaction()
			.hide(currentFragment!!)
			.show(targetFragment)
			.commit()

		currentFragment = targetFragment
		currentFragmentId = itemId
		return true
	}

	override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
		super.onSaveInstanceState(outState, outPersistentState)
		outState.putInt(CURRENT_FRAGMENT_ID, currentFragmentId)
	}

	companion object {
		private const val TAG = "MainActivity"
		private const val CURRENT_FRAGMENT_ID = "current-fragment-id"
	}
}