package com.schopenhauer.nous.ui.journal.write

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.schopenhauer.nous.R
import com.schopenhauer.nous.databinding.FragmentWriteJournalBinding
import com.schopenhauer.nous.ui.base.BaseFragment
import com.schopenhauer.nous.ui.main.MainActivity
import com.schopenhauer.nous.util.millisToDate
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class WriteJournalFragment : BaseFragment<FragmentWriteJournalBinding>() {
	private val viewModel: WriteJournalViewModel by viewModels()
	private var onBackPressedCallback: OnBackPressedCallback? = null
	private var datePicker: MaterialDatePicker<Long>? = null

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

	override fun onDestroy() {
		super.onDestroy()
		(activity as MainActivity).showBottomNavigationView()
		(activity as MainActivity).isNotDefaultNavHost = false
	}

	override fun initViews() {
		initDatePicker()

		binding.topAppBar.setNavigationOnClickListener {
			findNavController().popBackStack()
		}

		binding.dateInputLayout.setEndIconOnClickListener {
			datePicker?.show(childFragmentManager, TAG)
		}
	}

	private fun initDatePicker() {
		val todayInMillis = MaterialDatePicker.todayInUtcMilliseconds()
		val oneMonthAgo = Calendar.getInstance()
		oneMonthAgo.add(Calendar.MONTH, -1)

		val constraintsBuilder = CalendarConstraints.Builder()
			.setStart(oneMonthAgo.timeInMillis)
			.setEnd(todayInMillis)
			.setValidator(DateValidatorPointBackward.now())

		datePicker = MaterialDatePicker.Builder.datePicker()
			.setCalendarConstraints(constraintsBuilder.build())
			.setSelection(todayInMillis)
			.build()

		datePicker?.addOnPositiveButtonClickListener {
			viewModel.setDate(millisToDate(it))
		}
	}

	override fun onDetach() {
		super.onDetach()
		onBackPressedCallback?.remove()
		datePicker?.clearOnPositiveButtonClickListeners()
	}

	override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentWriteJournalBinding =
		{ inflater, container, isAttach ->
			DataBindingUtil.inflate<FragmentWriteJournalBinding?>(
				inflater,
				R.layout.fragment_write_journal,
				container,
				isAttach
			).also { binding ->
				binding.lifecycleOwner = this@WriteJournalFragment
				binding.vm = viewModel
			}
		}

	companion object {
		const val TAG = "WriteJournalFragment"
	}
}