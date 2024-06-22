package com.schopenhauer.nous.ui.base

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
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

abstract class BaseFragment<VB : ViewBinding> : Fragment() {
	abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
	private var _binding: VB? = null
	val binding get() = _binding!!
	private var onBackPressedCallback: OnBackPressedCallback? = null

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

	fun showToastMessage(message: String) {
		Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
	}

	fun <T> Fragment.collectState(flow: Flow<T>, collector: FlowCollector<T>) {
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
