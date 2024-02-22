package com.schopenhauer.nous.ui.journal.write

import androidx.lifecycle.ViewModel
import com.schopenhauer.nous.domain.model.Task
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

	fun setDate(date: String) {
		_uiState.update { it.copy(date = date) }
	}

	data class UiState(
		val date: String = millisToDate(),
		val tasks: List<Task> = listOf()
	)
}