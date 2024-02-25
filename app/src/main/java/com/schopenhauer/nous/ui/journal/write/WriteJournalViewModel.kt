package com.schopenhauer.nous.ui.journal.write

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schopenhauer.nous.domain.model.Task
import com.schopenhauer.nous.domain.usecase.SaveJournalUseCase
import com.schopenhauer.nous.util.ErrorType.ALREADY_SAVED_JOURNAL
import com.schopenhauer.nous.util.ErrorType.FAIL_SAVE_JOURNAL
import com.schopenhauer.nous.util.ErrorType.TASK_CONTENT_EMPTY
import com.schopenhauer.nous.util.Message.SUCCESS_SAVE_JOURNAL
import com.schopenhauer.nous.util.Result
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

	private val _uiEffect = MutableSharedFlow<UiEffect>()
	val uiEffect = _uiEffect.asSharedFlow()

	fun setDate(date: String) {
		_uiState.update { it.copy(date = date) }
	}

	fun writeTask(content: String) = viewModelScope.launch {
		if (content.isEmpty()) {
			_uiEffect.emit(UiEffect.OnError(TASK_CONTENT_EMPTY.code, TASK_CONTENT_EMPTY.message))
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
			_uiEffect.emit(UiEffect.OnError(TASK_CONTENT_EMPTY.code, TASK_CONTENT_EMPTY.message))
			return@launch
		}

		_uiState.update { it.copy(isLoading = true) }
		when (val res = saveJournalUseCase(_uiState.value.date, _uiState.value.tasks)) {
			is Result.Success -> _uiEffect.emit(UiEffect.OnSuccess(res.data ?: SUCCESS_SAVE_JOURNAL.content))
			is Result.Error -> {
				when (res.code) {
					ALREADY_SAVED_JOURNAL.code,
					FAIL_SAVE_JOURNAL.code -> _uiEffect.emit(UiEffect.OnError(res.code, res.message))
				}
			}
		}
		_uiState.update { it.copy(isLoading = false) }
	}

	data class UiState(
		val isLoading: Boolean = false,
		val date: String = millisToDate(),
		val tasks: List<Task> = listOf(),
	)

	sealed class UiEffect {
		data class OnError(val code: String, val message: String) : UiEffect()
		data class OnSuccess(val message: String) : UiEffect()
	}

	companion object {
		const val TAG = "WriteJournalViewModel"
	}
}
