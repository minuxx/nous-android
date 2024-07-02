package com.schopenhauer.nous.ui.screen.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schopenhauer.nous.data.Result
import com.schopenhauer.nous.data.network.NetworkError
import com.schopenhauer.nous.domain.model.News
import com.schopenhauer.nous.domain.model.NewsError
import com.schopenhauer.nous.domain.usecase.news.GetNewsPageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
	private val getNewsPageUseCase: GetNewsPageUseCase
) : ViewModel() {
	private val _uiState = MutableStateFlow(UiState())
	val uiState = _uiState.asStateFlow()

	private val _uiEvent = MutableSharedFlow<UiEvent>()
	val uiEvent = _uiEvent.asSharedFlow()

	init {
		getNews()
	}

	fun getNews() = viewModelScope.launch {
		_uiState.update { it.copy(isPageLoading = true) }
		when (val res = getNewsPageUseCase(_uiState.value.page)) {
			is Result.Success -> {
				val newNewses = _uiState.value.newses + res.data.newses
				val totalCnt = res.data.totalCnt

				_uiState.update {
					it.copy(
						page = _uiState.value.page + 1,
						newses = newNewses,
						isLastPage = totalCnt <= newNewses.size
					)
				}
				yield()
			}

			is Result.Failure -> {
				when(res.error.code) {
					NetworkError.CONNECT.code,
					NewsError.LOAD.code -> updateUiEvent(UiEvent.OnShowToastMessage(res.error.message))
				}
			}
		}
		_uiState.update { it.copy(isPageLoading = false) }
	}

	fun isPageLoading() = _uiState.value.isPageLoading
	fun isLastPage() = _uiState.value.isLastPage

	private fun updateUiEvent(uiEvent: UiEvent) = viewModelScope.launch {
		_uiEvent.emit(uiEvent)
	}

	data class UiState(
		val page: Int = 1,
		val newses: List<News> = listOf(),
		val isPageLoading: Boolean = false,
		val isLastPage: Boolean = false
	)

	sealed class UiEvent {
		data class OnShowToastMessage(val message: String) : UiEvent()
	}

	companion object {
		const val TAG = "NewsViewModel"
	}
}