package com.schopenhauer.nous.domain.repository

import com.schopenhauer.nous.data.local.model.JournalEntity
import com.schopenhauer.nous.util.Result

interface JournalsRepository {
  suspend fun writeJournals(journals: List<JournalEntity>): Result<Unit>
  suspend fun getJournals(): Result<List<JournalEntity>>
}
