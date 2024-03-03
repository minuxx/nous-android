package com.schopenhauer.nous.domain.repository

import com.schopenhauer.nous.data.remote.model.NewsItemPage
import com.schopenhauer.nous.util.Result

interface NewsRepository {
	suspend fun getNews(page: Int): Result<NewsItemPage>
}