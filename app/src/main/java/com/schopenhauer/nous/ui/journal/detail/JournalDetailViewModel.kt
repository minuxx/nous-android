package com.schopenhauer.nous.ui.journal.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schopenhauer.nous.domain.model.Journal
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
class JournalDetailViewModel @Inject constructor(
) : ViewModel() {
	private val _uiState = MutableStateFlow(UiState())
	val uiState = _uiState.asStateFlow()

	private val _uiEffect = MutableSharedFlow<UiEffect>()
	val uiEffect = _uiEffect.asSharedFlow()

	fun setJournalId(journalId: Long) {
		_uiState.update { it.copy(journalId = journalId) }
	}

	data class UiState(
		val journalId: Long = 0L,
		val date: String = "",
		val tasks: List<Task> = listOf()
	)

	sealed class UiEffect {
		data class OnError(val code: String, val message: String) : UiEffect()
		data class OnSuccess(val message: String) : UiEffect()
	}

	companion object {
		const val TAG = "WriteJournalViewModel"
	}
}
