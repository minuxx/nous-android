package com.schopenhauer.nous.data.repository

import com.schopenhauer.nous.data.remote.datasource.AppRemoteDataSource
import com.schopenhauer.nous.data.remote.model.ItemPage
import com.schopenhauer.nous.domain.repository.AppRepository
import com.schopenhauer.nous.util.Result
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
  private val appRemoteDataSource: AppRemoteDataSource,
) : AppRepository {

  override suspend fun fetchList(
    query: String,
    page: Int
  ): Result<ItemPage> =
    when (val res = appRemoteDataSource.fetchList(query, page)) {
      is Result.Success -> Result.Success(
        ItemPage(
          items = res.data?.items ?: emptyList(),
          isLast = res.data?.meta?.isEnd ?: true
        )
      )
      is Result.Error -> Result.Error(res.code, res.message)
    }
}
