package com.schopenhauer.nous.ui.journal.write

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schopenhauer.nous.data.Result
import com.schopenhauer.nous.domain.model.JournalError
import com.schopenhauer.nous.domain.model.Task
import com.schopenhauer.nous.domain.model.TaskValidationException
import com.schopenhauer.nous.domain.usecase.journal.SaveJournalUseCase
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

	fun setTimeMillis(timeMillis: Long) {
		_uiState.update { it.copy(timeMillis = timeMillis) }
	}

	fun writeTask(content: String) {
		try {
			val tasks = _uiState.value.tasks
			val taskId = (tasks.size + 1).toLong()
			val newTask = Task.create(taskId, content)

			_uiState.update { it.copy(tasks = tasks + newTask) }
		} catch (e: TaskValidationException) {
			updateUiEvent(UiEvent.OnShowToastMessage(e.error.message))
		}
	}

	fun removeTask(id: Long) {
		val tasks = _uiState.value.tasks
		val newTasks = tasks.filter { it.id != id }

		_uiState.update { it.copy(tasks = newTasks) }
	}

	fun saveJournal() = viewModelScope.launch {
		_uiState.update { it.copy(isLoading = true) }

		val (timeMillis, tasks, _) = _uiState.value
		when (val res = saveJournalUseCase(timeMillis, tasks)) {
			is Result.Success -> updateUiEvent(UiEvent.OnSuccessSaveJournal)
			is Result.Failure -> {
				when (res.error.code) {
					JournalError.EMPTY_DATE.code,
					JournalError.EMPTY_TASK.code,
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
		val timeMillis: Long? = null,
		val tasks: List<Task> = listOf(),
		val isLoading: Boolean = false,
	)

	sealed class UiEvent {
		data object OnSuccessSaveJournal : UiEvent()
		data class OnShowToastMessage(val message: String) : UiEvent()
	}

	companion object {
		const val TAG = "WriteJournalViewModel"
	}
}
