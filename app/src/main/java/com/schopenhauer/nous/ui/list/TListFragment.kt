package com.schopenhauer.nous.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.schopenhauer.nous.databinding.FragmentListBinding
import com.schopenhauer.nous.ui.base.BaseFragment
import com.schopenhauer.nous.ui.detail.TListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class TListFragment : BaseFragment<FragmentListBinding>() {
  override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentListBinding =
    { layoutInflater, container, isAttach ->
      FragmentListBinding.inflate(layoutInflater, container, isAttach)
    }

	private val viewModel: TListViewModel by viewModels()
	private lateinit var listAdapter: TListAdapter

  override fun initViews() {
    initRecyclerView()
  }

	private fun initRecyclerView() {
		val linearLayoutManager = LinearLayoutManager(requireActivity())
		listAdapter = TListAdapter { viewModel.toggleBookmark(it) }

		binding.recyclerView.apply {
			adapter = listAdapter
			layoutManager = linearLayoutManager
			itemAnimator = null
			setHasFixedSize(true)
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

    collectStateFlow(viewModel.uiState.map { it.items }.distinctUntilChanged()) {
      listAdapter.submitList(it)
    }
	}

	companion object {
		fun newInstance(): TListFragment = TListFragment()
		const val ARG_ITEM_ID = "argument-item-id"
	}
}
