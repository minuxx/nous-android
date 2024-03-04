package com.schopenhauer.nous.ui.news.list

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.schopenhauer.nous.databinding.FragmentNewsBinding
import com.schopenhauer.nous.ui.base.BaseFragment
import com.schopenhauer.nous.ui.base.PaginationScrollListener
import com.schopenhauer.nous.ui.news.list.NewsViewModel.UiEffect
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class NewsFragment : BaseFragment<FragmentNewsBinding>() {
	private val viewModel: NewsViewModel by viewModels()
	private lateinit var newsAdapter: NewsAdapter

	override fun initViews() {
		initJournalRecyclerView()
	}

	private fun initJournalRecyclerView() {
		newsAdapter = NewsAdapter { link ->
			startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
		}
		val linearLayoutManager = LinearLayoutManager(requireActivity())
		binding.newsRecyclerView.apply {
			layoutManager = linearLayoutManager
			adapter = newsAdapter
			itemAnimator = null
			setHasFixedSize(true)
			addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
				override fun loadMoreItems() { viewModel.getNews() }
				override fun isLastPage() = viewModel.isLastPage()
				override fun isPageLoading() = viewModel.isPageLoading()
			})
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		collectUiState()
		collectUiEffect()
	}

	private fun collectUiState() {
		collectState(viewModel.uiState.map { it.newses }.distinctUntilChanged()) {
			newsAdapter.submitList(it)
		}
	}

	private fun collectUiEffect() {
		collectState(viewModel.uiEffect) {
			when (it) {
				is UiEffect.OnError -> Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
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
