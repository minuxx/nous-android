package com.schopenhauer.nous.ui.journal.write

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.schopenhauer.nous.databinding.FragmentWriteJournalBinding
import com.schopenhauer.nous.ui.base.BaseFragment
import com.schopenhauer.nous.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class WriteJournalFragment : BaseFragment<FragmentWriteJournalBinding>() {
	override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentWriteJournalBinding =
		{ layoutInflater, container, isAttach ->
			FragmentWriteJournalBinding.inflate(layoutInflater, container, isAttach)
		}
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
			val selectedDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				val dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd", Locale.getDefault())
				Instant.ofEpochMilli(it)
					.atZone(ZoneId.systemDefault())
					.toLocalDate()
					.format(dateFormat)
			} else {
				val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
				dateFormat.format(Date(it))
			}

			Log.d(TAG, selectedDate)
		}
	}

	override fun onDetach() {
		super.onDetach()
		onBackPressedCallback?.remove()
		datePicker?.clearOnPositiveButtonClickListeners()
	}

	companion object {
		const val TAG = "WriteJournalFragment"
	}
}