package com.schopenhauer.nous.domain.usecase.news

import com.schopenhauer.nous.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsPageUseCase @Inject constructor(
	private val newsRepository: NewsRepository
) {
	suspend operator fun invoke(page: Int) = newsRepository.getNews(page)
}