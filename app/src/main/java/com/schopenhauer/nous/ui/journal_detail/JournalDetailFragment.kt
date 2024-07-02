package com.schopenhauer.nous.ui.journal_detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.schopenhauer.nous.databinding.FragmentJournalDetailBinding
import com.schopenhauer.nous.ui.base.BaseFragment
import com.schopenhauer.nous.ui.journal_detail.JournalDetailViewModel.UiEvent
import com.schopenhauer.nous.ui.journals.JournalsFragment.Companion.JOURNAL_ID_KEY
import com.schopenhauer.nous.MainActivity
import com.schopenhauer.nous.ui.theme.NousTheme
import com.schopenhauer.nous.util.getTodayTimeMillis
import com.schopenhauer.nous.util.millisToDate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class JournalDetailFragment : BaseFragment<FragmentJournalDetailBinding>() {
	private val viewModel: JournalDetailViewModel by viewModels()
	private var onBackPressedCallback: OnBackPressedCallback? = null


	override fun onAttach(context: Context) {
		super.onAttach(context)
		onBackPressedCallback = object : OnBackPressedCallback(true) {
			override fun handleOnBackPressed() {
				findNavController().popBackStack()
			}
		}
		(activity as MainActivity).onBackPressedDispatcher.addCallback(this, onBackPressedCallback!!)
		(activity as MainActivity).hideBottomNavigationView()
	}

	override fun initViews() {}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		super.onCreateView(inflater, container, savedInstanceState)
		binding.composeView.apply {
			setContent {
				NousTheme {
					setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
					val uiState by viewModel.uiState.collectAsState()

					Surface {
						JournalDetailScreen(
							date = millisToDate(uiState.timeMillis ?: getTodayTimeMillis()),
							tasks = uiState.tasks,
							onClickBack = { findNavController().popBackStack() },
							onJournalRemove = { viewModel.removeJournal() }
						)
					}
				}
			}
		}

		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		readArguments()
		collectUiState()
		collectUiEvent()
	}

	private fun readArguments() {
		val journalId = arguments?.getLong(JOURNAL_ID_KEY, 0L) ?: 0L
		if (journalId == 0L) {
			findNavController().popBackStack()
			return
		}
		viewModel.setJournalId(journalId)
	}

	private fun collectUiState() {
		collectState(viewModel.uiState.map { it.isLoading }.distinctUntilChanged()) { isLoading ->
			binding.loadingBar.visibility = if (isLoading) {
				View.VISIBLE
			} else {
				View.GONE
			}
		}
	}

	private fun collectUiEvent() {
		collectState(viewModel.uiEvent) { event ->
			when (event) {
				is UiEvent.OnSuccessRemoveJournal -> findNavController().popBackStack()
				is UiEvent.OnFailLoadJournal -> findNavController().popBackStack()
				is UiEvent.OnShowToastMessage -> Toast.makeText(requireActivity(), event.message, Toast.LENGTH_SHORT).show()
			}
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		(activity as MainActivity).showBottomNavigationView()
		(activity as MainActivity).isNotDefaultNavHost = false
	}

	override fun onDetach() {
		super.onDetach()
		onBackPressedCallback?.remove()
	}

	override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentJournalDetailBinding =
		{ layoutInflater, container, isAttach ->
			FragmentJournalDetailBinding.inflate(layoutInflater, container, isAttach)
		}

	companion object {
		const val TAG = "JournalDetailFragment"
	}
}