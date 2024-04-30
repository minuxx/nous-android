package com.schopenhauer.nous.ui.journal.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.schopenhauer.nous.R
import com.schopenhauer.nous.databinding.FragmentJournalDetailBinding
import com.schopenhauer.nous.ui.base.BaseFragment
import com.schopenhauer.nous.ui.journal.detail.JournalDetailViewModel.UiEvent
import com.schopenhauer.nous.ui.journal.list.JournalsFragment.Companion.JOURNAL_ID_KEY
import com.schopenhauer.nous.ui.journal.TaskAdapter
import com.schopenhauer.nous.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class JournalDetailFragment : BaseFragment<FragmentJournalDetailBinding>() {
	private val viewModel: JournalDetailViewModel by viewModels()
	private lateinit var taskAdapter: TaskAdapter
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

	override fun initViews() {
		initTaskRecyclerView()

		binding.topAppBar.setNavigationOnClickListener {
			findNavController().popBackStack()
		}

		binding.topAppBar.setOnMenuItemClickListener {
			viewModel.removeJournal()
			true
		}
	}

	private fun initTaskRecyclerView() {
		taskAdapter = TaskAdapter(isDeletable = false)
		binding.taskRecyclerView.apply {
			layoutManager = LinearLayoutManager(requireActivity())
			adapter = taskAdapter
			itemAnimator = null
			setHasFixedSize(false)
			isNestedScrollingEnabled = false
		}
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
		collectState(viewModel.uiState.map { it.tasks }.distinctUntilChanged()) {
			taskAdapter.submitList(it)
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
		{ inflater, container, isAttach ->
			DataBindingUtil.inflate<FragmentJournalDetailBinding?>(
				inflater,
				R.layout.fragment_journal_detail,
				container,
				isAttach
			).also { binding ->
				binding.lifecycleOwner = this@JournalDetailFragment
				binding.vm = viewModel
			}
		}

	companion object {
		const val TAG = "JournalDetailFragment"
	}
}