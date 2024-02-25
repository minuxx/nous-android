package com.schopenhauer.nous.ui.journal.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schopenhauer.nous.domain.model.Task
import com.schopenhauer.nous.domain.usecase.GetJournalUseCase
import com.schopenhauer.nous.util.ErrorType.FAIL_LOAD_JOURNAL
import com.schopenhauer.nous.util.Result
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
	private val getJournalUseCase: GetJournalUseCase
) : ViewModel() {
	private val _uiState = MutableStateFlow(UiState())
	val uiState = _uiState.asStateFlow()

	private val _uiEffect = MutableSharedFlow<UiEffect>()
	val uiEffect = _uiEffect.asSharedFlow()

	fun setJournalId(journalId: Long) {
		_uiState.update { it.copy(journalId = journalId) }
		getJournal()
	}

	private fun getJournal() = viewModelScope.launch {
		if (_uiState.value.journalId == 0L) return@launch

		when (val res = getJournalUseCase(_uiState.value.journalId)) {
			is Result.Success -> {
				if (res.data != null) {
					_uiState.update { it.copy(date = res.data.date, tasks = res.data.tasks) }
				} else {
					_uiEffect.emit(UiEffect.OnError(FAIL_LOAD_JOURNAL.code, FAIL_LOAD_JOURNAL.message))
				}
			}
			is Result.Error -> {
				when (res.code) {
					FAIL_LOAD_JOURNAL.code -> _uiEffect.emit(
						UiEffect.OnError(
							FAIL_LOAD_JOURNAL.code,
							FAIL_LOAD_JOURNAL.message
						)
					)
				}
			}
		}
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
