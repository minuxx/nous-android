package com.schopenhauer.nous.domain.repository

import com.schopenhauer.nous.data.local.model.JournalEntity
import com.schopenhauer.nous.data.local.model.TaskEntity
import com.schopenhauer.nous.data.repository.model.JournalWithTasks
import com.schopenhauer.nous.util.Result

interface JournalsRepository {
  suspend fun writeJournals(journals: List<JournalEntity>): Result<Unit> // 더미 데이터용
  suspend fun getJournals(): Result<List<JournalEntity>>
  suspend fun saveJournal(date: String, tasks: List<TaskEntity>): Result<String>
  suspend fun getJournal(id: Long): Result<JournalWithTasks>
  suspend fun deleteJournal(id: Long): Result<String>
}