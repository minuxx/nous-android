package com.schopenhauer.nous.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.schopenhauer.nous.data.local.model.JournalEntity

@Dao
interface JournalDao {
	// 더미 데이터 용
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertJournals(journals: List<JournalEntity>)

	@Query("SELECT * FROM journals")
	suspend fun getAllJournals(): List<JournalEntity>

	@Query("SELECT * FROM journals WHERE id = :id")
	suspend fun getJournal(id: Long): JournalEntity

	@Query("SELECT COUNT(*) FROM journals WHERE date = :date")
	suspend fun getJournalCountByDate(date: String): Long

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertJournal(journal: JournalEntity): Long
}