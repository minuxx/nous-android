package com.schopenhauer.nous.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity (
	@PrimaryKey(autoGenerate = true) val id: Int,
	val content: String,
	val tag: String,
	val onCreateAt: String,
	val onUpdateAt: String,
	val isDeleted: Boolean
)