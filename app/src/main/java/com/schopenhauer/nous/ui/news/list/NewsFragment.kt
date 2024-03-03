package com.schopenhauer.nous.ui.news.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.schopenhauer.nous.R
import com.schopenhauer.nous.databinding.FragmentNewsBinding
import com.schopenhauer.nous.ui.base.BaseFragment
import com.schopenhauer.nous.ui.journal.list.JournalAdapter
import com.schopenhauer.nous.ui.journal.list.JournalsFragment
import com.schopenhauer.nous.ui.main.MainActivity
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
		newsAdapter = NewsAdapter {}
		binding.newsRecyclerView.apply {
			layoutManager = LinearLayoutManager(requireActivity())
			adapter = newsAdapter
			itemAnimator = null
			setHasFixedSize(true)
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		collectUiState()
	}

	private fun collectUiState() {
		collectStateFlow(viewModel.uiState.map { it.newses }.distinctUntilChanged()) {
			newsAdapter.submitList(it)
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
