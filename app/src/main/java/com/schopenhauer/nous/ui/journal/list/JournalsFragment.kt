package com.schopenhauer.nous.ui.journal.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.schopenhauer.nous.R
import com.schopenhauer.nous.databinding.FragmentJournalsBinding
import com.schopenhauer.nous.ui.base.BaseFragment
import com.schopenhauer.nous.ui.journal.detail.JournalDetailScreen
import com.schopenhauer.nous.ui.journal.list.JournalsViewModel.UiEvent
import com.schopenhauer.nous.ui.main.MainActivity
import com.schopenhauer.nous.ui.theme.NousTheme
import com.schopenhauer.nous.util.getTodayTimeMillis
import com.schopenhauer.nous.util.millisToDate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class JournalsFragment : BaseFragment<FragmentJournalsBinding>() {
	private val viewModel: JournalsViewModel by viewModels()
//	private lateinit var journalAdapter: JournalAdapter

	override fun initViews() {
//		initJournalRecyclerView()
//		binding.writeJournalFab.setOnClickListener {
//			(activity as MainActivity).isNotDefaultNavHost = true
//			findNavController().navigate(R.id.action_journals_to_write_journal)
//		}

		binding.composeView.apply {
			setContent {
				NousTheme {
					setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
					val uiState by viewModel.uiState.collectAsState()

					Surface {
						JournalsScreen(
							journals = uiState.journals,
							onJournalClick = { journalId ->
								(activity as MainActivity).isNotDefaultNavHost = true
								val bundle = Bundle().apply { putLong(JOURNAL_ID_KEY, journalId) }
								findNavController().navigate(R.id.action_journals_to_journal_detail, bundle)
							}
						)
					}
				}
			}
		}
	}


//	private fun initJournalRecyclerView() {
//		journalAdapter = JournalAdapter {
//			(activity as MainActivity).isNotDefaultNavHost = true
//			val bundle = Bundle().apply { putLong(JOURNAL_ID_KEY, it) }
//			findNavController().navigate(R.id.action_journals_to_journal_detail, bundle)
//		}
//
//		binding.journalRecyclerView.apply {
//			layoutManager = LinearLayoutManager(requireActivity())
//			adapter = journalAdapter
//			itemAnimator = null
//			setHasFixedSize(true)
//		}
//
//		binding.journalRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//			override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//				if (dy < 0 || dy > 0 && binding.writeJournalFab.isShown) binding.writeJournalFab.hide()
//			}
//
//			override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//				if (newState == RecyclerView.SCROLL_STATE_IDLE) binding.writeJournalFab.show()
//				super.onScrollStateChanged(recyclerView, newState)
//			}
//		})
//	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		collectUiState()
		collectUiEvent()
	}

	private fun collectUiState() {
//		collectState(viewModel.uiState.map { it.journals }.distinctUntilChanged()) {
//			journalAdapter.submitList(it)
//		}
	}

	private fun collectUiEvent() {
		collectState(viewModel.uiEvent) { event ->
			when (event) {
				is UiEvent.OnShowToastMessage -> Toast.makeText(requireActivity(), event.message, Toast.LENGTH_SHORT).show()
			}
		}
	}

	override fun onStart() {
		super.onStart()
		viewModel.getJournals()
	}

	override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentJournalsBinding =
		{ layoutInflater, container, isAttach ->
			FragmentJournalsBinding.inflate(layoutInflater, container, isAttach)
		}

	companion object {
		const val TAG = "JournalsFragment"
		const val JOURNAL_ID_KEY = "journal-id"
	}
}
