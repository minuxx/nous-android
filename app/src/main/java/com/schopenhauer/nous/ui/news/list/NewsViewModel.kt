package com.schopenhauer.nous.ui.news.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schopenhauer.nous.domain.usecase.news.GetNewsUseCase
import com.schopenhauer.nous.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
	private val getNewsUseCase: GetNewsUseCase
) : ViewModel() {

	init {
		getNews()
	}

	fun getNews() = viewModelScope.launch {
		when(val res = getNewsUseCase("채용", 2)) {
			is Result.Success -> {NewsViewModel
				Log.d(TAG, "${res.data}")
			}
			is Result.Error -> {}
		}
	}

	companion object {
		const val TAG = "NewsViewModel"
	}
}