package com.schopenhauer.nous.data.repository

import com.schopenhauer.nous.data.remote.datasource.NaverRemoteDataSource
import com.schopenhauer.nous.data.remote.model.NewsItemPage
import com.schopenhauer.nous.domain.repository.NewsRepository
import com.schopenhauer.nous.util.Result
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
	private val naverRemoteDataSource: NaverRemoteDataSource
) : NewsRepository {

	override suspend fun getNews(page: Int): Result<NewsItemPage> {
		return when (val res = naverRemoteDataSource.fetchNews(page)) {
			is Result.Success -> {
				Result.Success(
					NewsItemPage(
						newsItems = res.data?.items ?: emptyList(),
						totalCnt = res.data?.total ?: 0
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