package com.schopenhauer.nous.data.remote.datasource

import com.schopenhauer.nous.data.remote.PAGE_SIZE
import com.schopenhauer.nous.data.remote.api.NaverSearchApi
import javax.inject.Inject

class NaverRemoteDataSource @Inject constructor(
  private val naverSearchApi: NaverSearchApi
) : BaseRemoteDataSource() {

  suspend fun fetchNews(query: String, page: Int) = apiCall {
    naverSearchApi.fetchNews(
      query = query,
      offset = ((page - 1) * PAGE_SIZE) + 1
    )
  }
}