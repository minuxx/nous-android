package com.schopenhauer.nous.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.schopenhauer.nous.data.local.model.TaskEntity

@Dao
interface TaskDao {
	@Query("SELECT * FROM tasks")
	suspend fun getAllTasks(): List<TaskEntity>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertTasks(tasks: List<TaskEntity>)
}