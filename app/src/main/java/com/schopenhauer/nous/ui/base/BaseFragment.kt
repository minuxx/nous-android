package com.schopenhauer.nous.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.schopenhauer.nous.ui.main.MainActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

abstract class BaseFragment<VB : ViewDataBinding> : Fragment() {
	abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
	private var _binding: VB? = null
	val binding get() = _binding!!
	private var onBackPressedCallback: OnBackPressedCallback? = null
	private var mainActivity: MainActivity? = null

	override fun onAttach(context: Context) {
		super.onAttach(context)
		mainActivity = activity as MainActivity
		if (mainActivity?.isNotDefaultNavHost == true) {
			onBackPressedCallback = object : OnBackPressedCallback(true) {
				override fun handleOnBackPressed() {
					findNavController().popBackStack()
				}
			}
			mainActivity?.onBackPressedDispatcher?.addCallback(this, onBackPressedCallback!!)
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		if (mainActivity?.isNotDefaultNavHost == true) {
			mainActivity?.hideBottomNavigationView()
		}
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_binding = bindingInflater.invoke(inflater, container, false)
		initViews()
		return binding.root
	}

	abstract fun initViews()

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	override fun onDestroy() {
		super.onDestroy()
		if (mainActivity?.isNotDefaultNavHost == true) {
			mainActivity?.showBottomNavigationView()
			mainActivity?.isNotDefaultNavHost = false
		}
	}

	override fun onDetach() {
		super.onDetach()
		onBackPressedCallback?.remove()
	}

	fun showToastMessage(message: String) {
		Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
	}

	fun <T> Fragment.collectStateFlow(flow: Flow<T>, collector: FlowCollector<T>) {
		viewLifecycleOwner.lifecycleScope.launch {
			viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
				flow.collect(collector)
			}
		}
	}

	companion object {
		private const val TAG = "BaseFragment"
	}
}
