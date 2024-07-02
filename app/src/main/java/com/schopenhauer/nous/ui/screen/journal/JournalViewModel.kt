package com.schopenhauer.nous.ui.screen.journal

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.schopenhauer.nous.data.Result
import com.schopenhauer.nous.domain.model.JournalError
import com.schopenhauer.nous.domain.model.Task
import com.schopenhauer.nous.domain.usecase.journal.GetJournalUseCase
import com.schopenhauer.nous.domain.usecase.journal.RemoveJournalUseCase
import com.schopenhauer.nous.ui.base.BaseViewModel
import com.schopenhauer.nous.ui.base.UiEffect
import com.schopenhauer.nous.ui.base.UiEvent
import com.schopenhauer.nous.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JournalViewModel @Inject constructor(
	savedStateHandle: SavedStateHandle,
	private val getJournalUseCase: GetJournalUseCase,
	private val removeJournalUseCase: RemoveJournalUseCase
) : BaseViewModel<JournalUiState, JournalUiEvent, JournalUiEffect>() {
	private val journalId: Long = savedStateHandle[JOURNAL_ID_ARG] ?: 0

	init {
		getJournal()
	}

	override fun onCleared() {
		super.onCleared()
		Log.d("JournalViewModel", "onCleared")
	}

	override fun createInitialState(): JournalUiState = JournalUiState.Idle

	override fun handleUiEvent(event: JournalUiEvent) {
		when(event) {
			JournalUiEvent.OnBackClick -> {
				setUiEffect {
					JournalUiEffect.NavigateBack
				}
			}
			JournalUiEvent.OnRemoveClick -> {
				removeJournal()
			}
		}
	}

	private fun getJournal() = viewModelScope.launch {
		when (val res = getJournalUseCase(journalId)) {
			is Result.Success -> {
				val (_, timeMillis, tasks) = res.data

				setUiState {
					JournalUiState.Loaded(
						timeMillis = timeMillis,
						tasks = tasks
					)
				}
			}
			is Result.Failure -> {
				when (res.error.code) {
					JournalError.LOAD.code -> {
						setUiEffect {
							JournalUiEffect.ShowToast(res.error.message)
						}
					}
				}
			}
		}
	}

	private fun removeJournal() = viewModelScope.launch {
		when (val res = removeJournalUseCase(journalId)) {
			is Result.Success -> {
				setUiEffect {
					JournalUiEffect.NavigateBack
				}
			}
			is Result.Failure -> {
				when (res.error.code) {
					JournalError.REMOVE.code -> {
						setUiEffect {
							JournalUiEffect.ShowToast(res.error.message)
						}
					}
				}
			}
		}
	}

	companion object {
		const val TAG = "WriteJournalViewModel"
	}
}

sealed interface JournalUiState : UiState {
	data object Idle : JournalUiState
	data class Loaded(
		val timeMillis: Long,
		val tasks: List<Task>
	) : JournalUiState
	data object Loading : JournalUiState
}

sealed interface JournalUiEvent : UiEvent {
	data object OnRemoveClick : JournalUiEvent
	data object OnBackClick : JournalUiEvent
}

sealed interface JournalUiEffect : UiEffect {
	data class ShowToast(val message: String) : JournalUiEffect
	data object NavigateBack : JournalUiEffect
}