package com.schopenhauer.nous.ui.screen.setting

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

  fun setVersion(version: String) {
    _uiState.update { it.copy(version = version) }
  }

  data class UiState(
    val version: String = ""
  )

  companion object {
    private const val TAG = "DetailViewModel"
  }
}
