package com.schopenhauer.nous.data.remote.datasource

import com.schopenhauer.nous.data.remote.PAGE_SIZE
import com.schopenhauer.nous.data.remote.api.AppApi
import javax.inject.Inject

class AppRemoteDataSource @Inject constructor(
  private val mainApi: AppApi
) : BaseRemoteDataSource() {

  suspend fun fetchList(query: String, page: Int) = apiCall {
    mainApi.fetchList(
      query = query,
      page = page,
      size = PAGE_SIZE
    )
  }
}
