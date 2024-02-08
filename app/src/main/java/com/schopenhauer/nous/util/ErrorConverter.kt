package com.schopenhauer.nous.util

import org.json.JSONObject

object ErrorConverter {
  private const val KEY_KAKAO_ERROR_TYPE = "errorType"
  private const val KEY_APP_ERROR_CODE = "code"
  private const val KEY_NOTHING = "nothing"
  private const val KEY_MESSAGE = "message"

  fun fromJson(json: String): Result.Error {
    return try {
      val jsonObj = JSONObject(json)
      val code = when {
        jsonObj.has(KEY_APP_ERROR_CODE) -> jsonObj.getString(KEY_APP_ERROR_CODE)
        jsonObj.has(KEY_KAKAO_ERROR_TYPE) -> jsonObj.getString(KEY_KAKAO_ERROR_TYPE)
        else -> jsonObj.getString(KEY_NOTHING)
      }
      val message = jsonObj.getString(KEY_MESSAGE)

      Result.Error(code, message)
    } catch (e: Exception) {
      throw e
    }
  }
}
