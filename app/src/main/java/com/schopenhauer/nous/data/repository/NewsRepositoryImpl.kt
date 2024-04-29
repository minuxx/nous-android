package com.schopenhauer.nous.data.repository

import com.schopenhauer.nous.data.network.datasource.NaverRemoteDataSource
import com.schopenhauer.nous.data.network.models.NewsItemPage
import com.schopenhauer.nous.domain.repository.NewsRepository
import com.schopenhauer.nous.data.Result
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
	private val naverRemoteDataSource: NaverRemoteDataSource
) : NewsRepository {

	override suspend fun getNews(page: Int): Result<NewsItemPage> {
		return when (val res = naverRemoteDataSource.fetchNews(page)) {
			is Result.Success -> {
				Result.Success(
					NewsItemPage(
						newsItems = res.data.items,
						totalCnt = res.data.total
					)
				)
			}
			is Result.Error -> res
		}
	}

	companion object {
		private const val TAG = "NewsRepositoryImpl"
	}
}