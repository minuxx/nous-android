package com.schopenhauer.nous.domain.usecase.news

import com.schopenhauer.nous.domain.mapper.toNews
import com.schopenhauer.nous.domain.repository.NewsRepository
import com.schopenhauer.nous.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
	private val newsRepository: NewsRepository
) {
	suspend operator fun invoke(query: String, page: Int) = withContext(Dispatchers.Default) {
		when(val res = newsRepository.getNews(query, page)) {
			is Result.Success -> {
				val news = res.data?.map { it.toNews() } ?: emptyList()
				Result.Success(news)
			}
			is Result.Error -> res
		}
	}
}