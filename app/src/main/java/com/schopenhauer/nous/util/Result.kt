package com.schopenhauer.nous.util

sealed class Result<out T> {
  data class Success<out T>(val data: T? = null) : Result<T>()
  data class Error(val code: String, val message: String) : Result<Nothing>()
}
