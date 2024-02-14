package com.schopenhauer.nous.ui.main

import android.view.LayoutInflater
import androidx.viewpager2.widget.ViewPager2
import com.schopenhauer.nous.R
import com.schopenhauer.nous.databinding.ActivityMainBinding
import com.schopenhauer.nous.ui.base.BaseActivity
import com.schopenhauer.nous.ui.main.MainViewPagerAdapter.Companion.FRG_DETAIL_POSITION
import com.schopenhauer.nous.ui.main.MainViewPagerAdapter.Companion.FRG_LIST_POSITION
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
	override val bindingInflater: (LayoutInflater) -> ActivityMainBinding =
		{ layoutInflater ->
			ActivityMainBinding.inflate(layoutInflater)
		}

	override fun initViews() {
		initViewPager()
		initBottomNav()
	}

	private fun initViewPager() {
		binding.viewPager.apply {
			adapter = MainViewPagerAdapter(this@MainActivity)
			registerOnPageChangeCallback(createOnPageChangeCallback())
			isUserInputEnabled = false
		}
	}

	private fun createOnPageChangeCallback(): ViewPager2.OnPageChangeCallback {
		return object : ViewPager2.OnPageChangeCallback() {
			override fun onPageSelected(position: Int) {
				super.onPageSelected(position)
				binding.bottomNav.menu.getItem(position).isChecked = true
			}
		}
	}

	private fun initBottomNav() {
		binding.bottomNav.setOnItemSelectedListener {
			return@setOnItemSelectedListener when (it.itemId) {
				R.id.list -> {
					binding.viewPager.currentItem = FRG_LIST_POSITION
					true
				}
				R.id.detail -> {
					binding.viewPager.currentItem = FRG_DETAIL_POSITION
					true
				}
				else -> false
			}
		}
	}
}