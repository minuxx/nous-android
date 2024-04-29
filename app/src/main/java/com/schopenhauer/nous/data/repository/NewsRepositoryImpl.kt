package com.schopenhauer.nous.data.repository

import com.schopenhauer.nous.data.Result
import com.schopenhauer.nous.data.network.ApiHandler
import com.schopenhauer.nous.data.network.NAVER_SEARCH_PAGE_SIZE
import com.schopenhauer.nous.data.network.NetworkOwner
import com.schopenhauer.nous.data.network.api.NaverSearchApi
import com.schopenhauer.nous.data.network.models.asDomain
import com.schopenhauer.nous.domain.model.NewsPage
import com.schopenhauer.nous.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
	private val naverSearchApi: NaverSearchApi
) : NewsRepository {

	override suspend fun getNews(page: Int) = withContext(Dispatchers.IO) {
		try {
			val res = ApiHandler.safeCall { naverSearchApi.fetchNews(start = ((page - 1) * NAVER_SEARCH_PAGE_SIZE) + 1) }

			Result.Success(NewsPage(res.items.map { it.asDomain() }, res.total))
		} catch (e: Exception) {
			ApiHandler.handleException(TAG, NetworkOwner.NAVER, e)
		}
	}

	companion object {
		private const val TAG = "NewsRepositoryImpl"
	}
}