package com.schopenhauer.nous.data.remote.datasource

import android.util.Log
import com.schopenhauer.nous.util.ErrorConverter
import com.schopenhauer.nous.util.ErrorType.UNKNOWN
import com.schopenhauer.nous.util.ErrorType.NETWORK
import com.schopenhauer.nous.util.ErrorType.JSON
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONException
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import com.schopenhauer.nous.util.Result

open class BaseRemoteDataSource {

  suspend fun <T : Any> apiCall(execute: suspend () -> Response<T>) = withContext(Dispatchers.IO) {
    try {
      val response = execute()
      val body = response.body()

      if (response.isSuccessful && body != null) {
        Result.Success(body)
      } else {
        val error = convertErrorBody(response.errorBody())
        Result.Error(error.code, error.message)
      }
    } catch (e: Exception) {
      handleNetworkException(e)
    }
  }

  private fun handleNetworkException(e: Exception): Result.Error = when (e) {
    is HttpException -> {
      Log.w(TAG, "HTTP error: ${e.code()}, Message: ${e.message()}")
      Result.Error(NETWORK.code, NETWORK.message)
    }
    is IOException -> {
      Log.w(TAG, "Network error: ${e.message ?: NETWORK.message}")
      Result.Error(NETWORK.code, NETWORK.message)
    }
    else -> {
      Log.e(TAG, "Unknown error: ${e.message ?: UNKNOWN.message}")
      Result.Error(UNKNOWN.code, UNKNOWN.message)
    }
  }

  private fun convertErrorBody(errorBody: ResponseBody?): Result.Error {
    return try {
      if (errorBody == null) {
        Result.Error(NETWORK.code, NETWORK.message)
      } else {
        ErrorConverter.fromJson(errorBody.string())
      }
    } catch (e: JSONException) {
      Log.e(TAG, "Json error: ${e.message ?: UNKNOWN.message}")
      Result.Error(JSON.code, e.message ?: JSON.message)
    } catch (e: Exception) {
      Log.e(TAG, "Error converting error body: ${e.message ?: UNKNOWN.message}")
      Result.Error(UNKNOWN.code, e.message ?: UNKNOWN.message)
    }
  }

  companion object {
    private const val TAG = "BaseRemoteDataSource"
  }
}
