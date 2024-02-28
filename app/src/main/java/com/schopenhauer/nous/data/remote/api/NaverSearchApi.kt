package com.schopenhauer.nous.data.remote.api

import com.schopenhauer.nous.data.remote.PAGE_SIZE
import com.schopenhauer.nous.domain.model.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverSearchApi {
	@GET("/search/news.json")
	suspend fun fetchNews(
		@Query("query") query: String,
		@Query("start") offset: Int,
		@Query("display") display: Int = PAGE_SIZE,
		@Query("sort") sort: String = "date"
	): Response<List<News>>
}