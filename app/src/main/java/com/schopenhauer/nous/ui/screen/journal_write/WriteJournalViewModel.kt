package com.schopenhauer.nous.ui.screen.journal_write

import androidx.lifecycle.viewModelScope
import com.schopenhauer.nous.data.Result
import com.schopenhauer.nous.domain.model.JournalError
import com.schopenhauer.nous.domain.model.Task
import com.schopenhauer.nous.domain.model.TaskValidationException
import com.schopenhauer.nous.domain.usecase.journal.SaveJournalUseCase
import com.schopenhauer.nous.ui.base.BaseViewModel
import com.schopenhauer.nous.ui.base.UiEffect
import com.schopenhauer.nous.ui.base.UiEvent
import com.schopenhauer.nous.ui.base.UiState
import com.schopenhauer.nous.util.getTodayTimeMillis
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WriteJournalViewModel @Inject constructor(
	private val saveJournalUseCase: SaveJournalUseCase
) : BaseViewModel<WriteJournalUiState, WriteJournalUiEvent, WriteJournalUiEffect>() {

	override fun createInitialState(): WriteJournalUiState = WriteJournalUiState()

	override fun handleUiEvent(event: WriteJournalUiEvent) {
		when (event) {
			WriteJournalUiEvent.OnBackClick -> {
				setUiEffect {
					WriteJournalUiEffect.NavigateBack
				}
			}

			is WriteJournalUiEvent.OnSelectDate -> {
				setUiState {
					copy(timeMillis = event.timeMillis)
				}
			}

			is WriteJournalUiEvent.OnWriteTask -> {
				writeTask(event.content)
			}

			is WriteJournalUiEvent.OnRemoveTask -> {
				removeTask(event.taskId)
			}

			WriteJournalUiEvent.OnSaveClick -> {
				saveJournal()
			}
		}
	}

	private fun writeTask(content: String) {
		try {
			val tasks = uiState.value.tasks
			val taskId = (tasks.size + 1).toLong()
			val newTask = Task.create(taskId, content)

			setUiState { copy(tasks = tasks + newTask) }
		} catch (e: TaskValidationException) {
			setUiEffect {
				WriteJournalUiEffect.ShowToast(e.error.message)
			}
		}
	}

	private fun removeTask(id: Long) {
		val tasks = uiState.value.tasks
		val newTasks = tasks.filter { it.id != id }

		setUiState { copy(tasks = newTasks) }
	}

	private fun saveJournal() = viewModelScope.launch {
		val (timeMillis, tasks) = uiState.value

		when (val res = saveJournalUseCase(timeMillis, tasks)) {
			is Result.Success -> {
				setUiEffect {
					WriteJournalUiEffect.NavigateBack
				}
			}

			is Result.Failure -> {
				when (res.error.code) {
					JournalError.EMPTY_DATE.code,
					JournalError.EMPTY_TASK.code,
					JournalError.ALREADY.code,
					JournalError.SAVE.code -> {
						setUiEffect {
							WriteJournalUiEffect.ShowToast(res.error.message)
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

data class WriteJournalUiState(
	val timeMillis: Long = getTodayTimeMillis(),
	val tasks: List<Task> = listOf(),
) : UiState

sealed interface WriteJournalUiEvent : UiEvent {
	data object OnBackClick : WriteJournalUiEvent
	data class OnSelectDate(val timeMillis: Long) : WriteJournalUiEvent
	data object OnSaveClick : WriteJournalUiEvent
	data class OnWriteTask(val content: String) : WriteJournalUiEvent
	data class OnRemoveTask(val taskId: Long) : WriteJournalUiEvent
}

sealed interface WriteJournalUiEffect : UiEffect {
	data class ShowToast(val message: String) : WriteJournalUiEffect
	data object NavigateBack : WriteJournalUiEffect
}
