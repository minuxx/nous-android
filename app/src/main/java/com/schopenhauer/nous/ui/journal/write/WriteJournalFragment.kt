package com.schopenhauer.nous.ui.journal.write

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.schopenhauer.nous.databinding.FragmentWriteJournalBinding
import com.schopenhauer.nous.ui.base.BaseFragment
import com.schopenhauer.nous.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WriteJournalFragment : BaseFragment<FragmentWriteJournalBinding>() {
	override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentWriteJournalBinding =
		{ layoutInflater, container, isAttach ->
			FragmentWriteJournalBinding.inflate(layoutInflater, container, isAttach)
		}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		(requireActivity() as MainActivity).hideBottomNavigationView()
	}

	override fun initViews() {}

	override fun onDestroy() {
		super.onDestroy()
		(requireActivity() as MainActivity).showBottomNavigationView()
	}

	companion object {
		const val TAG = "WriteJournalFragment"
	}
}