package com.schopenhauer.nous.domain.usecase.news

import com.schopenhauer.nous.domain.mapper.toNews
import com.schopenhauer.nous.domain.model.NewsPage
import com.schopenhauer.nous.domain.repository.NewsRepository
import com.schopenhauer.nous.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetNewsPageUseCase @Inject constructor(
	private val newsRepository: NewsRepository
) {
	suspend operator fun invoke(page: Int) = withContext(Dispatchers.Default) {
		when (val res = newsRepository.getNews(page)) {
			is Result.Success -> {
				val newses = res.data?.newsItems?.map { it.toNews() } ?: emptyList()
				val totalCnt = res.data?.totalCnt ?: 0
				Result.Success(
					NewsPage(
						newses = newses,
						totalCnt = totalCnt
					)
				)
			}
			is Result.Error -> res
		}
	}
}