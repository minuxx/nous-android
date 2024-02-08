package com.schopenhauer.nous.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.schopenhauer.nous.ui.detail.TDetailFragment
import com.schopenhauer.nous.ui.list.TListFragment

class MainViewPagerAdapter(fragmentActivity: FragmentActivity) :
  FragmentStateAdapter(fragmentActivity) {

  override fun createFragment(position: Int): Fragment {
    return when (position) {
      FRG_LIST_POSITION -> TListFragment.newInstance()
      FRG_DETAIL_POSITION -> TDetailFragment.newInstance(0)
      else -> throw IllegalArgumentException("Invalid position")
    }
  }

  override fun getItemCount(): Int = FRAGMENT_COUNT

  companion object {
    private const val FRAGMENT_COUNT = 2
    const val FRG_LIST_POSITION = 0
    const val FRG_DETAIL_POSITION = 1
  }
}
