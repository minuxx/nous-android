package com.schopenhauer.nous.ui.journal.write

import androidx.lifecycle.ViewModel
import com.schopenhauer.nous.domain.model.Task
import com.schopenhauer.nous.util.ErrorType.TASK_CONTENT_EMPTY
import com.schopenhauer.nous.util.millisToDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class WriteJournalViewModel @Inject constructor(

) : ViewModel() {
	private val _uiState = MutableStateFlow(UiState())
	val uiState = _uiState.asStateFlow()

	private val _errorMessage = MutableStateFlow<String?>(null)
	val errorMessage = _errorMessage.asStateFlow()

	fun setDate(date: String) {
		_uiState.update { it.copy(date = date) }
	}

	fun writeTask(content: String) {
		if (content.isEmpty()) {
			_errorMessage.update { TASK_CONTENT_EMPTY.message }
			return
		}

		val newTasks = _uiState.value.tasks + Task(
			id = _uiState.value.tasks.size + 1,
			content = content
		)
		_uiState.update { it.copy(tasks = newTasks) }
	}

	fun eraseTask(id: Int) {
		val newTasks = _uiState.value.tasks.filter { it.id != id }
		_uiState.update {it.copy(tasks =newTasks) }
	}

	fun clearErrorMessage() = _errorMessage.update { null }

	data class UiState(
		val date: String = millisToDate(),
		val tasks: List<Task> = listOf(),
	)

	companion object {
		const val TAG = "WriteJournalViewModel"
	}
}