package com.schopenhauer.nous.data.network.api

import com.schopenhauer.nous.data.network.NAVER_SEARCH_EMPLOYMENT_NEWS_QUERY
import com.schopenhauer.nous.data.network.NAVER_SEARCH_NEWS_SORT_STRATEGY
import com.schopenhauer.nous.data.network.NAVER_SEARCH_PAGE_SIZE
import com.schopenhauer.nous.data.network.models.GetNewsesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverSearchApi {
	@GET("search/news.json")
	suspend fun fetchNews(
		@Query("query") query: String = NAVER_SEARCH_EMPLOYMENT_NEWS_QUERY,
		@Query("start") start: Int,
		@Query("display") display: Int = NAVER_SEARCH_PAGE_SIZE,
		@Query("sort") sort: String = NAVER_SEARCH_NEWS_SORT_STRATEGY
	): Response<GetNewsesResponse>
}

