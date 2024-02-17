package com.schopenhauer.nous.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.schopenhauer.nous.R
import com.schopenhauer.nous.databinding.FragmentDetailBinding
import com.schopenhauer.nous.ui.base.BaseFragment
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
}
