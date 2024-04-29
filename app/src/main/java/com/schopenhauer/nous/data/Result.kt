package com.schopenhauer.nous.data

import com.schopenhauer.nous.domain.model.JournalError
import com.schopenhauer.nous.domain.model.NewsError
import com.schopenhauer.nous.domain.model.TaskError

sealed class Result<out T> {
  data class Success<out T>(val data: T) : Result<T>()
  data class Failure(val error: Error) : Result<Nothing>()
}

sealed class Error(val code: String, val message: String) {
  class Journal(error: JournalError) : Error(error.code, error.message)
  class Task(error: TaskError) : Error(error.code, error.message)
  class News(error: NewsError) : Error(error.code, error.message)
}