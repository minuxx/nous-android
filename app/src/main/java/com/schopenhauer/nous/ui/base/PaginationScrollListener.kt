package com.schopenhauer.nous.ui.base

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(private val layoutManager: LinearLayoutManager) :
  RecyclerView.OnScrollListener() {

  override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
    super.onScrolled(recyclerView, dx, dy)
    val visibleItemCount: Int = layoutManager.childCount
    val totalItemCount: Int = layoutManager.itemCount
    val lastVisibleItemPosition: Int = layoutManager.findLastVisibleItemPosition()

    if (!isPageLoading() && !isLastPage()) {
      if (visibleItemCount * 1.5 + lastVisibleItemPosition >= totalItemCount &&
        lastVisibleItemPosition >= 0
      ) {
        loadMoreItems()
      }
    }
  }

  abstract fun loadMoreItems()
  abstract fun isLastPage(): Boolean
  abstract fun isPageLoading(): Boolean
}
