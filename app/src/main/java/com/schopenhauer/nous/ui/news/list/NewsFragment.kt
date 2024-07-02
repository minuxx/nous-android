package com.schopenhauer.nous.ui.news.list

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import com.schopenhauer.nous.databinding.FragmentNewsBinding
import com.schopenhauer.nous.ui.base.BaseFragment
import com.schopenhauer.nous.ui.news.NewsScreen
import com.schopenhauer.nous.ui.news.list.NewsViewModel.UiEvent
import com.schopenhauer.nous.ui.theme.NousTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : BaseFragment<FragmentNewsBinding>() {
	private val viewModel: NewsViewModel by viewModels()

	override fun initViews() {
		binding.composeView.apply {
			setContent {
				NousTheme {
					setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
					val uiState by viewModel.uiState.collectAsState()

					Surface {
						NewsScreen(
							newses = uiState.newses,
							onNewsClick = { newsId ->
								startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(newsId))) },
							loadMoreNews = { viewModel.getNews() },
							isPageLoading = uiState.isPageLoading,
							isLastPage = uiState.isLastPage
						)
					}
				}
			}
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		collectUiEffect()
	}

	private fun collectUiEffect() {
		collectState(viewModel.uiEvent) { event ->
			when (event) {
				is UiEvent.OnShowToastMessage -> Toast.makeText(requireActivity(), event.message, Toast.LENGTH_SHORT).show()
			}
		}
	}

	override fun onStart() {
		super.onStart()
		viewModel.getNews()
	}

	override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNewsBinding =
		{ layoutInflater, container, isAttach ->
			FragmentNewsBinding.inflate(layoutInflater, container, isAttach)
		}

	companion object {
		const val TAG = "NewsFragment"
	}
}
