package com.schopenhauer.nous.domain.repository

import com.schopenhauer.nous.data.remote.model.ItemPage
import com.schopenhauer.nous.util.Result

interface AppRepository {
  suspend fun fetchList(query: String, page: Int): Result<ItemPage>
}
