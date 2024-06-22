package com.schopenhauer.nous.ui.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.schopenhauer.nous.BuildConfig
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
		viewModel.setVersion(BuildConfig.VERSION_NAME)
		binding.termsOfUseContainer.setOnClickListener {
			Toast.makeText(requireActivity(),"개발 준비중이에요", Toast.LENGTH_SHORT).show()
		}
		binding.privacyPolicyContainer.setOnClickListener {
			Toast.makeText(requireActivity(), "개발 준비중이에요", Toast.LENGTH_SHORT).show()
		}
		binding.openSourceLicenseContainer.setOnClickListener {
			Toast.makeText(requireActivity(), "개발 준비중이에요", Toast.LENGTH_SHORT).show()
		}
	}
}
