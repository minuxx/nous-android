package com.schopenhauer.nous.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.schopenhauer.nous.data.local.entities.TaskEntity

@Dao
interface TaskDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertTasks(tasks: List<TaskEntity>)

	@Query("DELETE FROM tasks WHERE journalId = :journalId")
	suspend fun deleteTasks(journalId: Long)
}