package com.schopenhauer.nous.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.schopenhauer.nous.ui.detail.TDetailFragment
import com.schopenhauer.nous.ui.journals.JournalsFragment

class MainViewPagerAdapter(fragmentActivity: FragmentActivity) :
  FragmentStateAdapter(fragmentActivity) {

  override fun createFragment(position: Int): Fragment {
    return when (position) {
      JOURNALS_FRAGMENT_POSITION -> JournalsFragment.newInstance()
      FRG_DETAIL_POSITION -> TDetailFragment.newInstance(0)
      else -> throw IllegalArgumentException("Invalid position")
    }
  }

  override fun getItemCount(): Int = FRAGMENT_COUNT

  companion object {
    private const val FRAGMENT_COUNT = 2
    const val JOURNALS_FRAGMENT_POSITION = 0
    const val FRG_DETAIL_POSITION = 1
  }
}
