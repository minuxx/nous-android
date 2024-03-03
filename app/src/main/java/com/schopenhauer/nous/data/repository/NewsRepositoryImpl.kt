package com.schopenhauer.nous.data.repository

import com.schopenhauer.nous.data.remote.datasource.NaverRemoteDataSource
import com.schopenhauer.nous.data.remote.model.NewsApiModel
import com.schopenhauer.nous.domain.repository.NewsRepository
import com.schopenhauer.nous.util.Result
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
	private val naverRemoteDataSource: NaverRemoteDataSource
) : NewsRepository {

	override suspend fun getNews(query: String, page: Int): Result<List<NewsApiModel>> {
		return when(val res = naverRemoteDataSource.fetchNews(query, page)) {
			is Result.Success -> {
				Result.Success(res.data?.newsApiModels)
			}
			is Result.Error -> res
		}
	}

	companion object {
		private const val TAG = "NewsRepositoryImpl"
	}
}