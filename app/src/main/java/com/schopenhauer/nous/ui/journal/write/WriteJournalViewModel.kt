package com.schopenhauer.nous.ui.journal.write

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schopenhauer.nous.data.Result
import com.schopenhauer.nous.domain.model.JournalError
import com.schopenhauer.nous.domain.model.Task
import com.schopenhauer.nous.domain.usecase.journal.SaveJournalUseCase
import com.schopenhauer.nous.util.millisToDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WriteJournalViewModel @Inject constructor(
	private val saveJournalUseCase: SaveJournalUseCase
) : ViewModel() {
	private val _uiState = MutableStateFlow(UiState())
	val uiState = _uiState.asStateFlow()

	private val _uiEvent = MutableSharedFlow<UiEvent>()
	val uiEvent = _uiEvent.asSharedFlow()

	fun setDate(date: String) {
		_uiState.update { it.copy(date = date) }
	}

	fun writeTask(content: String) = viewModelScope.launch {
		if (content.isEmpty()) {
			updateUiEvent(UiEvent.OnShowToastMessage("업무 내용을 입력해주세요"))
			return@launch
		}

		val newTasks = _uiState.value.tasks + Task(
			id = (_uiState.value.tasks.size + 1).toLong(),
			content = content
		)
		_uiState.update { it.copy(tasks = newTasks) }
	}

	fun deleteTask(id: Long) {
		val newTasks = _uiState.value.tasks.filter { it.id != id }
		_uiState.update { it.copy(tasks = newTasks) }
	}

	fun saveJournal() = viewModelScope.launch {
		if (_uiState.value.isLoading) return@launch
		if (_uiState.value.tasks.isEmpty()) {
			updateUiEvent(UiEvent.OnShowToastMessage("업무 내용을 입력해주세요"))
			return@launch
		}

		_uiState.update { it.copy(isLoading = true) }
		when (val res = saveJournalUseCase(_uiState.value.date, _uiState.value.tasks)) {
			is Result.Success -> updateUiEvent(UiEvent.OnSuccessSaveJournal)
			is Result.Failure -> {
				when (res.error.code) {
					JournalError.ALREADY.code,
					JournalError.SAVE.code -> updateUiEvent(UiEvent.OnShowToastMessage(res.error.message))
				}
			}
		}
		_uiState.update { it.copy(isLoading = false) }
	}

	private fun updateUiEvent(uiEvent: UiEvent) = viewModelScope.launch {
		_uiEvent.emit(uiEvent)
	}

	data class UiState(
		val isLoading: Boolean = false,
		val date: String = millisToDate(),
		val tasks: List<Task> = listOf(),
	)

	sealed class UiEvent {
		data object OnSuccessSaveJournal : UiEvent()
		data class OnShowToastMessage(val message: String) : UiEvent()
	}

	companion object {
		const val TAG = "WriteJournalViewModel"
	}
}
