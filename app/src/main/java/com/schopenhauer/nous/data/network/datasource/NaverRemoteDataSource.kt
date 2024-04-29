package com.schopenhauer.nous.data.network.datasource

import com.schopenhauer.nous.data.network.NAVER_SEARCH_PAGE_SIZE
import com.schopenhauer.nous.data.network.api.NaverSearchApi
import javax.inject.Inject

class NaverRemoteDataSource @Inject constructor(
  private val naverSearchApi: NaverSearchApi
) : BaseRemoteDataSource() {

  suspend fun fetchNews(page: Int) = apiCall {
    naverSearchApi.fetchNews(
      start = ((page - 1) * NAVER_SEARCH_PAGE_SIZE) + 1
    )
  }
}