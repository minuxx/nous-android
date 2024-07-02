package com.schopenhauer.nous.ui.screen.journal

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schopenhauer.nous.data.Result
import com.schopenhauer.nous.domain.model.JournalError
import com.schopenhauer.nous.domain.model.Task
import com.schopenhauer.nous.domain.usecase.journal.GetJournalUseCase
import com.schopenhauer.nous.domain.usecase.journal.RemoveJournalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JournalViewModel @Inject constructor(
	savedStateHandle: SavedStateHandle,
	private val getJournalUseCase: GetJournalUseCase,
	private val removeJournalUseCase: RemoveJournalUseCase
) : ViewModel() {
	private val journalId: Long = savedStateHandle[JOURNAL_ID_ARG] ?: 0

	private val _uiState = MutableStateFlow(UiState())
	val uiState = _uiState.asStateFlow()

	private val _uiEvent = MutableSharedFlow<UiEvent>()
	val uiEvent = _uiEvent.asSharedFlow()

	init {
		getJournal()
	}

	private fun getJournal() = viewModelScope.launch {
		when (val res = getJournalUseCase(journalId)) {
			is Result.Success -> {
				val (_, timeMillis, tasks) = res.data
				_uiState.update { it.copy(timeMillis = timeMillis, tasks = tasks) }
			}
			is Result.Failure -> {
				when (res.error.code) {
					JournalError.LOAD.code -> updateUiEvent(UiEvent.OnFailLoadJournal)
				}
			}
		}
	}

	fun removeJournal() = viewModelScope.launch {
		when (val res = removeJournalUseCase(journalId)) {
			is Result.Success -> updateUiEvent(UiEvent.OnSuccessRemoveJournal)
			is Result.Failure -> {
				when (res.error.code) {
					JournalError.REMOVE.code -> updateUiEvent(UiEvent.OnShowToastMessage(res.error.message))
				}
			}
		}
	}

	private fun updateUiEvent(uiEvent: UiEvent) = viewModelScope.launch {
		_uiEvent.emit(uiEvent)
	}

	data class UiState(
		val isLoading: Boolean = false,
		val timeMillis: Long? = null,
		val tasks: List<Task> = listOf(),
	)

	// 이벤트 흐름이 두 개가 된다 -> Ui 상태 안에서 이벤트를 정의하고 이벤트를 전달한다.
	sealed class UiEvent {
		data object OnFailLoadJournal : UiEvent()
		data object OnSuccessRemoveJournal : UiEvent()
		data class OnShowToastMessage(val message: String) : UiEvent()
	}

	companion object {
		const val TAG = "WriteJournalViewModel"
	}
}
