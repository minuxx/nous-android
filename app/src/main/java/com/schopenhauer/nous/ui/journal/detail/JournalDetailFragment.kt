package com.schopenhauer.nous.ui.journal.detail

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.schopenhauer.nous.R
import com.schopenhauer.nous.databinding.FragmentJournalDetailBinding
import com.schopenhauer.nous.ui.base.BaseFragment
import com.schopenhauer.nous.ui.journal.list.JournalsFragment.Companion.JOURNAL_ID_KEY
import com.schopenhauer.nous.ui.journal.write.TaskAdapter
import com.schopenhauer.nous.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JournalDetailFragment : BaseFragment<FragmentJournalDetailBinding>() {
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
	}

	private fun initTaskRecyclerView() {
		taskAdapter = TaskAdapter {

		}

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
	}

	private fun readArguments() {
		val journalId = arguments?.getLong(JOURNAL_ID_KEY, 0L) ?: 0L
		if (journalId == 0L) {
			findNavController().popBackStack()
			return
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
//				binding.vm = viewModel
			}
		}

	companion object {
		const val TAG = "JournalDetailFragment"
	}
}