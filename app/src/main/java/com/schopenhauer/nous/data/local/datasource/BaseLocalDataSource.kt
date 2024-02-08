package com.schopenhauer.nous.data.local.datasource

import android.util.Log
import com.schopenhauer.nous.util.ErrorType.LOCAL
import com.schopenhauer.nous.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class BaseLocalDataSource {

  suspend fun <T : Any> execute(call: () -> T): Result<T> = withContext(Dispatchers.IO) {
    try {
      val result = call.invoke()
      Result.Success(result)
    } catch (e: Exception) {
      handleLocalException(e)
    }
  }

  private fun handleLocalException(e: Exception): Result.Error {
    Log.w(TAG, "Local error: ${e.message ?: LOCAL.message}")
    return Result.Error(LOCAL.code, LOCAL.message)
  }

  companion object {
    private const val TAG = "BaseLocalDataSource"
  }
}
