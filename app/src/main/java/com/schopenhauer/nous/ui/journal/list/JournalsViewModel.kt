package com.schopenhauer.nous.ui.journal.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schopenhauer.nous.domain.model.Journal
import com.schopenhauer.nous.domain.usecase.GetJournalsUseCase
import com.schopenhauer.nous.util.ErrorType.FAIL_LOAD_JOURNALS
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
class JournalsViewModel @Inject constructor(
	private val getJournalsUseCase: GetJournalsUseCase
) : ViewModel() {
	private val _uiState = MutableStateFlow(UiState())
	val uiState = _uiState.asStateFlow()

	private val _uiEffect = MutableSharedFlow<UiEffect>()
	val uiEffect = _uiEffect.asSharedFlow()

	init {
		getJournals()
	}

	fun getJournals() = viewModelScope.launch {
		when (val res = getJournalsUseCase()) {
			is Result.Success -> _uiState.update { it.copy(journals = res.data ?: emptyList()) }
			is Result.Error -> _uiEffect.emit(
				UiEffect.OnError(
					FAIL_LOAD_JOURNALS.code,
					FAIL_LOAD_JOURNALS.message
				)
			)
		}
	}

	fun toggleBookmark(journalId: Long) {}

	data class UiState(
		val journals: List<Journal> = listOf(),
		val isNoResult: Boolean = false,
	)

	sealed class UiEffect {
		data class OnError(val code: String, val message: String) : UiEffect()
		data class OnSuccess(val message: String) : UiEffect()
	}

	companion object {
		private const val TAG = "JournalsViewModel"
	}
}