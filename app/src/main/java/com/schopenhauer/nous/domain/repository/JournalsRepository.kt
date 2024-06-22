package com.schopenhauer.nous.domain.repository

import com.schopenhauer.nous.data.Result
import com.schopenhauer.nous.domain.model.Journal
import com.schopenhauer.nous.domain.model.Task

interface JournalsRepository {
  suspend fun getJournals(): Result<List<Journal>>
  suspend fun saveJournal(timeMillis: Long?, tasks: List<Task>): Result<Unit>
  suspend fun getJournal(id: Long): Result<Journal>
  suspend fun removeJournal(id: Long): Result<Unit>
}