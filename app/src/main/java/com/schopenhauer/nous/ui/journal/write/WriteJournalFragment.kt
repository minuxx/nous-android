package com.schopenhauer.nous.ui.journal.write

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.schopenhauer.nous.databinding.FragmentWriteJournalBinding
import com.schopenhauer.nous.ui.base.BaseFragment
import com.schopenhauer.nous.ui.journal.TaskAdapter
import com.schopenhauer.nous.ui.journal.write.WriteJournalViewModel.UiEvent
import com.schopenhauer.nous.ui.main.MainActivity
import com.schopenhauer.nous.util.getTodayTimeMillis
import com.schopenhauer.nous.util.millisToDate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.util.Calendar

@AndroidEntryPoint
class WriteJournalFragment : BaseFragment<FragmentWriteJournalBinding>() {
	private val viewModel: WriteJournalViewModel by viewModels()
	private var datePicker: MaterialDatePicker<Long>? = null
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
		initDatePicker()
		initTaskRecyclerView()

		binding.topAppBar.setNavigationOnClickListener {
			findNavController().popBackStack()
		}

		binding.topAppBar.setOnMenuItemClickListener {
			viewModel.saveJournal()
			true
		}

		binding.dateInputLayout.setEndIconOnClickListener {
			(activity as MainActivity).hideSoftKeyboard()
			datePicker?.show(childFragmentManager, TAG)
		}

		binding.taskInputLayout.setEndIconOnClickListener {
			viewModel.writeTask(binding.taskInputEt.text.toString())
			binding.taskInputEt.text = null
		}
	}

	private fun initDatePicker() {
		val todayMillis = getTodayTimeMillis()
		viewModel.setTimeMillis(todayMillis)

		val oneMonthAgo = Calendar.getInstance()
		oneMonthAgo.add(Calendar.MONTH, -1)

		val constraintsBuilder = CalendarConstraints.Builder()
			.setStart(oneMonthAgo.timeInMillis)
			.setEnd(todayMillis)
			.setValidator(DateValidatorPointBackward.now())

		datePicker = MaterialDatePicker.Builder.datePicker()
			.setCalendarConstraints(constraintsBuilder.build())
			.setSelection(todayMillis)
			.build()

		datePicker?.addOnPositiveButtonClickListener { timeMillis ->
			viewModel.setTimeMillis(timeMillis)
		}
	}

	private fun initTaskRecyclerView() {
		taskAdapter = TaskAdapter(isDeletable = true) { taskId -> viewModel.removeTask(taskId) }
		binding.taskRecyclerView.apply {
			layoutManager = LinearLayoutManager(requireActivity())
			adapter = taskAdapter
			itemAnimator = null
			isNestedScrollingEnabled = false
			setHasFixedSize(false)
			addOnLayoutChangeListener { _, _, _, _, bottom, _, _, _, oldBottom ->
				if (bottom != oldBottom) {
					binding.nestedScrollView.fullScroll(View.FOCUS_DOWN)
					if (taskAdapter.itemCount != 0) binding.taskInputEt.requestFocus()
				}
			}
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		collectUiState()
		collectUiEvent()
	}

	private fun collectUiState() {
		collectState(viewModel.uiState.map { it.tasks }.distinctUntilChanged()) { tasks ->
			taskAdapter.submitList(tasks)
		}

		collectState(viewModel.uiState.map { it.timeMillis }.distinctUntilChanged()) { timeMillis ->
			val date = millisToDate(timeMillis ?: getTodayTimeMillis())
			binding.dateInputEt.setText(date)
		}

		collectState(viewModel.uiState.map { it.isLoading }.distinctUntilChanged()) { isLoading ->
			binding.loadingBar.visibility = if (isLoading) {
				View.VISIBLE
			} else {
				View.GONE
			}
		}
	}

	private fun collectUiEvent() {
		collectState(viewModel.uiEvent) {
			when (it) {
				is UiEvent.OnSuccessSaveJournal -> findNavController().popBackStack()
				is UiEvent.OnShowToastMessage -> Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
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
		datePicker?.clearOnPositiveButtonClickListeners()
	}

	override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentWriteJournalBinding =
		{ layoutInflater, container, isAttach ->
			FragmentWriteJournalBinding.inflate(layoutInflater, container, isAttach)
		}

	companion object {
		const val TAG = "WriteJournalFragment"
	}
}