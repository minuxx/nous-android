package com.schopenhauer.nous.ui.news.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schopenhauer.nous.domain.model.News
import com.schopenhauer.nous.domain.usecase.news.GetNewsUseCase
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
class NewsViewModel @Inject constructor(
	private val getNewsUseCase: GetNewsUseCase
) : ViewModel() {
	private val _uiState = MutableStateFlow(UiState())
	val uiState = _uiState.asStateFlow()

	private val _uiEffect = MutableSharedFlow<UiEffect>()
	val uiEffect = _uiEffect.asSharedFlow()

	fun getNews() = viewModelScope.launch {
		when (val res = getNewsUseCase(1)) {
			is Result.Success -> {
				_uiState.update { it.copy(newses = res.data ?: emptyList()) }
			}

			is Result.Error -> {}
		}
	}

	data class UiState(
		val newses: List<News> = listOf()
	)

	sealed class UiEffect {
		data class OnError(val code: String, val message: String) : UiEffect()
		data class OnSuccess(val message: String) : UiEffect()
	}

	companion object {
		const val TAG = "NewsViewModel"
	}
}