package com.schopenhauer.nous.domain.repository

import com.schopenhauer.nous.domain.model.News
import com.schopenhauer.nous.util.Result

interface NewsRepository {
	suspend fun getNews(query: String, page: Int): Result<List<News>>
}