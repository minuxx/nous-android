package com.schopenhauer.nous.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.schopenhauer.nous.R
import com.schopenhauer.nous.databinding.FragmentDetailBinding
import com.schopenhauer.nous.ui.base.BaseFragment
import com.schopenhauer.nous.ui.list.TListFragment.Companion.ARG_ITEM_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TDetailFragment : BaseFragment<FragmentDetailBinding>() {
	private val viewModel: TDetailViewModel by viewModels()

	override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailBinding =
		{ inflater, container, isAttach ->
			DataBindingUtil.inflate<FragmentDetailBinding?>(
				inflater,
				R.layout.fragment_detail,
				container,
				isAttach
			).also { binding ->
				binding.lifecycleOwner = this@TDetailFragment
				binding.vm = viewModel
			}
		}

	override fun initViews() {}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		readArguments()
	}

	private fun readArguments() {
		val itemId = arguments?.getLong(ARG_ITEM_ID)
		if (itemId == null) {
			requireActivity().supportFragmentManager.popBackStack()
			return
		}
		viewModel.setItemId(itemId)
	}

	companion object {
		fun newInstance(itemId: Long): TDetailFragment {
			val args = Bundle().apply { putLong(ARG_ITEM_ID, itemId) }

			return TDetailFragment().apply {
				arguments = args
			}
		}

		const val TAG = "DetailFragment"
	}
}
