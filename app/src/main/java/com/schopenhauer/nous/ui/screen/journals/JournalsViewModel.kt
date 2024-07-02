package com.schopenhauer.nous.ui.screen.journals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schopenhauer.nous.data.Result
import com.schopenhauer.nous.domain.model.Journal
import com.schopenhauer.nous.domain.usecase.journal.GetJournalsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JournalsViewModel @Inject constructor(
	private val getJournalsUseCase: GetJournalsUseCase
) : ViewModel() {
	private val _uiState = MutableStateFlow(UiState())
	val uiState = _uiState.asStateFlow()

	private val _uiEvent = MutableSharedFlow<UiEvent>()
	val uiEvent = _uiEvent.asSharedFlow()

	init {
		getJournals()
	}

	fun getJournals() = viewModelScope.launch {
		when (val res = getJournalsUseCase()) {
			is Result.Success -> _uiState.update { it.copy(journals = res.data) }
			is Result.Failure -> updateUiEvent(UiEvent.OnShowToastMessage(res.error.message))
		}
	}

	private fun updateUiEvent(uiEvent: UiEvent) = viewModelScope.launch {
		_uiEvent.emit(uiEvent)
	}

	data class UiState(
		val journals: List<Journal> = listOf(),
		val isNoResult: Boolean = false,
	)

	sealed class UiEvent {
		data class OnShowToastMessage(val message: String) : UiEvent()
	}

	companion object {
		private const val TAG = "JournalsViewModel"
	}
}