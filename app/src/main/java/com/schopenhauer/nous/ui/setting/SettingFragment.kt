package com.schopenhauer.nous.ui.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.schopenhauer.nous.BuildConfig
import com.schopenhauer.nous.R
import com.schopenhauer.nous.databinding.FragmentJournalDetailBinding
import com.schopenhauer.nous.databinding.FragmentSettingBinding
import com.schopenhauer.nous.ui.base.BaseFragment
import com.schopenhauer.nous.ui.theme.NousTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>() {
	private val viewModel: SettingViewModel by viewModels()

	override fun initViews() {
		viewModel.setVersion(BuildConfig.VERSION_NAME)

		binding.composeView.apply {
			setContent {
				setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
				val uiState by viewModel.uiState.collectAsState()

				NousTheme {
					Surface {
						SettingScreen(
							version = uiState.version,
							onClick = {
								Toast.makeText(requireActivity(),"개발 준비중이에요", Toast.LENGTH_SHORT).show()
							}
						)
					}
				}
			}
		}
	}

	override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSettingBinding =
		{ layoutInflater, container, isAttach ->
			FragmentSettingBinding.inflate(layoutInflater, container, isAttach)
		}
}
