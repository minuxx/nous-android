package com.schopenhauer.nous.data.remote.api

import com.schopenhauer.nous.data.remote.NAVER_EMPLOYMENT_NEWS_QUERY
import com.schopenhauer.nous.data.remote.NAVER_SEARCH_PAGE_SIZE
import com.schopenhauer.nous.data.remote.model.GetNewsesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverSearchApi {
	@GET("search/news.json")
	suspend fun fetchNews(
		@Query("query") query: String = NAVER_EMPLOYMENT_NEWS_QUERY,
		@Query("start") start: Int,
		@Query("display") display: Int = NAVER_SEARCH_PAGE_SIZE,
		@Query("sort") sort: String = "dat"
	): Response<GetNewsesResponse>
}