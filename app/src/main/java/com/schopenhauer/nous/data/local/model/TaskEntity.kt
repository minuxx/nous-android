package com.schopenhauer.nous.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity (
	@PrimaryKey(autoGenerate = true) val id: Long = 0,
	val journalId: Long = 0,
	val content: String,
)