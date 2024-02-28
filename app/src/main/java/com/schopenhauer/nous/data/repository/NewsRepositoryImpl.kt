package com.schopenhauer.nous.data.repository

import com.schopenhauer.nous.data.remote.datasource.NaverRemoteDataSource
import com.schopenhauer.nous.domain.model.News
import com.schopenhauer.nous.domain.repository.NewsRepository
import com.schopenhauer.nous.util.Result
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
	private val naverRemoteDataSource: NaverRemoteDataSource
) : NewsRepository {

	override suspend fun getNews(query: String, page: Int): Result<List<News>> {
		return naverRemoteDataSource.fetchNews(query, page)
	}

	companion object {
		private const val TAG = "NewsRepositoryImpl"
	}
}