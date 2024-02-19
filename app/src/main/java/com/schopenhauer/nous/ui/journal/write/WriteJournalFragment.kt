package com.schopenhauer.nous.ui.journal.write

import android.view.LayoutInflater
import android.view.ViewGroup
import com.schopenhauer.nous.databinding.FragmentWriteJournalBinding
import com.schopenhauer.nous.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WriteJournalFragment : BaseFragment<FragmentWriteJournalBinding>() {
	override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentWriteJournalBinding =
		{ layoutInflater, container, isAttach ->
			FragmentWriteJournalBinding.inflate(layoutInflater, container, isAttach)
		}

	override fun initViews() {}

	companion object {
		const val TAG = "WriteJournalFragment"
	}
}