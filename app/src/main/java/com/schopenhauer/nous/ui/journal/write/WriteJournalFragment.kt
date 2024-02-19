package com.schopenhauer.nous.ui.journal.write

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.schopenhauer.nous.databinding.FragmentWriteJournalBinding
import com.schopenhauer.nous.ui.base.BaseFragment
import com.schopenhauer.nous.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WriteJournalFragment : BaseFragment<FragmentWriteJournalBinding>() {
	override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentWriteJournalBinding =
		{ layoutInflater, container, isAttach ->
			FragmentWriteJournalBinding.inflate(layoutInflater, container, isAttach)
		}
	private lateinit var onBackPressedCallback: OnBackPressedCallback

	override fun onAttach(context: Context) {
		super.onAttach(context)
		onBackPressedCallback = object : OnBackPressedCallback(true) {
			override fun handleOnBackPressed() {
				findNavController().popBackStack()
			}
		}
		(activity as MainActivity).onBackPressedDispatcher.addCallback(onBackPressedCallback)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		(activity as MainActivity).hideBottomNavigationView()
	}

	override fun onDestroy() {
		super.onDestroy()
		(activity as MainActivity).showBottomNavigationView()
	}

	override fun onDetach() {
		super.onDetach()
		onBackPressedCallback.remove()
	}

	override fun initViews() {}

	companion object {
		const val TAG = "WriteJournalFragment"
	}
}