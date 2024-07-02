package com.schopenhauer.nous.ui.journals

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
import com.schopenhauer.nous.R
import com.schopenhauer.nous.databinding.FragmentJournalsBinding
import com.schopenhauer.nous.ui.base.BaseFragment
import com.schopenhauer.nous.ui.journals.JournalsViewModel.UiEvent
import com.schopenhauer.nous.MainActivity
import com.schopenhauer.nous.ui.theme.NousTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JournalsFragment : BaseFragment<FragmentJournalsBinding>() {
	private val viewModel: JournalsViewModel by viewModels()

	override fun initViews() {
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
							},
							onWriteButtonClick = {
								(activity as MainActivity).isNotDefaultNavHost = true
								findNavController().navigate(R.id.action_journals_to_write_journal)
							}
						)
					}
				}
			}
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		collectUiEvent()
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
