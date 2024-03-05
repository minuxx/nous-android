package com.schopenhauer.nous.ui.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.schopenhauer.nous.R
import com.schopenhauer.nous.databinding.FragmentSettingBinding
import com.schopenhauer.nous.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>() {
	private val viewModel: SettingViewModel by viewModels()

	override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSettingBinding =
		{ inflater, container, isAttach ->
			DataBindingUtil.inflate<FragmentSettingBinding?>(
				inflater,
				R.layout.fragment_setting,
				container,
				isAttach
			).also { binding ->
				binding.lifecycleOwner = this@SettingFragment
				binding.vm = viewModel
			}
		}

	override fun initViews() {

	}
}
