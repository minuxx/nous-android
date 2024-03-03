package com.schopenhauer.nous.data.remote.datasource

import android.util.Log
import com.schopenhauer.nous.data.remote.NAVER_SEARCH_PAGE_SIZE
import com.schopenhauer.nous.data.remote.api.NaverSearchApi
import javax.inject.Inject

class NaverRemoteDataSource @Inject constructor(
  private val naverSearchApi: NaverSearchApi
) : BaseRemoteDataSource() {

  suspend fun fetchNews(page: Int) = apiCall {
    naverSearchApi.fetchNews(
      offset = ((page - 1) * NAVER_SEARCH_PAGE_SIZE) + 1
    )
  }
}