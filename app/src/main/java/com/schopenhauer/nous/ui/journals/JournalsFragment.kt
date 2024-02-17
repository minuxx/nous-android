package com.schopenhauer.nous.ui.journals

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
		journalAdapter = JournalAdapter {
			viewModel.toggleBookmark(it)
			Toast.makeText(requireActivity(), "눌림", Toast.LENGTH_SHORT).show()
		}

		binding.recyclerView.apply {
			layoutManager = LinearLayoutManager(requireActivity())
			adapter = journalAdapter
			itemAnimator = null
			setHasFixedSize(true)
		}

		binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
			override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
				if (dy < 0 || dy > 0 && binding.writeJournalFab.isShown) binding.writeJournalFab.hide()
			}

			override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
				if (newState == RecyclerView.SCROLL_STATE_IDLE) binding.writeJournalFab.show()
				super.onScrollStateChanged(recyclerView, newState)
			}
		})
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		Log.d(TAG, "onViewCreated")

    collectStateFlow(viewModel.uiState.map { it.journals }.distinctUntilChanged()) {
      journalAdapter.submitList(it)
			(view.parent as? ViewGroup)?.doOnPreDraw {
				startPostponedEnterTransition()
			}
    }
	}

	companion object {
		const val TAG = "JournalsFragment"
		fun newInstance(): JournalsFragment = JournalsFragment()
		const val JOURNAL_ID_ARGUMENT = "journal-id"
	}
}
