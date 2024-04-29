package com.schopenhauer.nous.domain.repository

import com.schopenhauer.nous.data.Result
import com.schopenhauer.nous.domain.model.NewsPage

interface NewsRepository {
	suspend fun getNews(page: Int): Result<NewsPage>
}