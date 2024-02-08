package com.schopenhauer.nous.data.remote.api

import com.schopenhauer.nous.data.remote.model.ListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AppApi {
  @GET("list")
  suspend fun fetchList(
    @Query("query") query: String,
    @Query("page") page: Int,
    @Query("size") size: Int,
  ): Response<ListResponse>
}