package com.schopenhauer.nous.domain.repository

import com.schopenhauer.nous.data.network.models.NewsItemPage
import com.schopenhauer.nous.data.Result

interface NewsRepository {
	suspend fun getNews(page: Int): Result<NewsItemPage>
}