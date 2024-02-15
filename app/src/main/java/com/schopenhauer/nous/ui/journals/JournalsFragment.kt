package com.schopenhauer.nous.ui.journals

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.schopenhauer.nous.databinding.FragmentJournalsBinding
import com.schopenhauer.nous.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class JournalsFragment : BaseFragment<FragmentJournalsBinding>() {
  override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentJournalsBinding =
    { layoutInflater, container, isAttach ->
			FragmentJournalsBinding.inflate(layoutInflater, container, isAttach)
    }

	private val viewModel: JournalsViewModel by viewModels()
	private lateinit var journalAdapter: JournalAdapter

  override fun initViews() {
    initRecyclerView()
  }

	private fun initRecyclerView() {
		journalAdapter = JournalAdapter { viewModel.toggleBookmark(it) }

		binding.recyclerView.apply {
			layoutManager = LinearLayoutManager(requireActivity())
			adapter = journalAdapter
			itemAnimator = null
			setHasFixedSize(true)
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

    collectStateFlow(viewModel.uiState.map { it.journals }.distinctUntilChanged()) {
      journalAdapter.submitList(it)
    }
	}

	companion object {
		fun newInstance(): JournalsFragment = JournalsFragment()
		private const val TAG = "JournalsFragment"
		const val JOURNAL_ID_ARGUMENT = "journal-id"
	}
}
