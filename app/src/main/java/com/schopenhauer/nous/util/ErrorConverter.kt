package com.schopenhauer.nous.util

import com.schopenhauer.nous.data.Result
import org.json.JSONObject
import com.schopenhauer.nous.util.ErrorType.UNKNOWN

object ErrorConverter {
  private const val NAVER_ERROR_CODE_KEY = "errorCode"
  private const val NAVER_ERROR_MESSAGE_KEY = "errorMessage"

  fun fromJson(json: String): Result.Error {
    return try {
      val jsonObj = JSONObject(json)
      val code = when {
        jsonObj.has(NAVER_ERROR_CODE_KEY) -> jsonObj.getString(NAVER_ERROR_CODE_KEY)
        else -> UNKNOWN.code
      }
      val message = when {
        jsonObj.has(NAVER_ERROR_MESSAGE_KEY) -> jsonObj.getString(NAVER_ERROR_MESSAGE_KEY)
        else -> UNKNOWN.message
      }

      Result.Error(code, message)
    } catch (e: Exception) {
      throw e
    }
  }
}
