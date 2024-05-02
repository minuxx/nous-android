package com.schopenhauer.nous.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.schopenhauer.nous.data.local.entities.JournalEntity
import com.schopenhauer.nous.data.local.entities.JournalWithTasks

@Dao
interface JournalDao {
	@Transaction
	@Query("SELECT * FROM journals")
	suspend fun getJournals(): List<JournalWithTasks>

	@Transaction
	@Query("SELECT * FROM journals WHERE id = :id")
	suspend fun getJournal(id: Long): JournalWithTasks

	@Query("SELECT COUNT(*) FROM journals WHERE timeMillis = :timeMillis")
	suspend fun getJournalCountByDate(timeMillis: Long): Long

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertJournal(journal: JournalEntity): Long

	@Query("DELETE FROM journals WHERE id = :id")
	suspend fun deleteJournal(id: Long)
}