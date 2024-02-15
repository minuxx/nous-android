package com.schopenhauer.nous.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.schopenhauer.nous.data.local.model.JournalEntity

@Dao
interface JournalDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertJournals(journals: List<JournalEntity>)

	@Query("SELECT * FROM journals")
	suspend fun getAllJournals(): List<JournalEntity>
}