package com.schopenhauer.nous.ui.screen.journals

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schopenhauer.nous.data.Result
import com.schopenhauer.nous.domain.model.Journal
import com.schopenhauer.nous.domain.usecase.journal.GetJournalsUseCase
import com.schopenhauer.nous.ui.base.BaseViewModel
import com.schopenhauer.nous.ui.base.UiEffect
import com.schopenhauer.nous.ui.base.UiEvent
import com.schopenhauer.nous.ui.base.UiState
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
) : BaseViewModel<JournalsUiState, JournalsUiEvent, JournalsUiEffect>() {
	override fun createInitialState(): JournalsUiState = JournalsUiState.Idle

	fun getJournals() = viewModelScope.launch {
		when (val res = getJournalsUseCase()) {
			is Result.Success -> {
				setUiState {
					JournalsUiState.Loaded(res.data)
				}
			}
			is Result.Failure -> {
				setUiState {
					JournalsUiState.Idle
				}
				setUiEffect {
					JournalsUiEffect.ShowToast(res.error.message)
				}
			}
		}
	}

	override fun handleUiEvent(event: JournalsUiEvent) {
		when(event) {
			is JournalsUiEvent.OnJournalClicked -> {
				setUiEffect {
					JournalsUiEffect.NavigateToJournal(event.journalId)
				}
			}
			is JournalsUiEvent.OnWriteButtonClicked -> {
				setUiEffect {
					JournalsUiEffect.NavigateToWriteJournal
				}
			}
		}
	}

	companion object {
		private const val TAG = "JournalsViewModel"
	}
}

sealed interface JournalsUiState : UiState {
	data object Idle : JournalsUiState
	data class Loaded(val journals: List<Journal>) : JournalsUiState
	data object Loading : JournalsUiState
}

sealed interface JournalsUiEvent : UiEvent {
	data class OnJournalClicked(val journalId: Long) : JournalsUiEvent
	data object OnWriteButtonClicked : JournalsUiEvent
}

sealed interface JournalsUiEffect : UiEffect {
	data class ShowToast(val message: String) : JournalsUiEffect
	data class NavigateToJournal(val journalId: Long) : JournalsUiEffect
	data object NavigateToWriteJournal : JournalsUiEffect
}