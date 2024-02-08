package com.schopenhauer.nous.ui.list

import androidx.lifecycle.ViewModel
import com.schopenhauer.nous.domain.model.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TListViewModel  @Inject constructor(

) : ViewModel() {
  private val _uiState = MutableStateFlow(UiState())
  val uiState = _uiState.asStateFlow()

  fun toggleBookmark(itemId: Long) {}

  data class UiState(
    val page: Int = 1,
    val items: List<Item> = listOf(),
    val isNoResult: Boolean = false,
    val isPageLoading: Boolean = false,
    val isLastPage: Boolean = false,
  )

  companion object {
    private const val TAG = "ListViewModel"
  }
}
