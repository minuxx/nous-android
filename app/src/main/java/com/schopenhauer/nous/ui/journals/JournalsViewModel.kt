package com.schopenhauer.nous.ui.journals

import androidx.lifecycle.ViewModel
import com.schopenhauer.nous.domain.model.Journal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class JournalsViewModel @Inject constructor(

) : ViewModel() {
	private val _uiState = MutableStateFlow(UiState())
	val uiState = _uiState.asStateFlow()

	private val dummyJournals = listOf(
		Journal(1, "2024.01.15", "누스"),
		Journal(2, "2024.01.16", "누스"),
		Journal(3, "2024.01.17", "누스"),
		Journal(4, "2024.01.18", "누스"),
		Journal(5, "2024.01.19", "누스"),

    Journal(6, "2024.01.20", "누스"),
    Journal(7, "2024.01.21", "누스"),
    Journal(8, "2024.01.22", "누스"),
    Journal(9, "2024.01.23", "누스"),
    Journal(10, "2024.01.24", "누스"),

    Journal(11, "2024.01.25", "누스"),
    Journal(12, "2024.01.26", "누스"),
    Journal(13, "2024.01.27", "누스"),
    Journal(14, "2024.01.28", "누스"),
    Journal(15, "2024.01.29", "누스"),
	)

  init {
  	_uiState.update { it.copy(journals = dummyJournals) }
  }

	fun toggleBookmark(journalId: Int) {}

	data class UiState(
		val journals: List<Journal> = listOf(),
		val isNoResult: Boolean = false,
	)

	companion object {
		private const val TAG = "JournalsViewModel"
	}
}