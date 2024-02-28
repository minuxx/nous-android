package com.schopenhauer.nous.ui.news.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.schopenhauer.nous.databinding.FragmentNewsBinding
import com.schopenhauer.nous.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : BaseFragment<FragmentNewsBinding>() {
	private val viewModel: NewsViewModel by viewModels()

	override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNewsBinding =
		{ layoutInflater, container, isAttach ->
			FragmentNewsBinding.inflate(layoutInflater, container, isAttach)
		}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		viewModel.getNews()
	}

	override fun initViews() {
	}

	companion object {
		const val TAG = "NewsFragment"
	}
}
