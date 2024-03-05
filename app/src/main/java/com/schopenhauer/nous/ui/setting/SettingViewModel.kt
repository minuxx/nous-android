package com.schopenhauer.nous.ui.setting

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(

) : ViewModel() {
  private val _uiState = MutableStateFlow(UiState())
  val uiState = _uiState.asStateFlow()

  fun setItemId(itemId: Long) {
    _uiState.update { it.copy(itemId = itemId) }
  }

  data class UiState(
    val itemId: Long = 0
  )

  companion object {
    private const val TAG = "DetailViewModel"
  }
}
